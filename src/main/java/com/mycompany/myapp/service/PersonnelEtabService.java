package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.PersonnelEtab;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link PersonnelEtab}.
 */
public interface PersonnelEtabService {
    /**
     * Save a personnelEtab.
     *
     * @param personnelEtab the entity to save.
     * @return the persisted entity.
     */
    PersonnelEtab save(PersonnelEtab personnelEtab);

    /**
     * Partially updates a personnelEtab.
     *
     * @param personnelEtab the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PersonnelEtab> partialUpdate(PersonnelEtab personnelEtab);

    /**
     * Get all the personnelEtabs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PersonnelEtab> findAll(Pageable pageable);

    /**
     * Get all the personnelEtabs with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PersonnelEtab> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" personnelEtab.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PersonnelEtab> findOne(Long id);

    /**
     * Delete the "id" personnelEtab.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
