package io.github.edetec.medical.dao;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;

import io.github.edetec.medical.exception.BussnessException;
import io.github.edetec.medical.model.entity.Medic;
import io.github.edetec.medical.model.entity.MedicalSpecialty;

public class MedicDao extends AbstractDao {

	public void save(Medic medic) throws BussnessException {
		Session session = openSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.saveOrUpdate(medic);
			transaction.commit();
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			throw new BussnessException(e.getMessage());
		}

		session.close();
	}

	public List<Medic> list(String ordered) {
		Order order = getOder(ordered);
		return openSession().createCriteria(Medic.class)
				.addOrder(order)
				.list();
	}

	public boolean exists(String cpf) {
		Medic medic = openSession().createQuery("From Medic m Where m.cpf = :cpf", Medic.class)
				.setParameter("cpf", cpf).uniqueResult();
		return medic != null;
	}

	public Medic findById(Long id) {
		return openSession().get(Medic.class, id);
	}

	@SuppressWarnings("deprecation")
	public List<Medic> listByName(String ordered, String name) {
		Order order = getOder(ordered);
		List list = openSession().createCriteria(Medic.class)
				.add(Property.forName("name").like(name + "%").ignoreCase())
				.addOrder(order)
				.list();
		return list;
	}

	private Order getOder(String ordered) {
		if(ordered !=  null && !ordered.isEmpty()){
			if(ordered.charAt(0) == '-'){
				return Property.forName(ordered.substring(1)).desc();
			}
			return Property.forName(ordered).asc();
		}
		return Property.forName("name").asc();
	}

	public void delete(Long id) {
		Session session = openSession();
		Medic medic = session.load(Medic.class, id);
		session.getTransaction().begin();
		session.delete(medic);
		session.getTransaction().commit();
	}

}
