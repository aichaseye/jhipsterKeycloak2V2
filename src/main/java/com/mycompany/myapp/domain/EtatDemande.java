package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.enumeration.Etat;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EtatDemande.
 */
@Entity
@Table(name = "etat_demande")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EtatDemande implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "etat", nullable = false)
    private Etat etat;

    @Column(name = "date_disponibilite")
    private LocalDate dateDisponibilite;

    @Lob
    @Column(name = "description")
    private String description;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private DemandeMatriculeApp demandeMatriculeApp;

    @OneToOne
    @JoinColumn(unique = true)
    private DemandeMatriculeEtab demandeMatriculeEtab;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EtatDemande id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Etat getEtat() {
        return this.etat;
    }

    public EtatDemande etat(Etat etat) {
        this.setEtat(etat);
        return this;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public LocalDate getDateDisponibilite() {
        return this.dateDisponibilite;
    }

    public EtatDemande dateDisponibilite(LocalDate dateDisponibilite) {
        this.setDateDisponibilite(dateDisponibilite);
        return this;
    }

    public void setDateDisponibilite(LocalDate dateDisponibilite) {
        this.dateDisponibilite = dateDisponibilite;
    }

    public String getDescription() {
        return this.description;
    }

    public EtatDemande description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DemandeMatriculeApp getDemandeMatriculeApp() {
        return this.demandeMatriculeApp;
    }

    public void setDemandeMatriculeApp(DemandeMatriculeApp demandeMatriculeApp) {
        this.demandeMatriculeApp = demandeMatriculeApp;
    }

    public EtatDemande demandeMatriculeApp(DemandeMatriculeApp demandeMatriculeApp) {
        this.setDemandeMatriculeApp(demandeMatriculeApp);
        return this;
    }

    public DemandeMatriculeEtab getDemandeMatriculeEtab() {
        return this.demandeMatriculeEtab;
    }

    public void setDemandeMatriculeEtab(DemandeMatriculeEtab demandeMatriculeEtab) {
        this.demandeMatriculeEtab = demandeMatriculeEtab;
    }

    public EtatDemande demandeMatriculeEtab(DemandeMatriculeEtab demandeMatriculeEtab) {
        this.setDemandeMatriculeEtab(demandeMatriculeEtab);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EtatDemande)) {
            return false;
        }
        return id != null && id.equals(((EtatDemande) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EtatDemande{" +
            "id=" + getId() +
            ", etat='" + getEtat() + "'" +
            ", dateDisponibilite='" + getDateDisponibilite() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
