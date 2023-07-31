package org.service.classe_matiere_personnel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
public class AssociationTeacherMatiere extends PanacheEntityBase {
    
  @Id
  private Long id;

  @Column
  @Temporal(TemporalType.TIMESTAMP)
  private Date affectation;

  @Column
  @Temporal(TemporalType.TIMESTAMP)
  private Date expiration;

  /* @ManyToOne(fetch=FetchType.LAZY)
  private Matiere matiere;

  @ManyToOne(fetch=FetchType.LAZY)
  private Teacher teacher; */

}
