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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.github.edetec.medical.exception.BussnessException;
import io.github.edetec.medical.model.MedicalSpecialtyBus;
import io.github.edetec.medical.model.entity.MedicalSpecialty;

@Path("specialty")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MedicalSpecialtyService {
	private MedicalSpecialtyBus bus = new MedicalSpecialtyBus();
	
	@GET
	public List<MedicalSpecialty> fetchAll(){
		return bus.fetchAll();
	}

	@POST
	public MedicalSpecialty save(MedicalSpecialty specialty) {
		try {
			bus.save(specialty);
		} catch (BussnessException e) {
			throw new WebApplicationException(e.getMessage(), Response.Status.BAD_REQUEST);
		}
		return specialty;
	}
	
	@PUT
	@Path("{id: \\d+}")
	public MedicalSpecialty update(@PathParam("id") Long id, MedicalSpecialty specialty) {
		try {
			bus.save(specialty);
		} catch (BussnessException e) {
			throw new WebApplicationException(e.getMessage(), Response.Status.BAD_REQUEST);
		}
		return specialty;
	}
	
	@DELETE
	@Path("{id: \\d+}")
	public void delete(@PathParam("id") Long id){
		bus.delete(id);
		
	}
}
