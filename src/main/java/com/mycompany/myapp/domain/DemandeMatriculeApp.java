package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.enumeration.Sexe;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DemandeMatriculeApp.
 */
@Entity
@Table(name = "demande_matricule_app")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DemandeMatriculeApp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_demande_app", unique = true)
    private String numeroDemandeApp;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotNull
    @Column(name = "num_id_national", nullable = false, unique = true)
    private Long numIdNational;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sexe", nullable = false)
    private Sexe sexe;

    @NotNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @NotNull
    @Column(name = "lieu_naissance", nullable = false)
    private String lieuNaissance;

    @NotNull
    @Column(name = "adresse", nullable = false)
    private String adresse;

    @NotNull
    @Column(name = "etab_frequente", nullable = false)
    private String etabFrequente;

    @NotNull
    @Column(name = "matricule_etab", nullable = false)
    private String matriculeEtab;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DemandeMatriculeApp id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroDemandeApp() {
        return this.numeroDemandeApp;
    }

    public DemandeMatriculeApp numeroDemandeApp(String numeroDemandeApp) {
        this.setNumeroDemandeApp(numeroDemandeApp);
        return this;
    }

    public void setNumeroDemandeApp(String numeroDemandeApp) {
        this.numeroDemandeApp = numeroDemandeApp;
    }

    public String getNom() {
        return this.nom;
    }

    public DemandeMatriculeApp nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public DemandeMatriculeApp prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Long getNumIdNational() {
        return this.numIdNational;
    }

    public DemandeMatriculeApp numIdNational(Long numIdNational) {
        this.setNumIdNational(numIdNational);
        return this;
    }

    public void setNumIdNational(Long numIdNational) {
        this.numIdNational = numIdNational;
    }

    public Sexe getSexe() {
        return this.sexe;
    }

    public DemandeMatriculeApp sexe(Sexe sexe) {
        this.setSexe(sexe);
        return this;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public String getEmail() {
        return this.email;
    }

    public DemandeMatriculeApp email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateNaissance() {
        return this.dateNaissance;
    }

    public DemandeMatriculeApp dateNaissance(LocalDate dateNaissance) {
        this.setDateNaissance(dateNaissance);
        return this;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {
        return this.lieuNaissance;
    }

    public DemandeMatriculeApp lieuNaissance(String lieuNaissance) {
        this.setLieuNaissance(lieuNaissance);
        return this;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public DemandeMatriculeApp adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEtabFrequente() {
        return this.etabFrequente;
    }

    public DemandeMatriculeApp etabFrequente(String etabFrequente) {
        this.setEtabFrequente(etabFrequente);
        return this;
    }

    public void setEtabFrequente(String etabFrequente) {
        this.etabFrequente = etabFrequente;
    }

    public String getMatriculeEtab() {
        return this.matriculeEtab;
    }

    public DemandeMatriculeApp matriculeEtab(String matriculeEtab) {
        this.setMatriculeEtab(matriculeEtab);
        return this;
    }

    public void setMatriculeEtab(String matriculeEtab) {
        this.matriculeEtab = matriculeEtab;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemandeMatriculeApp)) {
            return false;
        }
        return id != null && id.equals(((DemandeMatriculeApp) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandeMatriculeApp{" +
            "id=" + getId() +
            ", numeroDemandeApp='" + getNumeroDemandeApp() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", numIdNational=" + getNumIdNational() +
            ", sexe='" + getSexe() + "'" +
            ", email='" + getEmail() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", lieuNaissance='" + getLieuNaissance() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", etabFrequente='" + getEtabFrequente() + "'" +
            ", matriculeEtab='" + getMatriculeEtab() + "'" +
            "}";
    }
}
