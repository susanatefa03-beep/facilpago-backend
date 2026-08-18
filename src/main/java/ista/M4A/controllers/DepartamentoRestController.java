package ista.M4A.controllers;

import java.net.URI;
import java.util.List;

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

import ista.M4A.models.entity.Bloque;
import ista.M4A.models.entity.Departamento;
import ista.M4A.models.service.IBloqueService;
import ista.M4A.models.service.ICondominioService;
import ista.M4A.models.service.IDepartamentoService;
import ista.M4A.models.service.IPagoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class DepartamentoRestController {

	private final IDepartamentoService departamentoService;
	private final IBloqueService bloqueService;
	private final ICondominioService condominioService;
	private final IPagoService pagoService;

	public DepartamentoRestController(IDepartamentoService departamentoService, IBloqueService bloqueService, ICondominioService condominioService, IPagoService pagoService) {
		this.departamentoService = departamentoService;
		this.bloqueService = bloqueService;
		this.condominioService = condominioService;
		this.pagoService = pagoService;
	}

	@GetMapping("/departamentos")
	public List<Departamento> listar() {
		return departamentoService.findAll();
	}

	@GetMapping("/departamentos/{id}")
	public ResponseEntity<Departamento> buscarPorId(@PathVariable Long id) {
		return departamentoService.findById(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/departamentos/bloque/{bloqueId}")
	public List<Departamento> listarPorBloque(@PathVariable Long bloqueId) {
		return departamentoService.findByBloqueId(bloqueId);
	}

	@PostMapping("/departamentos")
	public ResponseEntity<Departamento> crear(@Valid @RequestBody Departamento departamento) {

		if (departamento.getBloque() == null || departamento.getBloque().getId() == null) {
			return ResponseEntity.badRequest().build();
		}

		Bloque bloque = bloqueService.findById(departamento.getBloque().getId()).orElse(null);

		if (bloque == null) {
			return ResponseEntity.badRequest().build();
		}

		departamento.setId(null);
		departamento.setBloque(bloque);

		if (departamentoService.existsByNumeroDepartamento(departamento.getNumeroDepartamento())) {
			return ResponseEntity.status(409).build();
		}
		
		Departamento guardado = departamentoService.save(departamento);

		URI ubicacion = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(guardado.getId())
				.toUri();

		return ResponseEntity.created(ubicacion).body(guardado);
	}

	@PutMapping("/departamentos/{id}")
	public ResponseEntity<Departamento> actualizar(@PathVariable Long id, @Valid @RequestBody Departamento departamento) {

		if (departamento.getBloque() == null || departamento.getBloque().getId() == null) {
			return ResponseEntity.badRequest().build();
		}

		Bloque bloque = bloqueService.findById(departamento.getBloque().getId()).orElse(null);

		if (bloque == null) {
			return ResponseEntity.badRequest().build();
		}

		return departamentoService.findById(id)
				.map(actual -> {

					actual.setNumeroDepartamento(departamento.getNumeroDepartamento());
					actual.setDescripcionDepartamento(departamento.getDescripcionDepartamento());
					actual.setBloque(bloque);

					return ResponseEntity.ok(departamentoService.save(actual));
				})
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/departamentos/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {

		if (departamentoService.findById(id).isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		if (!condominioService.findByDepartamentoId(id).isEmpty()) {
			return ResponseEntity.status(409).build();
		}

		if (pagoService.existsByDepartamentoId(id)) {
			return ResponseEntity.status(409).build();
		}

		departamentoService.delete(id);

		return ResponseEntity.noContent().build();
	}
}