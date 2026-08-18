package ista.M4A.models.service;

import java.util.List;
import java.util.Optional;

import ista.M4A.models.entity.Departamento;

public interface IDepartamentoService {

	List<Departamento> findAll();

	Optional<Departamento> findById(Long id);

	Departamento save(Departamento departamento);

	void delete(Long id);

	List<Departamento> findByBloqueId(Long bloqueId);
	
	boolean existsByNumeroDepartamento(String numeroDepartamento);

	boolean existsByNumeroDepartamentoAndIdNot(String numeroDepartamento, Long id);
}