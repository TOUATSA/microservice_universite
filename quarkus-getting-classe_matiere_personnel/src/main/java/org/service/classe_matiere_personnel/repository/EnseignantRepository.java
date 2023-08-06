package org.service.classe_matiere_personnel.repository;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import org.service.classe_matiere_personnel.model.Enseignant;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class EnseignantRepository implements PanacheRepositoryBase<Enseignant, String> {
    
    public void deleteByName( String name ){
        delete( "nomComplet = ?1 ", name );
    }

     public Optional<Enseignant> findTeacherByName( String name ){
        return find( "nomComplet = ?1 ", name ).firstResultOptional();
    }

       public Optional<Enseignant> findTeacherByNameOrEmail( String name, String email ){
        return find( "nomComplet = ?1 or email=?2 ", name, email ).firstResultOptional();
    }

}
