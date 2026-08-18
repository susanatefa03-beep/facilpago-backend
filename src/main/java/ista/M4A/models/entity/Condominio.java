package ista.M4A.models.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "condominos")
public class Condominio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "La cédula es obligatoria")
	@Pattern(regexp = "\\d{10}", message = "La cédula debe contener 10 dígitos")
	@Column(name = "cedula_condominio", nullable = false, unique = true)
	private String cedulaCondominio;

	@NotBlank(message = "El nombre es obligatorio")
	@Column(name = "nombre_condomino", nullable = false)
	private String nombreCondomino;

	@NotBlank(message = "El apellido es obligatorio")
	@Column(name = "apellido_condomino", nullable = false)
	private String apellidoCondomino;

	@NotBlank(message = "El celular es obligatorio")
	@Pattern(regexp = "\\d{10}", message = "El celular debe contener 10 dígitos")
	@Column(name = "celular_condomino", nullable = false)
	private String celularCondomino;

	@NotBlank(message = "El teléfono es obligatorio")
	@Pattern(regexp = "\\d{7,10}", message = "El teléfono debe contener entre 7 y 10 dígitos")
	@Column(name = "telefono_condomino", nullable = false)
	private String telefonoCondomino;

	@Size(min = 1, message = "El condómino debe tener al menos un departamento")
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "condominos_departamentos",joinColumns = @JoinColumn(name = "condomino_id"),
		inverseJoinColumns = @JoinColumn(name = "departamento_id")
	)
	
	private Set<Departamento> departamentos = new HashSet<>();

	public Condominio() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCedulaCondominio() {
		return cedulaCondominio;
	}

	public void setCedulaCondominio(String cedulaCondominio) {
		this.cedulaCondominio = cedulaCondominio;
	}

	public String getNombreCondomino() {
		return nombreCondomino;
	}

	public void setNombreCondomino(String nombreCondomino) {
		this.nombreCondomino = nombreCondomino;
	}

	public String getApellidoCondomino() {
		return apellidoCondomino;
	}

	public void setApellidoCondomino(String apellidoCondomino) {
		this.apellidoCondomino = apellidoCondomino;
	}

	public String getCelularCondomino() {
		return celularCondomino;
	}

	public void setCelularCondomino(String celularCondomino) {
		this.celularCondomino = celularCondomino;
	}

	public String getTelefonoCondomino() {
		return telefonoCondomino;
	}

	public void setTelefonoCondomino(String telefonoCondomino) {
		this.telefonoCondomino = telefonoCondomino;
	}

	public Set<Departamento> getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(Set<Departamento> departamentos) {
		this.departamentos = departamentos;
	}
}