package org.service.classe_matiere_personnel.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.service.classe_matiere_personnel.enumeration.TypeParcours;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
public class Classe extends PanacheEntityBase {
    
    @Id
    private String codeClasse;
    private String filiere;
    private String niveau;
   // private String codeClasse;
    private TypeParcours typeParcours;

    @ManyToMany()
@JoinTable(name = "dispenser", joinColumns = @JoinColumn(name = "id_classe"),
              inverseJoinColumns = @JoinColumn(name = "id_matiere_classe"))
Set<Matiere> lesMatieresPourClasse = new HashSet<Matiere>();

    public Set<Matiere> getActeurs() {
        return lesMatieresPourClasse;
    }

    public void setActeurs(Set<Matiere> matieres) {
        this.lesMatieresPourClasse = matieres;
    } 

    

}
