package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.DemandeMatriculeApp;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link DemandeMatriculeApp}.
 */
public interface DemandeMatriculeAppService {
    /**
     * Save a demandeMatriculeApp.
     *
     * @param demandeMatriculeApp the entity to save.
     * @return the persisted entity.
     */
    DemandeMatriculeApp save(DemandeMatriculeApp demandeMatriculeApp);

    /**
     * Partially updates a demandeMatriculeApp.
     *
     * @param demandeMatriculeApp the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DemandeMatriculeApp> partialUpdate(DemandeMatriculeApp demandeMatriculeApp);

    /**
     * Get all the demandeMatriculeApps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DemandeMatriculeApp> findAll(Pageable pageable);

    /**
     * Get the "id" demandeMatriculeApp.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DemandeMatriculeApp> findOne(Long id);

    /**
     * Delete the "id" demandeMatriculeApp.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
