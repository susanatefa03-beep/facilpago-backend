package ista.M4A.controllers;

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

import ista.M4A.models.entity.Bloque;
import ista.M4A.models.service.IBloqueService;
import ista.M4A.models.service.IDepartamentoService;
import ista.M4A.models.service.IPagoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {
	    "http://localhost:4200",
	    "https://facilpago-frontend.onrender.com"
	})
public class BloqueRestController {

	private final IBloqueService bloqueService;
	private final IDepartamentoService departamentoService;
	private final IPagoService pagoService;

	public BloqueRestController(IBloqueService bloqueService, IDepartamentoService departamentoService, IPagoService pagoService) {
		this.bloqueService = bloqueService;
		this.departamentoService = departamentoService;
		this.pagoService = pagoService;
	}

	@GetMapping("/bloques")
	public List<Bloque> listar() {
		return bloqueService.findAll();
	}

	@GetMapping("/bloques/{id}")
	public ResponseEntity<Bloque> buscarPorId(@PathVariable Long id) {
		return bloqueService.findById(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping("/bloques")
	public ResponseEntity<Bloque> crear(@Valid @RequestBody Bloque bloque) {

		if (bloqueService.existsByNumeroBloque(bloque.getNumeroBloque())) {
			return ResponseEntity.status(409).build();
		}

		bloque.setId(null);
		Bloque bloqueNuevo = bloqueService.save(bloque);

		return ResponseEntity.ok(bloqueNuevo);
	}

	@PutMapping("/bloques/{id}")
	public ResponseEntity<Bloque> actualizar(@PathVariable Long id, @Valid @RequestBody Bloque bloque) {

		if (bloqueService.existsByNumeroBloqueAndIdNot(bloque.getNumeroBloque(), id)) {
			return ResponseEntity.status(409).build();
		}

		return bloqueService.findById(id)
				.map(actual -> {

					actual.setNumeroBloque(bloque.getNumeroBloque());
					actual.setDescripcionBloque(bloque.getDescripcionBloque());
					actual.setUbicacion(bloque.getUbicacion());

					return ResponseEntity.ok(bloqueService.save(actual));
				})
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/bloques/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {

		if (bloqueService.findById(id).isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		if (!departamentoService.findByBloqueId(id).isEmpty()) {
			return ResponseEntity.status(409).build();
		}

		if (pagoService.existsByBloqueId(id)) {
			return ResponseEntity.status(409).build();
		}

		bloqueService.delete(id);

		return ResponseEntity.noContent().build();
	}
}