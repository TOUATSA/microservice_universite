package org.service.classe_matiere_personnel.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.service.classe_matiere_personnel.enumeration.TypeMatiere;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
public class Matiere extends PanacheEntityBase {
    
    @Id
    private String codeMatiere;
    private short coefficient;
    private String intituleMatiere;
    private short semestre;
    private TypeMatiere typeMatiere;
    private String email;

   /*  @OneToMany(mappedBy="matiere")
    private List<AssociationTeacherMatiere> associationMatiere = new ArrayList<AssociationTeacherMatiere>();
 */

    @ManyToMany(mappedBy = "lesMatieresPourTeacher")
    Set<Teacher> lesTeacher;

    @ManyToMany(mappedBy = "lesMatieresPourClasse") 
    Set<Classe> lesClasse;

    public void addTeacher( Teacher teacher ){
        lesTeacher.add(teacher);
    }

    public void removeTeacher( Teacher teacher ){
        if( !lesTeacher.isEmpty() ){
            lesTeacher.remove(teacher);
        }
    }
}
