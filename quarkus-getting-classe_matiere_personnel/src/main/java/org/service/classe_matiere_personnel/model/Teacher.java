package org.service.classe_matiere_personnel.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
public class Teacher extends PanacheEntityBase {
    
    @Id
    private String matricule;
    private String nomComplet;
    private String password;
    private String telephone;
    private String grade;
    private String dateNaissance;
    private String departement;
    private byte[] photo;
    private byte[] cniValide;

    /*  @OneToMany(mappedBy="teacher")
    private List<AssociationTeacherMatiere> associationTeacher = new ArrayList<AssociationTeacherMatiere>();
 */

    @ManyToMany()
    @JoinTable(name = "enseignement", joinColumns = @JoinColumn(name = "id_teacher"),
              inverseJoinColumns = @JoinColumn(name = "id_matiere"))
    Set<Matiere> lesMatieresPourTeacher = new HashSet<Matiere>();

    
    public String getMatricule() {
        return matricule;
    }

    public void addMatiere( Matiere matiere ){
        lesMatieresPourTeacher.add(matiere);
    }

    public void removeMatiere( Matiere matiere ){
        if(lesMatieresPourTeacher.isEmpty()){
            lesMatieresPourTeacher.remove(matiere);
        }
    }


}
