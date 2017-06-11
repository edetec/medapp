package io.github.edetec.medical.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.BeforeClass;
import org.junit.Test;

import io.github.edetec.medical.dao.MedicalSpecialtyDao;
import io.github.edetec.medical.exception.BussnessException;
import io.github.edetec.medical.model.entity.MedicalSpecialty;
import io.github.edetec.medical.model.entity.Patient;

public class MedicalSpecialtyServiceTest extends JerseyTest {
	private static MedicalSpecialty specialty1;
	private static MedicalSpecialtyDao dao;

	@Override
	public Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(MedicalSpecialtyService.class);
	}

	@BeforeClass
	public static void init() throws BussnessException {
		specialty1 = new MedicalSpecialty();
		specialty1.setDescription("Dermatologist");
		dao = new MedicalSpecialtyDao();
		dao.save(specialty1);
	}

	@Test
	public void testFetchAll() {
		Response output = target("/specialty").request().get();
		assertEquals("should return status 200", 200, output.getStatus());
		List<MedicalSpecialty> specialties = output.readEntity(new GenericType<List<MedicalSpecialty>>() {
		});
		assertTrue("Should return list", specialties.size() > 0);
	}

	@Test
	public void testDeleteMedicalSpecialty() throws BussnessException {
		MedicalSpecialty specialty = new MedicalSpecialty();
		specialty.setDescription("Otolaryngologist");
		dao.save(specialty);

		Response output = target("/specialty/").path(specialty.getId().toString()).request().delete();
		assertEquals("Should return status 204", 204, output.getStatus());

		MedicalSpecialty specialtyDeleted = dao.get(specialty.getId());
		assertEquals("Shold not return specialty", null, specialtyDeleted);
	}

	@Test
	public void testInsertMedicalSpecialty() throws BussnessException {
		MedicalSpecialty specialty = new MedicalSpecialty();
		specialty.setDescription("Ophthalmologist");
		Response output = target("/specialty/").request().post(Entity.entity(specialty, MediaType.APPLICATION_JSON));
		assertEquals("Should return status 200", 200, output.getStatus());

		MedicalSpecialty specialtyReturned = (MedicalSpecialty) output.readEntity(MedicalSpecialty.class);
		assertNotNull("Shold return specialty with id", specialtyReturned.getId());
		assertEquals("Shold return same specialty description", specialty.getDescription(),
				specialtyReturned.getDescription());
	}

	@Test
	public void testUpdatePatient() throws BussnessException {
		MedicalSpecialty specialty = new MedicalSpecialty();
		specialty.setDescription("Cardiologist");
		dao.save(specialty);

		specialty.setDescription("Cardiologist updated");

		Response output = target("/specialty/").path(specialty.getId().toString()).request()
				.put(Entity.entity(specialty, MediaType.APPLICATION_JSON));
		assertEquals("Should return status 200", 200, output.getStatus());

		MedicalSpecialty specialtyReturned = (MedicalSpecialty) output.readEntity(MedicalSpecialty.class);
		assertNotNull("Shold return specialty with id", specialtyReturned.getId());
		assertEquals("Shold return same specialty description", specialty.getDescription(),
				specialtyReturned.getDescription());
	}
}
