package ista.M4A.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ista.M4A.models.entity.Bloque;

public interface IBloqueDao extends JpaRepository<Bloque, Long> {

	boolean existsByNumeroBloque(String numeroBloque);

	boolean existsByNumeroBloqueAndIdNot(String numeroBloque, Long id);
}