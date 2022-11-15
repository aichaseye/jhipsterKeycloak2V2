package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Serie;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SerieEtude.
 */
@Entity
@Table(name = "serie_etude")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SerieEtude implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "serie")
    private Serie serie;

    @OneToMany(mappedBy = "serieEtude")
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

    public SerieEtude id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Serie getSerie() {
        return this.serie;
    }

    public SerieEtude serie(Serie serie) {
        this.setSerie(serie);
        return this;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Set<Apprenant> getApprenants() {
        return this.apprenants;
    }

    public void setApprenants(Set<Apprenant> apprenants) {
        if (this.apprenants != null) {
            this.apprenants.forEach(i -> i.setSerieEtude(null));
        }
        if (apprenants != null) {
            apprenants.forEach(i -> i.setSerieEtude(this));
        }
        this.apprenants = apprenants;
    }

    public SerieEtude apprenants(Set<Apprenant> apprenants) {
        this.setApprenants(apprenants);
        return this;
    }

    public SerieEtude addApprenant(Apprenant apprenant) {
        this.apprenants.add(apprenant);
        apprenant.setSerieEtude(this);
        return this;
    }

    public SerieEtude removeApprenant(Apprenant apprenant) {
        this.apprenants.remove(apprenant);
        apprenant.setSerieEtude(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SerieEtude)) {
            return false;
        }
        return id != null && id.equals(((SerieEtude) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SerieEtude{" +
            "id=" + getId() +
            ", serie='" + getSerie() + "'" +
            "}";
    }
}
