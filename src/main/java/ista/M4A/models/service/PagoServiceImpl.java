package ista.M4A.models.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ista.M4A.models.dao.IPagoDao;
import ista.M4A.models.entity.Pago;

@Service
public class PagoServiceImpl implements IPagoService {

	private final IPagoDao pagoDao;

	public PagoServiceImpl(IPagoDao pagoDao) {
		this.pagoDao = pagoDao;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Pago> findAll() {
		return pagoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Pago> findById(Long id) {
		return pagoDao.findById(id);
	}

	@Override
	@Transactional
	public Pago save(Pago pago) {
		return pagoDao.save(pago);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		pagoDao.deleteById(id);
	}

	@Override
	public boolean existsByBloqueId(Long bloqueId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existsByDepartamentoId(Long departamentoId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existsByCondominioId(Long condominioId) {
		// TODO Auto-generated method stub
		return false;
	}
}