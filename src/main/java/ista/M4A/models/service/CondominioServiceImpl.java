package ista.M4A.models.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ista.M4A.models.dao.ICondominioDao;
import ista.M4A.models.entity.Condominio;

@Service
public class CondominioServiceImpl implements ICondominioService {

	private final ICondominioDao condominioDao;

	public CondominioServiceImpl(ICondominioDao condominioDao) {
		this.condominioDao = condominioDao;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Condominio> findAll() {
		return condominioDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Condominio> findById(Long id) {
		return condominioDao.findById(id);
	}

	@Override
	@Transactional
	public Condominio save(Condominio condominio) {
		return condominioDao.save(condominio);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		condominioDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Condominio> findByDepartamentoId(Long departamentoId) {
		return condominioDao.findByDepartamentoId(departamentoId);
	}
	
	@Override
	public boolean existsByCedulaCondominio(String cedulaCondominio) {
		return condominioDao.existsByCedulaCondominio(cedulaCondominio);
	}

	@Override
	public boolean existsByCedulaCondominioAndIdNot(String cedulaCondominio, Long id) {
		return condominioDao.existsByCedulaCondominioAndIdNot(cedulaCondominio, id);
	}
}