package ista.M4A.models.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "pagos")
public class Pago {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "La fecha de pago es obligatoria")
	@Column(name = "fecha_pago", nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaPago;

	@NotBlank(message = "El año y mes son obligatorios")
	@Pattern(regexp = "\\d{4}-(0[1-9]|1[0-2])", message = "El formato debe ser AAAA-MM")
	@Column(name = "anio_mes_pago", nullable = false)
	private String anioMesPago;

	@NotNull(message = "El valor de la alícuota es obligatorio")
	@DecimalMin(value = "0.0", inclusive = true, message = "El valor de la alícuota no puede ser negativo")
	@Column(name = "valor_pago_alicuota", nullable = false)
	private BigDecimal valorPagoAlicuota;

	@NotNull(message = "El valor de servicios es obligatorio")
	@DecimalMin(value = "0.0", inclusive = true, message = "El valor de servicios no puede ser negativo")
	@Column(name = "valor_pago_consumo_servicios", nullable = false)
	private BigDecimal valorPagoConsumoServicios;

	@NotNull(message = "El bloque es obligatorio")
	@ManyToOne
	@JoinColumn(name = "bloque_id", nullable = false)
	private Bloque bloque;

	@NotNull(message = "El departamento es obligatorio")
	@ManyToOne
	@JoinColumn(name = "departamento_id", nullable = false)
	private Departamento departamento;

	@NotNull(message = "El condómino es obligatorio")
	@ManyToOne
	@JoinColumn(name = "condomino_id", nullable = false)
	private Condominio condominio;

	public Pago() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(LocalDate fechaPago) {
		this.fechaPago = fechaPago;
	}

	public String getAnioMesPago() {
		return anioMesPago;
	}

	public void setAnioMesPago(String anioMesPago) {
		this.anioMesPago = anioMesPago;
	}

	public BigDecimal getValorPagoAlicuota() {
		return valorPagoAlicuota;
	}

	public void setValorPagoAlicuota(BigDecimal valorPagoAlicuota) {
		this.valorPagoAlicuota = valorPagoAlicuota;
	}

	public BigDecimal getValorPagoConsumoServicios() {
		return valorPagoConsumoServicios;
	}

	public void setValorPagoConsumoServicios(BigDecimal valorPagoConsumoServicios) {
		this.valorPagoConsumoServicios = valorPagoConsumoServicios;
	}

	public Bloque getBloque() {
		return bloque;
	}

	public void setBloque(Bloque bloque) {
		this.bloque = bloque;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}
}