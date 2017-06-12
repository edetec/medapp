package io.github.edetec.medical.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.github.edetec.medical.exception.BussnessException;
import io.github.edetec.medical.model.PatientBus;
import io.github.edetec.medical.model.entity.Patient;

@Path("patient")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PatientService {
	private PatientBus bus = new PatientBus();
	
	@GET
	public List<Patient> fetchByName(@QueryParam("name") String name){
		if(name == null){
			return bus.fetchAll();
		}
		return bus.fetchByName(name);
	}

	@POST
	public Patient save(Patient patient) {
		try {
			bus.insert(patient);
		} catch (BussnessException e) {
			throw new WebApplicationException(e.getMessage(), Response.Status.BAD_REQUEST);
		}
		return patient;
	}
	
	@PUT
	@Path("{cpf: \\d+}")
	public Patient update(@PathParam("cpf") String cpf, Patient patient) {
		try {
			bus.update(patient);
		} catch (BussnessException e) {
			throw new WebApplicationException(e.getMessage(), Response.Status.BAD_REQUEST);
		}
		return patient;
	}
	
	@GET
	@Path("{cpf: \\d+}")
	public Patient fetchByCpf(@PathParam("cpf") String cpf){
		return bus.fetchByCpf(cpf);
		
	}
	
	@DELETE
	@Path("{cpf: \\d+}")
	public void delete(@PathParam("cpf") String cpf){
		bus.delete(cpf);
		
	}
}
