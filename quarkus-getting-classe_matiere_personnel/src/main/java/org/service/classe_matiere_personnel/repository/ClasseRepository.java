package org.service.classe_matiere_personnel.repository;

import javax.enterprise.context.ApplicationScoped;

import org.service.classe_matiere_personnel.model.Classe;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class ClasseRepository implements PanacheRepositoryBase<Classe, String> {
    


}
