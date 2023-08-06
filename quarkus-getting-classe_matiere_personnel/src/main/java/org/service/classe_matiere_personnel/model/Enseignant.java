package org.service.classe_matiere_personnel.model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.service.classe_matiere_personnel.enumeration.GradeEnseignant;

@Entity
public class Enseignant {

    @Id
    @GeneratedValue(generator = "Enseignant_UUID")
    @GenericGenerator(name = "Enseignant_UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "uuid_gen_strategy_class", value = "org.hibernate.id.uuid.CustomVersionOneStrategy")
    })
    @Type(type = "uuid-char")
    @Column(length = 36, updatable = false)
    public UUID matricule;

    @Column(unique = true)
    public String nomComplet;
    @Column(unique = true)
    public String adresseEmail;
    public String password;
    public String telephone;
    public GradeEnseignant gradeEnseignant;
    public String dateNaissance;
    public String departement;

    
     @Lob
      @Column(columnDefinition = "bytea[]")
     private byte[] photo;
     
     @Lob
      @Column(columnDefinition = "bytea[]")
     private byte[] cniValide;
    

    public Enseignant() {
    }

}
