package org.service.classe_matiere_personnel.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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

import org.service.classe_matiere_personnel.body_request.BodyMatiere;
import org.service.classe_matiere_personnel.enumeration.TypeMatiere;
import org.service.classe_matiere_personnel.model.Classe;
import org.service.classe_matiere_personnel.model.Enseignant;
import org.service.classe_matiere_personnel.model.Matiere;
import org.service.classe_matiere_personnel.repository.ClasseRepository;
import org.service.classe_matiere_personnel.repository.MatiereRepository;

@ApplicationScoped
@Path("matiere")
public class MatiereController {

    @Inject
    ClasseRepository classeRepository;

    @Inject
    MatiereRepository matiereRepository;

    @GET
    public Response getAllMatiere() {
        return Response.ok(matiereRepository.listAll()).build();
    }

     @GET
    @Path("{code_matiere}")
    public Response findMatiereByCodeMatiere( @PathParam("code_matiere") String code_matiere ){
        Optional<Matiere> optionalMatiere = matiereRepository.findByIdOptional(code_matiere);

        if ( optionalMatiere.isPresent() ) {
            return Response.ok(optionalMatiere.get()).build();
        } else {
            return Response.status( Status.NOT_FOUND ).entity(" DESOLE, AUCUNE DE NOS MATIERE NE CORRESPOND AU CODE MATIERE " + code_matiere).build();
        }

    }


    @POST
    @Transactional
    @Path("create")
    public Response createNewMatiere(BodyMatiere bodyMatiere) {

        Optional<Matiere> optionalMatiere = matiereRepository.findByIdOptional(bodyMatiere.codeMatiere);

        if (!optionalMatiere.isPresent()) {
            Matiere matiere = new Matiere();
            matiere.intituleMatiere = bodyMatiere.intituleMatiere;
            matiere.coefficient = bodyMatiere.coefficient;
            matiere.semestre = bodyMatiere.semestre;
            matiere.typeMatiere = bodyMatiere.typeMatiere;
            matiere.codeMatiere = bodyMatiere.codeMatiere;

            matiereRepository.persist(matiere);
            if (matiereRepository.isPersistent(matiere)) {
                return Response.ok(matiere).build();
            } else {
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(
                        " VOTRE MATIERE N'A PAS PU ETRE CREE. VERIFIER LA CONFORMITE DES DIFFERENTS VALEURS QUE VOUS AVEZ FOURNIES ")
                        .build();
            }
        } else {
            return Response.status(Status.CONFLICT)
                    .entity("LE CODE MATIERE " + bodyMatiere.codeMatiere
                            + " est deja utilise par une autre matiere. veillez utilisé un autre.".toUpperCase())
                    .build();
        }

    }

    @PUT
    @Transactional
    @Path("update/add_at_classe/{codematiere}/{codeclasse}")
    public Response attribuerMatiereVersClasse(@PathParam("codematiere") String codematiere,
            @PathParam("codeclasse") String codeclasse) {

        Optional<Matiere> optionalMatiere = matiereRepository.findByIdOptional(codematiere);

        if (optionalMatiere.isPresent()) {

            Optional<Classe> optionalClasse = classeRepository.findByIdOptional(codeclasse);
            if (optionalClasse.isPresent()) {

                optionalClasse.get().getLesMatieresPourClasse().add(optionalMatiere.get());
                classeRepository.persist(optionalClasse.get());

                return Response.ok(optionalClasse.get()).build();
            } else {
                return Response.status(Status.CONFLICT)
                        .entity("LE CODE CLASSE " + codeclasse + " n'existe pas. essayez un autre ".toUpperCase())
                        .build();
            }

        } else {
            return Response.status(Status.CONFLICT)
                    .entity("LE CODE MATIERE " + codematiere + " n'existe pas. essayez un autre ".toUpperCase())
                    .build();
        }

    }

     @PUT
    @Transactional
    @Path("update/remove_at_classe/{codematiere}/{codeclasse}")
    public Response removeMatiereDansClasse(@PathParam("codematiere") String codematiere,
            @PathParam("codeclasse") String codeclasse) {

        Optional<Matiere> optionalMatiere = matiereRepository.findByIdOptional(codematiere);

        if (optionalMatiere.isPresent()) {

            Optional<Classe> optionalClasse = classeRepository.findByIdOptional(codeclasse);
            if (optionalClasse.isPresent()) {

                optionalClasse.get().getLesMatieresPourClasse().remove(optionalMatiere.get());
                classeRepository.persist(optionalClasse.get());

                return Response.ok(optionalClasse.get()).build();
            } else {
                return Response.status(Status.CONFLICT)
                        .entity("LE CODE CLASSE " + codeclasse + " n'existe pas. essayez un autre ".toUpperCase())
                        .build();
            }

        } else {
            return Response.status(Status.CONFLICT)
                    .entity("LE CODE MATIERE " + codematiere + " n'existe pas. essayez un autre ".toUpperCase())
                    .build();
        }

    }

}
