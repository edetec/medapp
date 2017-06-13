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
import io.github.edetec.medical.model.MedicalSpecialtyBus;
import io.github.edetec.medical.model.entity.Medic;
import io.github.edetec.medical.model.entity.MedicalSpecialty;

@Path("specialty")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MedicalSpecialtyService {
	private MedicalSpecialtyBus bus = new MedicalSpecialtyBus();

	@GET
	@Path("{id}/medics/")
	public List<Medic> fetMedicsBySpecialty(@PathParam("id") Long id) {
		MedicalSpecialty specialty = bus.findById(id);
		if (specialty == null){
			throw new WebApplicationException("Medical Specialty not found", Response.Status.NOT_FOUND);
		}

		// Fix @JsonIgnore don't working in WRITE_ONLY
		for(Medic medic: specialty.getMedics()){
			medic.setSpecialties(null);
		}
		
		return specialty.getMedics();
	}

	@GET
	public List<MedicalSpecialty> fetchAll(@QueryParam("description") String description) {
		if (description == null) {
			return bus.fetchAll();
		}

		return bus.fetchByDescription(description);
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
	public void delete(@PathParam("id") Long id) {
		bus.delete(id);

	}
}
