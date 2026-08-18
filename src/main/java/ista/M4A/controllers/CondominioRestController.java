package ista.M4A.controllers;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ista.M4A.models.entity.Condominio;
import ista.M4A.models.entity.Departamento;
import ista.M4A.models.service.ICondominioService;
import ista.M4A.models.service.IDepartamentoService;
import ista.M4A.models.service.IPagoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {
	    "http://localhost:4200",
	    "https://facilpago-frontend.onrender.com"
	})
public class CondominioRestController {

	private final ICondominioService condominioService;
	private final IDepartamentoService departamentoService;
	private final IPagoService pagoService;

	public CondominioRestController(ICondominioService condominioService, IDepartamentoService departamentoService, IPagoService pagoService) {
		this.condominioService = condominioService;
		this.departamentoService = departamentoService;
		this.pagoService = pagoService;
	}

	@GetMapping("/condominos")
	public List<Condominio> listar() {
		return condominioService.findAll();
	}

	@GetMapping("/condominos/{id}")
	public ResponseEntity<Condominio> buscarPorId(@PathVariable Long id) {
		return condominioService.findById(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/condominos/departamento/{departamentoId}")
	public List<Condominio> listarPorDepartamento(@PathVariable Long departamentoId) {
		return condominioService.findByDepartamentoId(departamentoId);
	}

	@PostMapping("/condominos")
	public ResponseEntity<Condominio> crear(@Valid @RequestBody Condominio condominio) {

		Set<Departamento> departamentos = obtenerDepartamentos(condominio.getDepartamentos());

		if (departamentos.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}

		condominio.setId(null);
		condominio.setDepartamentos(departamentos);

		if (condominioService.existsByCedulaCondominio(condominio.getCedulaCondominio())) {
			return ResponseEntity.status(409).build();
		}
		
		Condominio guardado = condominioService.save(condominio);

		URI ubicacion = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(guardado.getId())
				.toUri();

		return ResponseEntity.created(ubicacion).body(guardado);
	}

	@PutMapping("/condominos/{id}")
	public ResponseEntity<Condominio> actualizar(@PathVariable Long id, @Valid @RequestBody Condominio condominio) {

		Set<Departamento> departamentos = obtenerDepartamentos(condominio.getDepartamentos());

		if (departamentos.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}

		return condominioService.findById(id)
				.map(actual -> {

					actual.setCedulaCondominio(condominio.getCedulaCondominio());
					actual.setNombreCondomino(condominio.getNombreCondomino());
					actual.setApellidoCondomino(condominio.getApellidoCondomino());
					actual.setCelularCondomino(condominio.getCelularCondomino());
					actual.setTelefonoCondomino(condominio.getTelefonoCondomino());
					actual.setDepartamentos(departamentos);

					return ResponseEntity.ok(condominioService.save(actual));
				})
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/condominos/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {

		if (condominioService.findById(id).isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		if (pagoService.existsByCondominioId(id)) {
			return ResponseEntity.status(409).build();
		}

		condominioService.delete(id);

		return ResponseEntity.noContent().build();
	}

	private Set<Departamento> obtenerDepartamentos(Set<Departamento> departamentosRecibidos) {

		Set<Departamento> departamentos = new HashSet<>();

		if (departamentosRecibidos == null) {
			return departamentos;
		}

		for (Departamento departamento : departamentosRecibidos) {

			if (departamento.getId() != null) {

				departamentoService.findById(departamento.getId()).ifPresent(departamentos::add);
			}
		}

		return departamentos;
	}
}