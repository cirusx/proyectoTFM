package com.esei.rest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CustomExceptionMapper implements ExceptionMapper<RuntimeException> {

	@Override
	public Response toResponse(RuntimeException exception) {
		System.err.println("exception");
		exception.printStackTrace();
		if (exception instanceof WebApplicationException) {
			return ((WebApplicationException) exception).getResponse();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("x-reason", exception.getMessage()).entity("Internal error").type("text/plain").build();
		}
	}
	

}
