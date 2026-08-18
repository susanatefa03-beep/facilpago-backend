package ista.M4A.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ista.M4A.models.entity.Pago;

public interface IPagoDao extends JpaRepository<Pago, Long> {
	
	boolean existsByBloqueId(Long bloqueId);

	boolean existsByDepartamentoId(Long departamentoId);

	boolean existsByCondominioId(Long condominioId);

}