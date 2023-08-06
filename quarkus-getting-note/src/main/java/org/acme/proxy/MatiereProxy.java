package org.acme.proxy;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@RegisterRestClient( baseUri = "http://localhost:8081/matiere" )
public interface MatiereProxy {
    
     @GET
    @Path("{codematiere}")
    Response getMatiereByCodematiere( @PathParam("codematiere") String codematiere );


}
