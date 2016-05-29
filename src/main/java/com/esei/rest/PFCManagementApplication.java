package com.esei.rest;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;



@ApplicationPath("/rest/*")
public class PFCManagementApplication extends ResourceConfig {
	
	

	public PFCManagementApplication() {
		super();

		this.packages("com.esei.rest");
		register(JacksonFeature.class);
		register(MultiPartFeature.class);
	}

}
