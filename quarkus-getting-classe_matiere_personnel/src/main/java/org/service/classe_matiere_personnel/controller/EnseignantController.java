package org.service.classe_matiere_personnel.controller;

import java.io.InputStream;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.service.classe_matiere_personnel.body_request.BodyTeacher;
import org.service.classe_matiere_personnel.enumeration.GradeEnseignant;
import org.service.classe_matiere_personnel.model.Classe;
import org.service.classe_matiere_personnel.model.Enseignant;
import org.service.classe_matiere_personnel.model.Matiere;
import org.service.classe_matiere_personnel.repository.EnseignantRepository;
import org.service.classe_matiere_personnel.repository.MatiereRepository;

@ApplicationScoped
@Path("enseignant")
public class EnseignantController {
    
    @Inject
    EnseignantRepository enseignantRepository;

    @Inject
    MatiereRepository matiereRepository;

    @GET
    public Response getAllTeacher(  ){
        return Response.ok(enseignantRepository.listAll()).build();
    }

    @GET
    @Path("{teacher_name}")
    public Response findTeacherByName( @PathParam("teacher_name") String teacher_name ){
        Optional<Enseignant> optionalTeacher = enseignantRepository.findTeacherByName(teacher_name);

        if ( optionalTeacher.isPresent() ) {
            return Response.ok(optionalTeacher.get()).build();
        } else {
            return Response.status( Status.NOT_FOUND ).entity(" DESOLE, AUCUN DE NOS ENSEINANT NE PORTE CE NOM. ").build();
        }

    }

    @PUT
    @Path("update/add_at_matiere/{codematiere}/{codeteacher}")
    @Transactional
    public Response attributeMatiereAtTeacher( @PathParam("codematiere") String codematiere,
    @PathParam("codeteacher") String codeteacher ){

        Optional<Matiere> optionalMatiere = matiereRepository.findByIdOptional(codematiere);

        if (optionalMatiere.isPresent()) {

            Optional<Enseignant> optionalEnseignant = enseignantRepository.findByIdOptional(codeteacher);
            if (optionalEnseignant.isPresent()) {

                optionalMatiere.get().les_teachers.add( optionalEnseignant.get() );
                matiereRepository.persist(optionalMatiere.get());

                return Response.ok(optionalMatiere.get()).build();
            } else {
                return Response.status(Status.CONFLICT)
                        .entity("LE CODE CLASSE " + codeteacher + " n'existe pas. essayez un autre ".toUpperCase())
                        .build();
            }

        } else {
            return Response.status(Status.CONFLICT)
                    .entity("LE CODE MATIERE " + codematiere + " n'existe pas. essayez un autre ".toUpperCase())
                    .build();
        }

    }

    @POST
    @Path("create")
    @Transactional
    @Consumes( MediaType.MULTIPART_FORM_DATA )
    public Response createNewTeacher( @MultipartForm BodyTeacher bodyTeacher ){

        Enseignant enseignant = new Enseignant();
         Optional<Enseignant> optionalEnseignant = enseignantRepository.findTeacherByNameOrEmail( bodyTeacher.nomComplet , bodyTeacher.email);
         if ( optionalEnseignant.isPresent() ) {
            return Response.status( Status.BAD_REQUEST ).entity(" UN ENSEIGNANT A DEJA ETE ENREGISTRER AVEC LE NOM " + bodyTeacher.nomComplet + " OU L'ADRESSE EMAIL  " + bodyTeacher.email  + " RECTIFIER L'UNE DE CES INFORMATIOS").build();
         } else {
            enseignant.adresseEmail = bodyTeacher.email;
            enseignant.nomComplet = bodyTeacher.nomComplet;
            enseignant.password = bodyTeacher.password;
            enseignant.telephone = bodyTeacher.telephone;
            enseignant.dateNaissance = bodyTeacher.dateNaissance;
            enseignant.departement = bodyTeacher.departement;
            enseignant.gradeEnseignant =  GradeEnseignant.valueOf( bodyTeacher.gradeEnseignant );

            enseignantRepository.persist(enseignant);
            if (enseignantRepository.isPersistent(enseignant)) {
                return Response.ok(enseignant).build();
            } else {
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(
                        " VOTRE ENSEIGNANT N'A PAS PU ETRE CREE. VERIFIER LA CONFORMITE DES DIFFERENTS VALEURS QUE VOUS AVEZ FOURNIS ")
                        .build();
            }
            //enseignant.photo = bodyTeacher.password;
            //enseignant.cniValide = bodyTeacher.cniValide;

         }

    }

    @PUT
    @Path("")
    @Transactional
    public Response removeTeacherOnMatiere(  ){
        return null;
    }
    
    @DELETE
    @Path("{id_teacher}")
    @Transactional
    public Response deleteTeacherByName( @PathParam("id_teacher") String id_teacher ){
        enseignantRepository.deleteByName(id_teacher);
        return Response.ok("LA SUPPRESSION A ETE EFFECTUER AVEC SUCCESS").build();
    }


}
