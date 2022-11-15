package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Matiere.
 */
@Entity
@Table(name = "matiere")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Matiere implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nom_matiere", nullable = false)
    private String nomMatiere;

    @NotNull
    @Column(name = "reference", nullable = false, unique = true)
    private String reference;

    @Column(name = "type_matiere")
    private String typeMatiere;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "matricule_matiere", unique = true)
    private String matriculeMatiere;

    @ManyToOne
    @JsonIgnoreProperties(value = { "demandeMatriculeEtab", "apprenants", "personnelEtabs", "matieres" }, allowSetters = true)
    private Etablissement etablissement;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Matiere id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomMatiere() {
        return this.nomMatiere;
    }

    public Matiere nomMatiere(String nomMatiere) {
        this.setNomMatiere(nomMatiere);
        return this;
    }

    public void setNomMatiere(String nomMatiere) {
        this.nomMatiere = nomMatiere;
    }

    public String getReference() {
        return this.reference;
    }

    public Matiere reference(String reference) {
        this.setReference(reference);
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getTypeMatiere() {
        return this.typeMatiere;
    }

    public Matiere typeMatiere(String typeMatiere) {
        this.setTypeMatiere(typeMatiere);
        return this;
    }

    public void setTypeMatiere(String typeMatiere) {
        this.typeMatiere = typeMatiere;
    }

    public byte[] getImage() {
        return this.image;
    }

    public Matiere image(byte[] image) {
        this.setImage(image);
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return this.imageContentType;
    }

    public Matiere imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getMatriculeMatiere() {
        return this.matriculeMatiere;
    }

    public Matiere matriculeMatiere(String matriculeMatiere) {
        this.setMatriculeMatiere(matriculeMatiere);
        return this;
    }

    public void setMatriculeMatiere(String matriculeMatiere) {
        this.matriculeMatiere = matriculeMatiere;
    }

    public Etablissement getEtablissement() {
        return this.etablissement;
    }

    public void setEtablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
    }

    public Matiere etablissement(Etablissement etablissement) {
        this.setEtablissement(etablissement);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Matiere)) {
            return false;
        }
        return id != null && id.equals(((Matiere) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Matiere{" +
            "id=" + getId() +
            ", nomMatiere='" + getNomMatiere() + "'" +
            ", reference='" + getReference() + "'" +
            ", typeMatiere='" + getTypeMatiere() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", matriculeMatiere='" + getMatriculeMatiere() + "'" +
            "}";
    }
}
