package ista.M4A.models.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ista.M4A.models.dao.IDepartamentoDao;
import ista.M4A.models.entity.Departamento;

@Service
public class DepartamentoServiceImpl implements IDepartamentoService {

	private final IDepartamentoDao departamentoDao;

	public DepartamentoServiceImpl(IDepartamentoDao departamentoDao) {
		this.departamentoDao = departamentoDao;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Departamento> findAll() {
		return departamentoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Departamento> findById(Long id) {
		return departamentoDao.findById(id);
	}

	@Override
	@Transactional
	public Departamento save(Departamento departamento) {
		return departamentoDao.save(departamento);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		departamentoDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Departamento> findByBloqueId(Long bloqueId) {
		return departamentoDao.findByBloqueIdOrderByNumeroDepartamentoAsc(bloqueId);
	}
	
	@Override
	public boolean existsByNumeroDepartamento(String numeroDepartamento) {
		return departamentoDao.existsByNumeroDepartamento(numeroDepartamento);
	}

	@Override
	public boolean existsByNumeroDepartamentoAndIdNot(String numeroDepartamento, Long id) {
		return departamentoDao.existsByNumeroDepartamentoAndIdNot(numeroDepartamento, id);
	}
}