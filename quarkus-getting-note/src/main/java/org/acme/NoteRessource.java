package org.acme;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.acme.model.Note;
import org.acme.model.SessionValidation;
import org.acme.model.StatutNote;
import org.acme.proxy.ClasseProxy;
import org.acme.proxy.MatiereProxy;
import org.acme.repository.NoteRepository;
import org.acme.type_body_request.BodyMoreNote;
import org.acme.type_body_request.BodyOneNote;
import org.acme.type_body_request.LineNote;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("note")
public class NoteRessource {

    @RestClient
    ClasseProxy classeProxy;

    @RestClient 
    MatiereProxy matiereProxy;

    @Inject 
    NoteRepository noteRepository;
    
    @GET
    public Response getAllNote(  ){
        return Response.ok( noteRepository.findAllNotesByMatriculeStudent("17S972130") ).build();
    }

    
    @POST
    public Response addNoteCC( BodyMoreNote bodyMoreNote ){

        Response responseMatiere = matiereProxy.getMatiereByCodematiere( bodyMoreNote.codematiere );
        if ( responseMatiere.getStatus() != 200 ) {
            return Response.status( Status.BAD_REQUEST ).entity(" le code matiere : " + bodyMoreNote.codematiere + " est incorrecte. ").build();
        } else {
            Response reponseClasse = classeProxy.getClasseByCodeClasse( bodyMoreNote.codeclasse );
            if ( reponseClasse.getStatus() != 200 ) {
                return Response.status( Status.BAD_REQUEST ).entity(" le code classe : " + bodyMoreNote.codeclasse + " est incorrecte. ").build();
            } else {

               // Note note = new Note(); 
                List<Note> maListeNote = new ArrayList<>();
                
                for (LineNote lineNote : bodyMoreNote.lineNotes ) {
                    maListeNote.add( new Note( lineNote.matricule, bodyMoreNote.codematiere, lineNote.tpe, lineNote.notecc, bodyMoreNote.academicYear ) );
                }
                noteRepository.persist(maListeNote);
                return Response.ok(" TOUTES VOS NOTES ONT ETE ENREGISTRE ").build();

            }

        }

    }

    @PUT
    public Response addNoteExamOrRattrapage( BodyOneNote bodyOneNote ){

    Response responseMatiere = matiereProxy.getMatiereByCodematiere( bodyOneNote.codematiere );
            if ( responseMatiere.getStatus() != 200 ) {
                return Response.status( Status.BAD_REQUEST ).entity(" le code matiere : " + bodyOneNote.codematiere + " est incorrecte. ").build();
            } else {
                Response reponseClasse = classeProxy.getClasseByCodeClasse( bodyOneNote.codeclasse );
                if ( reponseClasse.getStatus() != 200 ) {
                    return Response.status( Status.BAD_REQUEST ).entity(" le code classe : " + bodyOneNote.codeclasse + " est incorrecte. ").build();
                } else {
                    List<Note> listeNote = noteRepository.findOneNoteOfStudentByMatriculeWithAcademicAndCodeMatiere( bodyOneNote.lineNotes.matricule, bodyOneNote.academicYear, bodyOneNote.codematiere );
                   
                     if ( listeNote.size() > 0) {
                        Note note = listeNote.get(0);
                        note.noteExam = bodyOneNote.lineNotes.exam; 
                        float calculNoteSur20 = 0f;
                        // calcul necessaire pour remplir les champs tels que mgp, moyenne20, statutNote, sessionValidation
                        // .....................
                        if ( calculNoteSur20 > 10.0 ) { 

                            if ( bodyOneNote.session.toUpperCase().equals("NORMALE") ) {
                                note.sessionValidation = SessionValidation.NORMALE;
                            } else {
                                 note.sessionValidation = SessionValidation.RATTRAPAGE;
                            }
                            note.moyenne20 = calculNoteSur20;
                            note.statutNote = StatutNote.VALIDE;
                        } else {
                            note.moyenne20 = calculNoteSur20;
                            if ( !bodyOneNote.session.toUpperCase().equals("NORMALE") ) {
                               note.statutNote = StatutNote.NONVALIDE;         
                            }
                        }
                        noteRepository.update(note);
                       // noteRepository.persist(note);
                        return Response.ok(" VOTRE NOTE D'EXAMEN A ETE ENREGISTRE ").build();
                    } else {
                        return Response.status( Status.NOT_FOUND ).entity(" l'etudiant de matricule  :  " + bodyOneNote.lineNotes.matricule + "  n'a pas de note de cc. verifier cela ").build();
                    } 

                }

            }
    }

      @PUT
      @Path("un")
    public Response modifyNoteCCAndTpe( BodyOneNote bodyOneNote ){

            Response responseMatiere = matiereProxy.getMatiereByCodematiere( bodyOneNote.codematiere );
           if ( responseMatiere.getStatus() != 200 ) {
                return Response.status( Status.BAD_REQUEST ).entity(" le code matiere : " + bodyOneNote.codematiere + " est incorrecte. ").build();
            } else {
                Response reponseClasse = classeProxy.getClasseByCodeClasse( bodyOneNote.codeclasse );
                if ( reponseClasse.getStatus() != 200 ) {
                    return Response.status( Status.BAD_REQUEST ).entity(" le code classe : " + bodyOneNote.codeclasse + " est incorrecte. ").build();
                } else {
                    List<Note> listeNote = noteRepository.findOneNoteOfStudentByMatriculeWithAcademicAndCodeMatiere( bodyOneNote.lineNotes.matricule, bodyOneNote.academicYear, bodyOneNote.codematiere );
                   
                     if ( listeNote.size() > 0) {
                        Note note = listeNote.get(0);
                        if( bodyOneNote.lineNotes.notecc > 0 )
                            note.noteCc = bodyOneNote.lineNotes.notecc;
                        if( bodyOneNote.lineNotes.tpe > 0 )
                            note.noteTpe = bodyOneNote.lineNotes.tpe;
                        
                        noteRepository.update(note);
                       // noteRepository.persist(note);
                        return Response.ok(" VOTRE NOTE DE CC ET/OU TPE A ETE MODIFIE ").build();
                    } else {
                        return Response.status( Status.NOT_FOUND ).entity(" l'etudiant de matricule  :  " + bodyOneNote.lineNotes.matricule + "  n'a pas de note de cc. verifier cela ").build();
                    } 

                }

            }
    

    }

}
