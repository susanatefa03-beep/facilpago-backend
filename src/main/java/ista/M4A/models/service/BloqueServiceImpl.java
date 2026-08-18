package ista.M4A.models.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ista.M4A.models.dao.IBloqueDao;
import ista.M4A.models.entity.Bloque;

@Service
public class BloqueServiceImpl implements IBloqueService {

	private final IBloqueDao bloqueDao;

	public BloqueServiceImpl(IBloqueDao bloqueDao) {
		this.bloqueDao = bloqueDao;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Bloque> findAll() {
		return bloqueDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Bloque> findById(Long id) {
		return bloqueDao.findById(id);
	}

	@Override
	@Transactional
	public Bloque save(Bloque bloque) {
		return bloqueDao.save(bloque);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		bloqueDao.deleteById(id);
	}
	
	@Override
	public boolean existsByNumeroBloque(String numeroBloque) {
		return bloqueDao.existsByNumeroBloque(numeroBloque);
	}

	@Override
	public boolean existsByNumeroBloqueAndIdNot(String numeroBloque, Long id) {
		return bloqueDao.existsByNumeroBloqueAndIdNot(numeroBloque, id);
	}
}