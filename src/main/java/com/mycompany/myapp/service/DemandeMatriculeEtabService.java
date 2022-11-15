package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.DemandeMatriculeEtab;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link DemandeMatriculeEtab}.
 */
public interface DemandeMatriculeEtabService {
    /**
     * Save a demandeMatriculeEtab.
     *
     * @param demandeMatriculeEtab the entity to save.
     * @return the persisted entity.
     */
    DemandeMatriculeEtab save(DemandeMatriculeEtab demandeMatriculeEtab);

    /**
     * Partially updates a demandeMatriculeEtab.
     *
     * @param demandeMatriculeEtab the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DemandeMatriculeEtab> partialUpdate(DemandeMatriculeEtab demandeMatriculeEtab);

    /**
     * Get all the demandeMatriculeEtabs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DemandeMatriculeEtab> findAll(Pageable pageable);

    /**
     * Get the "id" demandeMatriculeEtab.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DemandeMatriculeEtab> findOne(Long id);

    /**
     * Delete the "id" demandeMatriculeEtab.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
