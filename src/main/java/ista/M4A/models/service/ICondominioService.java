package ista.M4A.models.service;

import java.util.List;
import java.util.Optional;

import ista.M4A.models.entity.Condominio;

public interface ICondominioService {

	List<Condominio> findAll();

	Optional<Condominio> findById(Long id);

	Condominio save(Condominio condominio);

	void delete(Long id);

	List<Condominio> findByDepartamentoId(Long departamentoId);
	
	boolean existsByCedulaCondominio(String cedulaCondominio);

	boolean existsByCedulaCondominioAndIdNot(String cedulaCondominio, Long id);
}