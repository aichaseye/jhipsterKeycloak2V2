package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.EtatDemande;
import com.mycompany.myapp.repository.EtatDemandeRepository;
import com.mycompany.myapp.service.EtatDemandeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EtatDemande}.
 */
@Service
@Transactional
public class EtatDemandeServiceImpl implements EtatDemandeService {

    private final Logger log = LoggerFactory.getLogger(EtatDemandeServiceImpl.class);

    private final EtatDemandeRepository etatDemandeRepository;

    public EtatDemandeServiceImpl(EtatDemandeRepository etatDemandeRepository) {
        this.etatDemandeRepository = etatDemandeRepository;
    }

    @Override
    public EtatDemande save(EtatDemande etatDemande) {
        log.debug("Request to save EtatDemande : {}", etatDemande);
        return etatDemandeRepository.save(etatDemande);
    }

    @Override
    public Optional<EtatDemande> partialUpdate(EtatDemande etatDemande) {
        log.debug("Request to partially update EtatDemande : {}", etatDemande);

        return etatDemandeRepository
            .findById(etatDemande.getId())
            .map(existingEtatDemande -> {
                if (etatDemande.getEtat() != null) {
                    existingEtatDemande.setEtat(etatDemande.getEtat());
                }
                if (etatDemande.getDateDisponibilite() != null) {
                    existingEtatDemande.setDateDisponibilite(etatDemande.getDateDisponibilite());
                }
                if (etatDemande.getDescription() != null) {
                    existingEtatDemande.setDescription(etatDemande.getDescription());
                }

                return existingEtatDemande;
            })
            .map(etatDemandeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EtatDemande> findAll(Pageable pageable) {
        log.debug("Request to get all EtatDemandes");
        return etatDemandeRepository.findAll(pageable);
    }

    public Page<EtatDemande> findAllWithEagerRelationships(Pageable pageable) {
        return etatDemandeRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EtatDemande> findOne(Long id) {
        log.debug("Request to get EtatDemande : {}", id);
        return etatDemandeRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EtatDemande : {}", id);
        etatDemandeRepository.deleteById(id);
    }
}
