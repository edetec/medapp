package io.github.edetec.medical.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolationException;

import org.hibernate.Session;
import org.hibernate.Transaction;

import io.github.edetec.medical.exception.BussnessException;
import io.github.edetec.medical.model.entity.Patient;

public class PatientDao extends AbstractDao {

	public void save(Patient patient) throws BussnessException {
		Session session = openSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.saveOrUpdate(patient);
			transaction.commit();
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			throw new BussnessException(e.getMessage());
		}

		session.close();
	}

	public List<Patient> list() {
		return openSession().createQuery("from Patient").getResultList();
	}

	public boolean exists(String cpf) {
		Patient patient = openSession().get(Patient.class, cpf);
		return patient != null;
	}

	public Patient findByCpf(String cpf) {
		return openSession().get(Patient.class, cpf);
	}

	public List<Patient> listByName(String name) {
		Session openSession = openSession();
		return openSession.createQuery("from Patient p Where lower(p.name) like :name")
				.setParameter("name", name.toLowerCase() + "%")
				.getResultList();
	}

	public void delete(String cpf) {
		Session session = openSession();
		Patient patient = session.load(Patient.class, cpf);
		session.getTransaction().begin();
		session.delete(patient);
		session.getTransaction().commit();
	}
}
