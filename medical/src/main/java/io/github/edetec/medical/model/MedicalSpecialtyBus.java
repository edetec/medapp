package io.github.edetec.medical.model;

import java.util.List;

import io.github.edetec.medical.dao.MedicalSpecialtyDao;
import io.github.edetec.medical.exception.BussnessException;
import io.github.edetec.medical.model.entity.MedicalSpecialty;

public class MedicalSpecialtyBus {
	private MedicalSpecialtyDao dao;

	public MedicalSpecialtyBus() {
		dao = new MedicalSpecialtyDao();
	}

	public List<MedicalSpecialty> fetchAll() {
		return dao.listAll();
	}

	public void save(MedicalSpecialty specialty) throws BussnessException {
		dao.save(specialty);
	}

	public void delete(Long id) {
		dao.delete(id);
		
	}

	public List<MedicalSpecialty> fetchByDescription(String description) {
		return dao.listByDescription(description);
	}

}
