package org.service.classe_matiere_personnel.controller;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.service.classe_matiere_personnel.body_request.BodyClasse;
import org.service.classe_matiere_personnel.body_request.BodyMatiere;
import org.service.classe_matiere_personnel.enumeration.TypeParcours;
import org.service.classe_matiere_personnel.model.Classe;
import org.service.classe_matiere_personnel.model.Enseignant;
import org.service.classe_matiere_personnel.model.Matiere;
import org.service.classe_matiere_personnel.repository.ClasseRepository;

@ApplicationScoped
@Path("classe")
public class ClasseController {
 
    @Inject
    ClasseRepository classeRepository;

    @GET
    public Response getAllClasse(){
        return Response.ok( classeRepository.listAll() ).build();
    }

    @GET
    @Path("{code_classe}")
    public Response findClasseByCodeClasse( @PathParam("code_classe") String code_classe ){
        Optional<Classe> optionalClasse = classeRepository.findByIdOptional(code_classe);

        if ( optionalClasse.isPresent() ) {
            return Response.ok(optionalClasse.get()).build();
        } else {
            return Response.status( Status.NOT_FOUND ).entity(" DESOLE, AUCUNE DE NOS CLASSE NE PORTE CE CODE CLASSE. ").build();
        }

    }

    @POST
    @Path("create")
    @Transactional
    public Response createNewClasse( BodyClasse bodyClasse ){

        Optional<Classe> optionalClasse = classeRepository.findByIdOptional(bodyClasse.codeClasse);

        if (!optionalClasse.isPresent()) {
            Classe classe = new Classe();
            classe.codeClasse = bodyClasse.codeClasse;
            classe.filiere = bodyClasse.filiere;
            classe.niveau = bodyClasse.niveau;
            classe.typeParcours = TypeParcours.valueOf( bodyClasse.typeParcours );

            classeRepository.persist(classe);
            if (classeRepository.isPersistent(classe)) {
                return Response.ok(classe).build();
            } else {
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(
                        " VOTRE CLASSE N'A PAS PU ETRE CREE. VERIFIER LA CONFORMITE DES DIFFERENTS VALEURS QUE VOUS AVEZ FOURNIS ")
                        .build();
            }
        } else {
            return Response.status(Status.CONFLICT)
                    .entity("LE CODE CLASSE " + bodyClasse.codeClasse
                            + " est deja utilise par une autre CLASSE. veillez utilisé un autre.".toUpperCase())
                    .build();
        }
        
    }

}
