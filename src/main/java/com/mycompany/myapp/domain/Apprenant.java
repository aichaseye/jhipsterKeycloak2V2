package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Sexe;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Apprenant.
 */
@Entity
@Table(name = "apprenant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Apprenant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "matricule_app", unique = true)
    private String matriculeApp;

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

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToOne
    @JoinColumn(unique = true)
    private DemandeMatriculeApp demandeMatriculeApp;

    @ManyToOne
    @JsonIgnoreProperties(value = { "demandeMatriculeEtab", "apprenants", "personnelEtabs", "matieres" }, allowSetters = true)
    private Etablissement etablissement;

    @ManyToOne
    @JsonIgnoreProperties(value = { "apprenants" }, allowSetters = true)
    private NiveauEtude niveauEtude;

    @ManyToOne
    @JsonIgnoreProperties(value = { "apprenants" }, allowSetters = true)
    private SerieEtude serieEtude;

    @ManyToOne
    @JsonIgnoreProperties(value = { "apprenants" }, allowSetters = true)
    private FiliereEtude filiereEtude;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Apprenant id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatriculeApp() {
        return this.matriculeApp;
    }

    public Apprenant matriculeApp(String matriculeApp) {
        this.setMatriculeApp(matriculeApp);
        return this;
    }

    public void setMatriculeApp(String matriculeApp) {
        this.matriculeApp = matriculeApp;
    }

    public String getNom() {
        return this.nom;
    }

    public Apprenant nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Apprenant prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Long getNumIdNational() {
        return this.numIdNational;
    }

    public Apprenant numIdNational(Long numIdNational) {
        this.setNumIdNational(numIdNational);
        return this;
    }

    public void setNumIdNational(Long numIdNational) {
        this.numIdNational = numIdNational;
    }

    public Sexe getSexe() {
        return this.sexe;
    }

    public Apprenant sexe(Sexe sexe) {
        this.setSexe(sexe);
        return this;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public String getEmail() {
        return this.email;
    }

    public Apprenant email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateNaissance() {
        return this.dateNaissance;
    }

    public Apprenant dateNaissance(LocalDate dateNaissance) {
        this.setDateNaissance(dateNaissance);
        return this;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {
        return this.lieuNaissance;
    }

    public Apprenant lieuNaissance(String lieuNaissance) {
        this.setLieuNaissance(lieuNaissance);
        return this;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Apprenant adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Apprenant user(User user) {
        this.setUser(user);
        return this;
    }

    public DemandeMatriculeApp getDemandeMatriculeApp() {
        return this.demandeMatriculeApp;
    }

    public void setDemandeMatriculeApp(DemandeMatriculeApp demandeMatriculeApp) {
        this.demandeMatriculeApp = demandeMatriculeApp;
    }

    public Apprenant demandeMatriculeApp(DemandeMatriculeApp demandeMatriculeApp) {
        this.setDemandeMatriculeApp(demandeMatriculeApp);
        return this;
    }

    public Etablissement getEtablissement() {
        return this.etablissement;
    }

    public void setEtablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
    }

    public Apprenant etablissement(Etablissement etablissement) {
        this.setEtablissement(etablissement);
        return this;
    }

    public NiveauEtude getNiveauEtude() {
        return this.niveauEtude;
    }

    public void setNiveauEtude(NiveauEtude niveauEtude) {
        this.niveauEtude = niveauEtude;
    }

    public Apprenant niveauEtude(NiveauEtude niveauEtude) {
        this.setNiveauEtude(niveauEtude);
        return this;
    }

    public SerieEtude getSerieEtude() {
        return this.serieEtude;
    }

    public void setSerieEtude(SerieEtude serieEtude) {
        this.serieEtude = serieEtude;
    }

    public Apprenant serieEtude(SerieEtude serieEtude) {
        this.setSerieEtude(serieEtude);
        return this;
    }

    public FiliereEtude getFiliereEtude() {
        return this.filiereEtude;
    }

    public void setFiliereEtude(FiliereEtude filiereEtude) {
        this.filiereEtude = filiereEtude;
    }

    public Apprenant filiereEtude(FiliereEtude filiereEtude) {
        this.setFiliereEtude(filiereEtude);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Apprenant)) {
            return false;
        }
        return id != null && id.equals(((Apprenant) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Apprenant{" +
            "id=" + getId() +
            ", matriculeApp='" + getMatriculeApp() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", numIdNational=" + getNumIdNational() +
            ", sexe='" + getSexe() + "'" +
            ", email='" + getEmail() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", lieuNaissance='" + getLieuNaissance() + "'" +
            ", adresse='" + getAdresse() + "'" +
            "}";
    }
}
