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
import ista.M4A.models.entity.Condominio;
import ista.M4A.models.entity.Departamento;
import ista.M4A.models.entity.Pago;
import ista.M4A.models.service.IBloqueService;
import ista.M4A.models.service.ICondominioService;
import ista.M4A.models.service.IDepartamentoService;
import ista.M4A.models.service.IPagoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class PagoRestController {

	private final IPagoService pagoService;
	private final IBloqueService bloqueService;
	private final IDepartamentoService departamentoService;
	private final ICondominioService condominioService;

	public PagoRestController(IPagoService pagoService, IBloqueService bloqueService,
			IDepartamentoService departamentoService, ICondominioService condominioService) {

		this.pagoService = pagoService;
		this.bloqueService = bloqueService;
		this.departamentoService = departamentoService;
		this.condominioService = condominioService;
	}

	@GetMapping("/pagos")
	public List<Pago> listar() {
		return pagoService.findAll();
	}

	@GetMapping("/pagos/{id}")
	public ResponseEntity<Pago> buscarPorId(@PathVariable Long id) {
		return pagoService.findById(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping("/pagos")
	public ResponseEntity<Pago> crear(@Valid @RequestBody Pago pago) {

		if (!asignarRelaciones(pago)) {
			return ResponseEntity.badRequest().build();
		}

		pago.setId(null);

		Pago guardado = pagoService.save(pago);

		URI ubicacion = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(guardado.getId())
				.toUri();

		return ResponseEntity.created(ubicacion).body(guardado);
	}

	@PutMapping("/pagos/{id}")
	public ResponseEntity<Pago> actualizar(@PathVariable Long id, @Valid @RequestBody Pago pago) {

		if (!asignarRelaciones(pago)) {
			return ResponseEntity.badRequest().build();
		}

		return pagoService.findById(id)
				.map(actual -> {

					actual.setFechaPago(pago.getFechaPago());
					actual.setAnioMesPago(pago.getAnioMesPago());
					actual.setValorPagoAlicuota(pago.getValorPagoAlicuota());
					actual.setValorPagoConsumoServicios(pago.getValorPagoConsumoServicios());
					actual.setBloque(pago.getBloque());
					actual.setDepartamento(pago.getDepartamento());
					actual.setCondominio(pago.getCondominio());

					return ResponseEntity.ok(pagoService.save(actual));
				})
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/pagos/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {

		if (pagoService.findById(id).isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		pagoService.delete(id);

		return ResponseEntity.noContent().build();
	}

	private boolean asignarRelaciones(Pago pago) {

		if (pago.getBloque() == null || pago.getBloque().getId() == null) {
			return false;
		}

		if (pago.getDepartamento() == null || pago.getDepartamento().getId() == null) {
			return false;
		}

		if (pago.getCondominio() == null || pago.getCondominio().getId() == null) {
			return false;
		}

		Bloque bloque = bloqueService.findById(pago.getBloque().getId()).orElse(null);

		Departamento departamento = departamentoService.findById(pago.getDepartamento().getId()).orElse(null);

		Condominio condominio = condominioService.findById(pago.getCondominio().getId()).orElse(null);

		if (bloque == null || departamento == null || condominio == null) {
			return false;
		}

		if (!departamento.getBloque().getId().equals(bloque.getId())) {
			return false;
		}

		boolean pertenece = condominio.getDepartamentos().stream()
				.anyMatch(d -> d.getId().equals(departamento.getId()));

		if (!pertenece) {
			return false;
		}

		pago.setBloque(bloque);
		pago.setDepartamento(departamento);
		pago.setCondominio(condominio);

		return true;
	}
}