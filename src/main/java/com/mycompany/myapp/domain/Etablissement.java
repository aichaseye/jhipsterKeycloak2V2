package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.NomDep;
import com.mycompany.myapp.domain.enumeration.NomReg;
import com.mycompany.myapp.domain.enumeration.StatutEtab;
import com.mycompany.myapp.domain.enumeration.TypeEtab;
import com.mycompany.myapp.domain.enumeration.TypeInspection;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Etablissement.
 */
@Entity
@Table(name = "etablissement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Etablissement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "matricule_etab", unique = true)
    private String matriculeEtab;

    @NotNull
    @Column(name = "nom_etab", nullable = false)
    private String nomEtab;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_etab", nullable = false)
    private TypeEtab typeEtab;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutEtab statut;

    @Column(name = "email_etab", unique = true)
    private String emailEtab;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "region", nullable = false)
    private NomReg region;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "departement", nullable = false)
    private NomDep departement;

    @NotNull
    @Column(name = "commune", nullable = false)
    private String commune;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_insp", nullable = false)
    private TypeInspection typeInsp;

    @OneToOne
    @JoinColumn(unique = true)
    private DemandeMatriculeEtab demandeMatriculeEtab;

    @OneToMany(mappedBy = "etablissement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "user", "demandeMatriculeApp", "etablissement", "niveauEtude", "serieEtude", "filiereEtude" },
        allowSetters = true
    )
    private Set<Apprenant> apprenants = new HashSet<>();

    @OneToMany(mappedBy = "etablissement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "etablissement" }, allowSetters = true)
    private Set<PersonnelEtab> personnelEtabs = new HashSet<>();

    @OneToMany(mappedBy = "etablissement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "etablissement" }, allowSetters = true)
    private Set<Matiere> matieres = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Etablissement id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatriculeEtab() {
        return this.matriculeEtab;
    }

    public Etablissement matriculeEtab(String matriculeEtab) {
        this.setMatriculeEtab(matriculeEtab);
        return this;
    }

    public void setMatriculeEtab(String matriculeEtab) {
        this.matriculeEtab = matriculeEtab;
    }

    public String getNomEtab() {
        return this.nomEtab;
    }

    public Etablissement nomEtab(String nomEtab) {
        this.setNomEtab(nomEtab);
        return this;
    }

    public void setNomEtab(String nomEtab) {
        this.nomEtab = nomEtab;
    }

    public TypeEtab getTypeEtab() {
        return this.typeEtab;
    }

    public Etablissement typeEtab(TypeEtab typeEtab) {
        this.setTypeEtab(typeEtab);
        return this;
    }

    public void setTypeEtab(TypeEtab typeEtab) {
        this.typeEtab = typeEtab;
    }

    public StatutEtab getStatut() {
        return this.statut;
    }

    public Etablissement statut(StatutEtab statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(StatutEtab statut) {
        this.statut = statut;
    }

    public String getEmailEtab() {
        return this.emailEtab;
    }

    public Etablissement emailEtab(String emailEtab) {
        this.setEmailEtab(emailEtab);
        return this;
    }

    public void setEmailEtab(String emailEtab) {
        this.emailEtab = emailEtab;
    }

    public NomReg getRegion() {
        return this.region;
    }

    public Etablissement region(NomReg region) {
        this.setRegion(region);
        return this;
    }

    public void setRegion(NomReg region) {
        this.region = region;
    }

    public NomDep getDepartement() {
        return this.departement;
    }

    public Etablissement departement(NomDep departement) {
        this.setDepartement(departement);
        return this;
    }

    public void setDepartement(NomDep departement) {
        this.departement = departement;
    }

    public String getCommune() {
        return this.commune;
    }

    public Etablissement commune(String commune) {
        this.setCommune(commune);
        return this;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public TypeInspection getTypeInsp() {
        return this.typeInsp;
    }

    public Etablissement typeInsp(TypeInspection typeInsp) {
        this.setTypeInsp(typeInsp);
        return this;
    }

    public void setTypeInsp(TypeInspection typeInsp) {
        this.typeInsp = typeInsp;
    }

    public DemandeMatriculeEtab getDemandeMatriculeEtab() {
        return this.demandeMatriculeEtab;
    }

    public void setDemandeMatriculeEtab(DemandeMatriculeEtab demandeMatriculeEtab) {
        this.demandeMatriculeEtab = demandeMatriculeEtab;
    }

    public Etablissement demandeMatriculeEtab(DemandeMatriculeEtab demandeMatriculeEtab) {
        this.setDemandeMatriculeEtab(demandeMatriculeEtab);
        return this;
    }

    public Set<Apprenant> getApprenants() {
        return this.apprenants;
    }

    public void setApprenants(Set<Apprenant> apprenants) {
        if (this.apprenants != null) {
            this.apprenants.forEach(i -> i.setEtablissement(null));
        }
        if (apprenants != null) {
            apprenants.forEach(i -> i.setEtablissement(this));
        }
        this.apprenants = apprenants;
    }

    public Etablissement apprenants(Set<Apprenant> apprenants) {
        this.setApprenants(apprenants);
        return this;
    }

    public Etablissement addApprenant(Apprenant apprenant) {
        this.apprenants.add(apprenant);
        apprenant.setEtablissement(this);
        return this;
    }

    public Etablissement removeApprenant(Apprenant apprenant) {
        this.apprenants.remove(apprenant);
        apprenant.setEtablissement(null);
        return this;
    }

    public Set<PersonnelEtab> getPersonnelEtabs() {
        return this.personnelEtabs;
    }

    public void setPersonnelEtabs(Set<PersonnelEtab> personnelEtabs) {
        if (this.personnelEtabs != null) {
            this.personnelEtabs.forEach(i -> i.setEtablissement(null));
        }
        if (personnelEtabs != null) {
            personnelEtabs.forEach(i -> i.setEtablissement(this));
        }
        this.personnelEtabs = personnelEtabs;
    }

    public Etablissement personnelEtabs(Set<PersonnelEtab> personnelEtabs) {
        this.setPersonnelEtabs(personnelEtabs);
        return this;
    }

    public Etablissement addPersonnelEtab(PersonnelEtab personnelEtab) {
        this.personnelEtabs.add(personnelEtab);
        personnelEtab.setEtablissement(this);
        return this;
    }

    public Etablissement removePersonnelEtab(PersonnelEtab personnelEtab) {
        this.personnelEtabs.remove(personnelEtab);
        personnelEtab.setEtablissement(null);
        return this;
    }

    public Set<Matiere> getMatieres() {
        return this.matieres;
    }

    public void setMatieres(Set<Matiere> matieres) {
        if (this.matieres != null) {
            this.matieres.forEach(i -> i.setEtablissement(null));
        }
        if (matieres != null) {
            matieres.forEach(i -> i.setEtablissement(this));
        }
        this.matieres = matieres;
    }

    public Etablissement matieres(Set<Matiere> matieres) {
        this.setMatieres(matieres);
        return this;
    }

    public Etablissement addMatiere(Matiere matiere) {
        this.matieres.add(matiere);
        matiere.setEtablissement(this);
        return this;
    }

    public Etablissement removeMatiere(Matiere matiere) {
        this.matieres.remove(matiere);
        matiere.setEtablissement(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Etablissement)) {
            return false;
        }
        return id != null && id.equals(((Etablissement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Etablissement{" +
            "id=" + getId() +
            ", matriculeEtab='" + getMatriculeEtab() + "'" +
            ", nomEtab='" + getNomEtab() + "'" +
            ", typeEtab='" + getTypeEtab() + "'" +
            ", statut='" + getStatut() + "'" +
            ", emailEtab='" + getEmailEtab() + "'" +
            ", region='" + getRegion() + "'" +
            ", departement='" + getDepartement() + "'" +
            ", commune='" + getCommune() + "'" +
            ", typeInsp='" + getTypeInsp() + "'" +
            "}";
    }
}
