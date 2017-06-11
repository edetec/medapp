package io.github.edetec.medical.dao;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.hibernate.Session;
import org.hibernate.Transaction;

import io.github.edetec.medical.exception.BussnessException;
import io.github.edetec.medical.model.entity.MedicalSpecialty;

public class MedicalSpecialtyDao extends AbstractDao {

	public void save(MedicalSpecialty specialty) throws BussnessException {
		Session session = openSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.saveOrUpdate(specialty);
			transaction.commit();
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			throw new BussnessException(e.getMessage());
		}

		session.close();
	}

	public List<MedicalSpecialty> listAll() {
		return openSession().createQuery("from MedicalSpecialty").getResultList();
	}

	public void delete(Long id) {
		Session session = openSession();
		MedicalSpecialty specialty = session.load(MedicalSpecialty.class, id);
		session.getTransaction().begin();
		session.delete(specialty);
		session.getTransaction().commit();
	}

	public MedicalSpecialty get(Long id) {
		return openSession().get(MedicalSpecialty.class, id);
	}

	public List<MedicalSpecialty> listByDescription(String description) {
		return openSession().createQuery("from MedicalSpecialty s Where lower(s.description) like :description")
				.setParameter("description", description.toLowerCase() + "%").getResultList();
	}
}
