package ista.M4A.models.service;

import java.util.List;
import java.util.Optional;
import ista.M4A.models.entity.Bloque;

public interface IBloqueService {

	List<Bloque> findAll();

	Optional<Bloque> findById(Long id);

	Bloque save(Bloque bloque);

	void delete(Long id);

	boolean existsByNumeroBloque(String numeroBloque);

	boolean existsByNumeroBloqueAndIdNot(String numeroBloque, Long id);
}