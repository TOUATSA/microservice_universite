package org.service.classe_matiere_personnel.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.service.classe_matiere_personnel.enumeration.TypeMatiere;
import org.service.classe_matiere_personnel.model.Matiere;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class MatiereRepository implements PanacheRepository<Matiere> {
    
    public Matiere findByName(String name){
        return find("name", name).firstResult();
    }
 
    /**
     * @return
     */
    public List<Matiere> findAlive(){
        return list("typeMatiere", String.valueOf(TypeMatiere.OPTIONNEL) );
    }

}
