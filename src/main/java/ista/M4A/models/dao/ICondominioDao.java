package ista.M4A.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ista.M4A.models.entity.Condominio;

public interface ICondominioDao extends JpaRepository<Condominio, Long> {

	@Query("select distinct c from Condominio c join c.departamentos d where d.id = :departamentoId")
	List<Condominio> findByDepartamentoId(@Param("departamentoId") Long departamentoId);
	
	boolean existsByCedulaCondominio(String cedulaCondominio);

	boolean existsByCedulaCondominioAndIdNot(String cedulaCondominio, Long id);
}