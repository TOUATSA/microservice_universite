package org.service.classe_matiere_personnel.body_request;

import java.io.InputStream;
import javax.ws.rs.FormParam;

import javax.persistence.Column;

public class BodyTeacher {

    @FormParam("nomComplet")
    public String nomComplet;
    @FormParam("password")
    public String password;
    @FormParam("telephone")
    public String telephone;
    @FormParam("gradeEnseignant")
    public String gradeEnseignant;
    @FormParam("dateNaissance")
    public String dateNaissance;
    @FormParam("departement")
    public String departement;
    @FormParam("email")
    public String email;
    @FormParam("photo44")
    public InputStream photo44;
    @FormParam("cniValide")
    public InputStream cniValide;

}
