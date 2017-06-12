package io.github.edetec.medical.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
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

import io.github.edetec.medical.dao.MedicDao;
import io.github.edetec.medical.dao.MedicalSpecialtyDao;
import io.github.edetec.medical.exception.BussnessException;
import io.github.edetec.medical.model.entity.Medic;
import io.github.edetec.medical.model.entity.MedicalSpecialty;

public class MedicServiceTest extends JerseyTest {
	private static Medic medic1;
	private static MedicDao dao;
	private static MedicalSpecialtyDao specialtyDao;
	private static MedicalSpecialty specialty1;
	private static MedicalSpecialty specialty2;

	@Override
	public Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(MedicService.class);
	}

	@BeforeClass
	public static void init() throws BussnessException {
		dao = new MedicDao();
		specialtyDao = new MedicalSpecialtyDao();

		specialty1 = new MedicalSpecialty();
		specialty1.setDescription("Specialty 1");
		specialty2 = new MedicalSpecialty();
		specialty2.setDescription("Specialty 2");

		specialtyDao.save(specialty1);
		specialtyDao.save(specialty2);

		medic1 = new Medic();
		medic1.setName("Ana");
		medic1.setPhone("48 98888 6666");
		medic1.setCpf("99999999999");
		medic1.setCrm("12334");
		medic1.setSpecialties(new ArrayList<MedicalSpecialty>());
		medic1.getSpecialties().add(specialty1);
		medic1.getSpecialties().add(specialty2);
		dao.save(medic1);
	}

	@Test
	public void testFetchAll() {
		Response output = target("/medic").request().get();
		assertEquals("should return status 200", 200, output.getStatus());
		List<Medic> medics = output.readEntity(new GenericType<List<Medic>>() {
		});
		assertTrue("Should return list", medics.size() > 0);
	}
	
	@Test
	public void testFetchSpecialties() {
		Response output = target("/medic").path(medic1.getId().toString() + "/specialties/").request().get();
		assertEquals("should return status 200", 200, output.getStatus());
		List<MedicalSpecialty> specialties = output.readEntity(new GenericType<List<MedicalSpecialty>>() {
		});
		assertEquals("Should return list of specialties", specialties.size(), medic1.getSpecialties().size());
	}

	@Test
	public void testFetchByName() {
		Response output = target("/medic").queryParam("name", "An").request().get();
		assertEquals("should return status 200", 200, output.getStatus());
		List<Medic> medics = output.readEntity(new GenericType<List<Medic>>() {
		});
		assertTrue("Should return list", medics.size() > 0);
	}

	@Test
	public void testFetchById() throws BussnessException {
		Response output = target("/medic/").path(medic1.getId().toString()).request().get();
		assertEquals("Should return status 200", 200, output.getStatus());
		Medic medic = (Medic) output.readEntity(Medic.class);
		assertNotNull("Shold return medic with id",  medic.getId());
		assertEquals("Should return a medic with same name",  medic.getName(), medic1.getName());
		assertEquals("Should return a medic with same phone",  medic.getPhone(), medic1.getPhone());
		assertEquals("Should return a medic with same CPF",  medic.getCpf(), medic1.getCpf());
		assertEquals("Should return a medic with same CRM",  medic.getCrm(), medic1.getCrm());
	}

	@Test
	public void testDeleteMedic() throws BussnessException {
		Medic medic = new Medic();
		medic.setName("Zé");
		medic.setPhone("48 98888 6666");
		medic.setCpf("11111111111");
		medic.setCrm("4567");
		medic.setSpecialties(new ArrayList<MedicalSpecialty>());
		medic.getSpecialties().add(specialty1);
		dao.save(medic);

		Response output = target("/medic/").path(medic.getId().toString()).request().delete();
		assertEquals("Should return status 204", 204, output.getStatus());

		Medic medicReturned = dao.findById(medic.getId());
		assertEquals("Shold return medics", null, medicReturned);
	}

	@Test
	public void testUpdateMedic() throws BussnessException {
		Medic medic = new Medic();
		medic.setName("Maria");
		medic.setPhone("48 98888 6666");
		medic.setCpf("22222222222");
		medic.setCrm("78945");
		medic.setSpecialties(new ArrayList<MedicalSpecialty>());
		medic.getSpecialties().add(specialty2);
		dao.save(medic);

		medic.setPhone("48 5555 5555");

		Response output = target("/medic/").path(medic.getId().toString()).request()
				.put(Entity.entity(medic, MediaType.APPLICATION_JSON));
		assertEquals("Should return status 200", 200, output.getStatus());

		Medic medicReturned = (Medic) output.readEntity(Medic.class);
		assertNotNull("Shold return medic with id",  medicReturned.getId());
		assertEquals("Should return a medic with same name",  medic.getName(), medicReturned.getName());
		assertEquals("Should return a medic with same phone",  medic.getPhone(), medicReturned.getPhone());
		assertEquals("Should return a medic with same CPF",  medic.getCpf(), medicReturned.getCpf());
		assertEquals("Should return a medic with same CRM",  medic.getCrm(), medicReturned.getCrm());
	}

	@Test
	public void testInsertMedic() throws BussnessException {
		Medic medic = new Medic();
		medic.setName("Manuela");
		medic.setPhone("48 98888 3333");
		medic.setCpf("33333333333");
		medic.setCrm("12945");
		medic.setSpecialties(new ArrayList<MedicalSpecialty>());
		medic.getSpecialties().add(specialty1);
		medic.getSpecialties().add(specialty2);

		Response output = target("/medic/").request().post(Entity.entity(medic, MediaType.APPLICATION_JSON));
		assertEquals("Should return status 200", 200, output.getStatus());

		Medic medicReturned = (Medic) output.readEntity(Medic.class);
		assertNotNull("Shold return medic with id",  medicReturned.getId());
		assertEquals("Should return a medic with same name",  medic.getName(), medicReturned.getName());
		assertEquals("Should return a medic with same phone",  medic.getPhone(), medicReturned.getPhone());
		assertEquals("Should return a medic with same CPF",  medic.getCpf(), medicReturned.getCpf());
		assertEquals("Should return a medic with same CRM",  medic.getCrm(), medicReturned.getCrm());
	}

}
