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
import io.github.edetec.medical.model.MedicBus;
import io.github.edetec.medical.model.entity.Medic;
import io.github.edetec.medical.model.entity.MedicalSpecialty;

@Path("medic")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MedicService {
	private MedicBus bus = new MedicBus();

	@GET
	@Path("{id: \\d+}/specialties/")
	public List<MedicalSpecialty> fetchSpecialtiesByMedic(@PathParam("id") Long id) {
		Medic medic = bus.fetchById(id);
		if (medic == null)
			throw new WebApplicationException("Medic not found", Response.Status.NOT_FOUND);
		return medic.getSpecialties();
	}

	@GET
	public List<Medic> fetchByName(@QueryParam("ordered") String ordered, @QueryParam("name") String name) {
		List<Medic> list;
		if (name == null) {
			list = bus.fetchAll(ordered);
		}else{
			list = bus.fetchByName(ordered, name);
		}
		
		// Fix @JsonIgnore don't working in WRITE_ONLY
		for(Medic medic : list){
			medic.setSpecialties(null);
		}
		
		return list;
	}

	@POST
	public Medic save(Medic medic) {
		try {
			bus.insert(medic);
		} catch (BussnessException e) {
			throw new WebApplicationException(e.getMessage(), Response.Status.BAD_REQUEST);
		}
		return medic;
	}

	@PUT
	@Path("{id: \\d+}")
	public Medic update(@PathParam("id") Long id, Medic medic) {
		try {
			bus.update(medic);
		} catch (BussnessException e) {
			throw new WebApplicationException(e.getMessage(), Response.Status.BAD_REQUEST);
		}
		return medic;
	}

	@GET
	@Path("{id: \\d+}")
	public Medic get(@PathParam("id") Long id) {
		return bus.fetchById(id);
	}

	@DELETE
	@Path("{id: \\d+}")
	public void delete(@PathParam("id") Long id) {
		bus.delete(id);

	}
}
