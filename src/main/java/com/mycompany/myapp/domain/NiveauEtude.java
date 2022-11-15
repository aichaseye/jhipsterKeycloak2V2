package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Niveau;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A NiveauEtude.
 */
@Entity
@Table(name = "niveau_etude")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NiveauEtude implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "niveau", nullable = false)
    private Niveau niveau;

    @Column(name = "annee_etude")
    private LocalDate anneeEtude;

    @OneToMany(mappedBy = "niveauEtude")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "user", "demandeMatriculeApp", "etablissement", "niveauEtude", "serieEtude", "filiereEtude" },
        allowSetters = true
    )
    private Set<Apprenant> apprenants = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NiveauEtude id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Niveau getNiveau() {
        return this.niveau;
    }

    public NiveauEtude niveau(Niveau niveau) {
        this.setNiveau(niveau);
        return this;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public LocalDate getAnneeEtude() {
        return this.anneeEtude;
    }

    public NiveauEtude anneeEtude(LocalDate anneeEtude) {
        this.setAnneeEtude(anneeEtude);
        return this;
    }

    public void setAnneeEtude(LocalDate anneeEtude) {
        this.anneeEtude = anneeEtude;
    }

    public Set<Apprenant> getApprenants() {
        return this.apprenants;
    }

    public void setApprenants(Set<Apprenant> apprenants) {
        if (this.apprenants != null) {
            this.apprenants.forEach(i -> i.setNiveauEtude(null));
        }
        if (apprenants != null) {
            apprenants.forEach(i -> i.setNiveauEtude(this));
        }
        this.apprenants = apprenants;
    }

    public NiveauEtude apprenants(Set<Apprenant> apprenants) {
        this.setApprenants(apprenants);
        return this;
    }

    public NiveauEtude addApprenant(Apprenant apprenant) {
        this.apprenants.add(apprenant);
        apprenant.setNiveauEtude(this);
        return this;
    }

    public NiveauEtude removeApprenant(Apprenant apprenant) {
        this.apprenants.remove(apprenant);
        apprenant.setNiveauEtude(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NiveauEtude)) {
            return false;
        }
        return id != null && id.equals(((NiveauEtude) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NiveauEtude{" +
            "id=" + getId() +
            ", niveau='" + getNiveau() + "'" +
            ", anneeEtude='" + getAnneeEtude() + "'" +
            "}";
    }
}
