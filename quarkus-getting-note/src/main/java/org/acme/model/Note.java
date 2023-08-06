package org.acme.model;

import org.bson.types.ObjectId;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity
public class Note {
    
    public ObjectId id; // used by MongoDB for the _id field
        public String matriculeEtudiant;
        public String codeMatiere;
        public String academicYear;
        public float noteTpe;
        public float noteCc;
        public float noteExam;
        public SessionValidation sessionValidation;
        public StatutNote statutNote;
        public float moyenne20;
        public float mgp;

        public Note( String matricule, String codematiere, float tpe, float cc, String academicYear ){
            this.matriculeEtudiant = matricule;
            this.codeMatiere = codematiere;
            this.noteTpe = tpe; 
            this.noteCc = cc;
            this.academicYear = academicYear;
    } 

    public Note( String matricule, String codematiere, float exam ){
            this.matriculeEtudiant = matricule;
            this.codeMatiere = codematiere;
            this.noteExam = exam; 
    }

    public Note(){}
}
