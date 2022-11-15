package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.EtatDemande;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EtatDemande entity.
 */
@Repository
public interface EtatDemandeRepository extends JpaRepository<EtatDemande, Long> {
    default Optional<EtatDemande> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<EtatDemande> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<EtatDemande> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct etatDemande from EtatDemande etatDemande left join fetch etatDemande.demandeMatriculeApp left join fetch etatDemande.demandeMatriculeEtab",
        countQuery = "select count(distinct etatDemande) from EtatDemande etatDemande"
    )
    Page<EtatDemande> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct etatDemande from EtatDemande etatDemande left join fetch etatDemande.demandeMatriculeApp left join fetch etatDemande.demandeMatriculeEtab"
    )
    List<EtatDemande> findAllWithToOneRelationships();

    @Query(
        "select etatDemande from EtatDemande etatDemande left join fetch etatDemande.demandeMatriculeApp left join fetch etatDemande.demandeMatriculeEtab where etatDemande.id =:id"
    )
    Optional<EtatDemande> findOneWithToOneRelationships(@Param("id") Long id);
}
