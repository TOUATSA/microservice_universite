package org.service.classe_matiere_personnel.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import org.service.classe_matiere_personnel.enumeration.TypeMatiere;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "les_classes"})
public class Matiere {
    
    @Id
    public String codeMatiere;
    public short coefficient;
    public String intituleMatiere;
    public short semestre;
    public TypeMatiere typeMatiere;

    public Matiere( ){}

    // @ManyToMany(fetch = FetchType.EAGER)
    // @JoinTable( name = "dispenser", joinColumns = @JoinColumn(name = "matiere_id"), inverseJoinColumns = @JoinColumn(name = "classe_id"))
    // public List<Classe> les_classes = new ArrayList<>();

     @ManyToMany( fetch = FetchType.EAGER)
     @JoinTable( name = "enseignement", joinColumns = @JoinColumn(name = "enseignement_matiere_id"), inverseJoinColumns = @JoinColumn(name = "enseignement_teacher_id"))
     public Set<Enseignant> les_teachers = new HashSet<>();
 }
