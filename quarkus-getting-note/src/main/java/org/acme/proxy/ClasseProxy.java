package org.acme.proxy;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@RegisterRestClient( baseUri = "http://localhost:8081/classe" )
public interface ClasseProxy {
    
    
    @GET
    @Path("{codeclasse}")
    Response getClasseByCodeClasse( @PathParam("codeclasse")  String codeclasse );


}
