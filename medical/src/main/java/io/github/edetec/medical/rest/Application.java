package io.github.edetec.medical.rest;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import io.github.edetec.medical.util.JpaUtil;

@ApplicationPath("api")
public class Application extends ResourceConfig {
	public Application() {
		packages("io.github.edetec.medical.service");
	}
}
