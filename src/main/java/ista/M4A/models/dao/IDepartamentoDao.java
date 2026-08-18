package ista.M4A.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ista.M4A.models.entity.Departamento;

public interface IDepartamentoDao extends JpaRepository<Departamento, Long> {

	List<Departamento> findByBloqueIdOrderByNumeroDepartamentoAsc(Long bloqueId);

	boolean existsByNumeroDepartamento(String numeroDepartamento);

	boolean existsByNumeroDepartamentoAndIdNot(String numeroDepartamento, Long id);
}