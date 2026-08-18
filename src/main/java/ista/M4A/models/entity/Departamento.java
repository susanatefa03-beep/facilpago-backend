package ista.M4A.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "departamentos")
public class Departamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "El número del departamento es obligatorio")
	@Column(name = "numero_departamento", nullable = false, unique = true)
	private String numeroDepartamento;

	@NotBlank(message = "La descripción del departamento es obligatoria")
	@Column(name = "descripcion_departamento", nullable = false)
	private String descripcionDepartamento;

	@NotNull(message = "El bloque es obligatorio")
	@ManyToOne
	@JoinColumn(name = "bloque_id", nullable = false)
	private Bloque bloque;

	public Departamento() {
	}

	public Departamento(String numeroDepartamento, String descripcionDepartamento, Bloque bloque) {
		this.numeroDepartamento = numeroDepartamento;
		this.descripcionDepartamento = descripcionDepartamento;
		this.bloque = bloque;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroDepartamento() {
		return numeroDepartamento;
	}

	public void setNumeroDepartamento(String numeroDepartamento) {
		this.numeroDepartamento = numeroDepartamento;
	}

	public String getDescripcionDepartamento() {
		return descripcionDepartamento;
	}

	public void setDescripcionDepartamento(String descripcionDepartamento) {
		this.descripcionDepartamento = descripcionDepartamento;
	}

	public Bloque getBloque() {
		return bloque;
	}

	public void setBloque(Bloque bloque) {
		this.bloque = bloque;
	}
}