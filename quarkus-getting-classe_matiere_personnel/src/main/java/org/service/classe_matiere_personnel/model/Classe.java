package org.service.classe_matiere_personnel.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import org.service.classe_matiere_personnel.enumeration.TypeParcours;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "my_matieres"})
public class Classe {

    @Id
    public String codeClasse;
    public String filiere; 
    public String niveau;
    // private String codeClasse;
    @Enumerated( EnumType.STRING )
    public TypeParcours typeParcours;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( name = "dispenser",joinColumns = @JoinColumn( name = "classe_id" ),inverseJoinColumns = @JoinColumn( name = "matiere_id" ) )
    private List<Matiere> my_matieres = new ArrayList<>() ;

    public Classe(){}

    public List<Matiere> getLesMatieresPourClasse() {
        return my_matieres;
    }

    public void setLesMatieresPourClasse(List<Matiere> matieres) {
        this.my_matieres = matieres;
    }

    @OneToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private Enseignant teacher;


}
