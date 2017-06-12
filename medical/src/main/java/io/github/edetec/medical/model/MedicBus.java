package io.github.edetec.medical.model;

import java.util.List;

import io.github.edetec.medical.dao.MedicDao;
import io.github.edetec.medical.exception.BussnessException;
import io.github.edetec.medical.model.entity.Medic;
import io.github.edetec.medical.model.entity.MedicalSpecialty;
import io.github.edetec.medical.util.CpfValidator;

public class MedicBus {

	private MedicDao dao;

	public MedicBus() {
		dao = new MedicDao();
	}

	public List<Medic> fetchAll(String ordered) {
		return dao.list(ordered);
	}

	public List<Medic> fetchByName(String ordered, String name) {
		return dao.listByName(ordered, name);
	}

	public void insert(Medic medic) throws BussnessException {
		if (medic.getCpf() == null || !CpfValidator.isValid(medic.getCpf())) {
			throw new BussnessException("CPF attribute is invalid");
		}
		
		if(dao.exists(medic.getCpf())){
			throw new BussnessException("This CPF has already been registered");
		}
		dao.save(medic);
	}

	public void update(Medic medic) throws BussnessException {
		if (medic.getCpf() == null || !CpfValidator.isValid(medic.getCpf())) {
			throw new BussnessException("CPF attribute is invalid");
		}
		
		dao.save(medic);

	}

	public Medic fetchById(Long id) {
		return dao.findById(id);
	}

	public void delete(Long id) {
		dao.delete(id);
	}

}
