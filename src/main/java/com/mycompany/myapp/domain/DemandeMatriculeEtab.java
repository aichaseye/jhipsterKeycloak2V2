package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.enumeration.NomDep;
import com.mycompany.myapp.domain.enumeration.NomReg;
import com.mycompany.myapp.domain.enumeration.StatutEtab;
import com.mycompany.myapp.domain.enumeration.TypeEtab;
import com.mycompany.myapp.domain.enumeration.TypeInspection;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DemandeMatriculeEtab.
 */
@Entity
@Table(name = "demande_matricule_etab")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DemandeMatriculeEtab implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_demande_etab", unique = true)
    private String numeroDemandeEtab;

    @NotNull
    @Column(name = "nom_etab", nullable = false, unique = true)
    private String nomEtab;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_etab", nullable = false)
    private TypeEtab typeEtab;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutEtab statut;

    @NotNull
    @Column(name = "email_etab", nullable = false, unique = true)
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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DemandeMatriculeEtab id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroDemandeEtab() {
        return this.numeroDemandeEtab;
    }

    public DemandeMatriculeEtab numeroDemandeEtab(String numeroDemandeEtab) {
        this.setNumeroDemandeEtab(numeroDemandeEtab);
        return this;
    }

    public void setNumeroDemandeEtab(String numeroDemandeEtab) {
        this.numeroDemandeEtab = numeroDemandeEtab;
    }

    public String getNomEtab() {
        return this.nomEtab;
    }

    public DemandeMatriculeEtab nomEtab(String nomEtab) {
        this.setNomEtab(nomEtab);
        return this;
    }

    public void setNomEtab(String nomEtab) {
        this.nomEtab = nomEtab;
    }

    public TypeEtab getTypeEtab() {
        return this.typeEtab;
    }

    public DemandeMatriculeEtab typeEtab(TypeEtab typeEtab) {
        this.setTypeEtab(typeEtab);
        return this;
    }

    public void setTypeEtab(TypeEtab typeEtab) {
        this.typeEtab = typeEtab;
    }

    public StatutEtab getStatut() {
        return this.statut;
    }

    public DemandeMatriculeEtab statut(StatutEtab statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(StatutEtab statut) {
        this.statut = statut;
    }

    public String getEmailEtab() {
        return this.emailEtab;
    }

    public DemandeMatriculeEtab emailEtab(String emailEtab) {
        this.setEmailEtab(emailEtab);
        return this;
    }

    public void setEmailEtab(String emailEtab) {
        this.emailEtab = emailEtab;
    }

    public NomReg getRegion() {
        return this.region;
    }

    public DemandeMatriculeEtab region(NomReg region) {
        this.setRegion(region);
        return this;
    }

    public void setRegion(NomReg region) {
        this.region = region;
    }

    public NomDep getDepartement() {
        return this.departement;
    }

    public DemandeMatriculeEtab departement(NomDep departement) {
        this.setDepartement(departement);
        return this;
    }

    public void setDepartement(NomDep departement) {
        this.departement = departement;
    }

    public String getCommune() {
        return this.commune;
    }

    public DemandeMatriculeEtab commune(String commune) {
        this.setCommune(commune);
        return this;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public TypeInspection getTypeInsp() {
        return this.typeInsp;
    }

    public DemandeMatriculeEtab typeInsp(TypeInspection typeInsp) {
        this.setTypeInsp(typeInsp);
        return this;
    }

    public void setTypeInsp(TypeInspection typeInsp) {
        this.typeInsp = typeInsp;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemandeMatriculeEtab)) {
            return false;
        }
        return id != null && id.equals(((DemandeMatriculeEtab) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandeMatriculeEtab{" +
            "id=" + getId() +
            ", numeroDemandeEtab='" + getNumeroDemandeEtab() + "'" +
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
