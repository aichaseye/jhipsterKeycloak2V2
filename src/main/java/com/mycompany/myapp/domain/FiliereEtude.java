package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Filiere;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FiliereEtude.
 */
@Entity
@Table(name = "filiere_etude")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FiliereEtude implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "filiere")
    private Filiere filiere;

    @OneToMany(mappedBy = "filiereEtude")
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

    public FiliereEtude id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Filiere getFiliere() {
        return this.filiere;
    }

    public FiliereEtude filiere(Filiere filiere) {
        this.setFiliere(filiere);
        return this;
    }

    public void setFiliere(Filiere filiere) {
        this.filiere = filiere;
    }

    public Set<Apprenant> getApprenants() {
        return this.apprenants;
    }

    public void setApprenants(Set<Apprenant> apprenants) {
        if (this.apprenants != null) {
            this.apprenants.forEach(i -> i.setFiliereEtude(null));
        }
        if (apprenants != null) {
            apprenants.forEach(i -> i.setFiliereEtude(this));
        }
        this.apprenants = apprenants;
    }

    public FiliereEtude apprenants(Set<Apprenant> apprenants) {
        this.setApprenants(apprenants);
        return this;
    }

    public FiliereEtude addApprenant(Apprenant apprenant) {
        this.apprenants.add(apprenant);
        apprenant.setFiliereEtude(this);
        return this;
    }

    public FiliereEtude removeApprenant(Apprenant apprenant) {
        this.apprenants.remove(apprenant);
        apprenant.setFiliereEtude(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FiliereEtude)) {
            return false;
        }
        return id != null && id.equals(((FiliereEtude) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FiliereEtude{" +
            "id=" + getId() +
            ", filiere='" + getFiliere() + "'" +
            "}";
    }
}
