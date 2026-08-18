package ista.M4A.models.service;

import java.util.List;
import java.util.Optional;

import ista.M4A.models.entity.Pago;

public interface IPagoService {

	List<Pago> findAll();

	Optional<Pago> findById(Long id);

	Pago save(Pago pago);

	void delete(Long id);
	
	boolean existsByBloqueId(Long bloqueId);

	boolean existsByDepartamentoId(Long departamentoId);

	boolean existsByCondominioId(Long condominioId);
}