package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PersonnelEtab;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PersonnelEtab entity.
 */
@Repository
public interface PersonnelEtabRepository extends JpaRepository<PersonnelEtab, Long> {
    default Optional<PersonnelEtab> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<PersonnelEtab> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<PersonnelEtab> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct personnelEtab from PersonnelEtab personnelEtab left join fetch personnelEtab.user left join fetch personnelEtab.etablissement",
        countQuery = "select count(distinct personnelEtab) from PersonnelEtab personnelEtab"
    )
    Page<PersonnelEtab> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct personnelEtab from PersonnelEtab personnelEtab left join fetch personnelEtab.user left join fetch personnelEtab.etablissement"
    )
    List<PersonnelEtab> findAllWithToOneRelationships();

    @Query(
        "select personnelEtab from PersonnelEtab personnelEtab left join fetch personnelEtab.user left join fetch personnelEtab.etablissement where personnelEtab.id =:id"
    )
    Optional<PersonnelEtab> findOneWithToOneRelationships(@Param("id") Long id);
}
