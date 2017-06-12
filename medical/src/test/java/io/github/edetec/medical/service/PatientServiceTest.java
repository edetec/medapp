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

import io.github.edetec.medical.dao.PatientDao;
import io.github.edetec.medical.exception.BussnessException;
import io.github.edetec.medical.model.entity.Patient;

public class PatientServiceTest extends JerseyTest {
	private static Patient patient1;
	private static PatientDao dao;

	@Override
	public Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(PatientService.class);
	}

	@BeforeClass
	public static void init() throws BussnessException {
		patient1 = new Patient();
		patient1.setCpf("99999999999");
		patient1.setName("Ana");
		patient1.setPhone("48 98888 6666");
		dao = new PatientDao();
		dao.save(patient1);
	}

	@Test
	public void testFetchAll() {
		Response output = target("/patient").request().get();
		assertEquals("should return status 200", 200, output.getStatus());
		List<Patient> patients = output.readEntity(new GenericType<List<Patient>>(){});
		assertTrue("Should return list", patients.size() > 0);
	}
	
	@Test
	public void testFetchByName() {
		Response output = target("/patient").queryParam("name", "An").request().get();
		assertEquals("should return status 200", 200, output.getStatus());
		List<Patient> patients = output.readEntity(new GenericType<List<Patient>>(){});
		assertTrue("Should return list", patients.size() > 0);
	}

	@Test
	public void testFetchByCpf() throws BussnessException {
		Response output = target("/patient/99999999999").request().get();
		assertEquals("Should return status 200", 200, output.getStatus());
		Patient patient = (Patient) output.readEntity(Patient.class);
		assertNotNull("Should return patient", patient);
		assertEquals("Should return equals values", patient1, patient);
	}

	@Test
	public void testFetchByCpfFail_DoesNotHaveDigit() {
		Response output = target("/patient/no-id-digit").request().get();
		assertEquals("Should return status 404", 404, output.getStatus());
	}

	@Test
	public void testDeletePatient() throws BussnessException {
		Patient patient = new Patient();
		patient.setCpf("11111111111");
		patient.setName("Zé");
		patient.setPhone("48 98888 6666");
		dao.save(patient);
		
		Response output = target("/patient/").path(patient.getCpf()).request().delete();
		assertEquals("Should return status 204", 204, output.getStatus());
		
		Patient patientCpf = dao.findByCpf(patient.getCpf());
		assertEquals("Shold return patients", null, patientCpf);
	}
	
	@Test
	public void testUpdatePatient() throws BussnessException {
		Patient patient = new Patient();
		patient.setCpf("22222222222");
		patient.setName("Maria");
		patient.setPhone("48 98888 6666");
		dao.save(patient);
		
		patient.setPhone("48 5555 5555");
		
		Response output = target("/patient/").path(patient.getCpf()).request()
				.put(Entity.entity(patient, MediaType.APPLICATION_JSON));
		assertEquals("Should return status 200", 200, output.getStatus());
		
		Patient patientReturned = (Patient) output.readEntity(Patient.class);
		assertEquals("Shold return patients", patient, patientReturned);
	}
	
	@Test
	public void testInsertPatient() throws BussnessException {
		Patient patient = new Patient();
		patient.setCpf("33333333333");
		patient.setName("Manuela");
		patient.setPhone("48 98888 3333");
		
		Response output = target("/patient/").request()
				.post(Entity.entity(patient, MediaType.APPLICATION_JSON));
		assertEquals("Should return status 200", 200, output.getStatus());
		
		Patient patientReturned = (Patient) output.readEntity(Patient.class);
		assertEquals("Shold return patients", patient, patientReturned);
	}

}
