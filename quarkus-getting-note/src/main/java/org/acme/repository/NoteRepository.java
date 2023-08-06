package org.acme.repository;

import java.util.List;
import java.util.Optional;

import org.acme.model.Note;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class NoteRepository implements PanacheMongoRepository<Note> {
    
    public List<Note> findNotesByCodeMatiere(  String codeMatiere, String academicYear ){
        return find( " academicYear =?1 and codeMatiere =?2 ", academicYear, codeMatiere ).list();
    }

    public List<Note> findAllNotesByMatriculeStudent(  String matricule ){
        return find( " matriculeEtudiant = ?1 ", matricule ).list();
    } 

    public List<Note> findOneNoteOfStudentByMatriculeWithAcademicAndCodeMatiere(  String matricule, String academicYear, String Codematiere ){
        return find( " matriculeEtudiant =?1 and academicYear =?2 and codeMatiere =?3 ", matricule, academicYear, Codematiere ).list();
    } 

}

