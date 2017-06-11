package io.github.edetec.medical.model;

import java.util.List;

import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;

import io.github.edetec.medical.dao.PatientDao;
import io.github.edetec.medical.exception.BussnessException;
import io.github.edetec.medical.model.entity.Patient;
import io.github.edetec.medical.util.CpfValidator;

public class PatientBus {
	private PatientDao dao;

	public PatientBus() {
		dao = new PatientDao();
	}

	public List<Patient> fetchAll() {
		return dao.list();
	}

	public void insert(Patient patient) throws BussnessException {

		if (patient.getCpf() == null || !CpfValidator.isValid(patient.getCpf())) {
			throw new BussnessException("CPF attribute is invalid");
		}
		
		if(dao.findByCpf(patient.getCpf()) != null){
			throw new BussnessException("This CPF has already been registered");
		}

		dao.save(patient);
	}

	public void update(Patient patient) throws BussnessException {

		if (patient.getCpf() == null || !CpfValidator.isValid(patient.getCpf())) {
			throw new BussnessException("CPF attribute is invalid");
		}

		dao.save(patient);
	}

	public Patient fetchByCpf(String cpf) {
		return dao.findByCpf(cpf);
	}

	public List<Patient> fetchByName(String name) {
		return dao.listByName(name);
	}

	public void delete(String cpf) {
		dao.delete(cpf);
	}

}
