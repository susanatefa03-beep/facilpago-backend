package ista.M4A.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "bloques")
public class Bloque {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "El número del bloque es obligatorio")
	@Column(name = "numero_bloque", nullable = false, unique = true)
	private String numeroBloque;

	@NotBlank(message = "La descripción del bloque es obligatoria")
	@Column(name = "descripcion_bloque", nullable = false)
	private String descripcionBloque;

	@NotBlank(message = "La ubicación es obligatoria")
	@Column(nullable = false)
	private String ubicacion;

	public Bloque() {
	}

	public Bloque(String numeroBloque, String descripcionBloque, String ubicacion) {
		this.numeroBloque = numeroBloque;
		this.descripcionBloque = descripcionBloque;
		this.ubicacion = ubicacion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroBloque() {
		return numeroBloque;
	}

	public void setNumeroBloque(String numeroBloque) {
		this.numeroBloque = numeroBloque;
	}

	public String getDescripcionBloque() {
		return descripcionBloque;
	}

	public void setDescripcionBloque(String descripcionBloque) {
		this.descripcionBloque = descripcionBloque;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
}