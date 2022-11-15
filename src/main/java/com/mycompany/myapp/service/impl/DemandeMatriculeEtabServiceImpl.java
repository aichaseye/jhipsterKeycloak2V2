package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.DemandeMatriculeEtab;
import com.mycompany.myapp.repository.DemandeMatriculeEtabRepository;
import com.mycompany.myapp.service.DemandeMatriculeEtabService;
import java.util.Calendar;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DemandeMatriculeEtab}.
 */
@Service
@Transactional
public class DemandeMatriculeEtabServiceImpl implements DemandeMatriculeEtabService {

    private final Logger log = LoggerFactory.getLogger(DemandeMatriculeEtabServiceImpl.class);

    private final DemandeMatriculeEtabRepository demandeMatriculeEtabRepository;

    public DemandeMatriculeEtabServiceImpl(DemandeMatriculeEtabRepository demandeMatriculeEtabRepository) {
        this.demandeMatriculeEtabRepository = demandeMatriculeEtabRepository;
    }

    @Override
    public DemandeMatriculeEtab save(DemandeMatriculeEtab demandeMatriculeEtab) {
        log.debug("Request to save DemandeMatriculeEtab : {}", demandeMatriculeEtab);

        String date = String.valueOf(System.currentTimeMillis());

        String matricule = date.concat(date.substring(date.length() - 4)).concat(demandeMatriculeEtab.getNomEtab());

        demandeMatriculeEtab.setNumeroDemandeEtab(matricule);

        return demandeMatriculeEtabRepository.save(demandeMatriculeEtab);
    }

    @Override
    public Optional<DemandeMatriculeEtab> partialUpdate(DemandeMatriculeEtab demandeMatriculeEtab) {
        log.debug("Request to partially update DemandeMatriculeEtab : {}", demandeMatriculeEtab);

        return demandeMatriculeEtabRepository
            .findById(demandeMatriculeEtab.getId())
            .map(existingDemandeMatriculeEtab -> {
                if (demandeMatriculeEtab.getNumeroDemandeEtab() != null) {
                    existingDemandeMatriculeEtab.setNumeroDemandeEtab(demandeMatriculeEtab.getNumeroDemandeEtab());
                }
                if (demandeMatriculeEtab.getNomEtab() != null) {
                    existingDemandeMatriculeEtab.setNomEtab(demandeMatriculeEtab.getNomEtab());
                }
                if (demandeMatriculeEtab.getTypeEtab() != null) {
                    existingDemandeMatriculeEtab.setTypeEtab(demandeMatriculeEtab.getTypeEtab());
                }
                if (demandeMatriculeEtab.getStatut() != null) {
                    existingDemandeMatriculeEtab.setStatut(demandeMatriculeEtab.getStatut());
                }
                if (demandeMatriculeEtab.getEmailEtab() != null) {
                    existingDemandeMatriculeEtab.setEmailEtab(demandeMatriculeEtab.getEmailEtab());
                }
                if (demandeMatriculeEtab.getRegion() != null) {
                    existingDemandeMatriculeEtab.setRegion(demandeMatriculeEtab.getRegion());
                }
                if (demandeMatriculeEtab.getDepartement() != null) {
                    existingDemandeMatriculeEtab.setDepartement(demandeMatriculeEtab.getDepartement());
                }
                if (demandeMatriculeEtab.getCommune() != null) {
                    existingDemandeMatriculeEtab.setCommune(demandeMatriculeEtab.getCommune());
                }
                if (demandeMatriculeEtab.getTypeInsp() != null) {
                    existingDemandeMatriculeEtab.setTypeInsp(demandeMatriculeEtab.getTypeInsp());
                }

                return existingDemandeMatriculeEtab;
            })
            .map(demandeMatriculeEtabRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DemandeMatriculeEtab> findAll(Pageable pageable) {
        log.debug("Request to get all DemandeMatriculeEtabs");
        return demandeMatriculeEtabRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DemandeMatriculeEtab> findOne(Long id) {
        log.debug("Request to get DemandeMatriculeEtab : {}", id);
        return demandeMatriculeEtabRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DemandeMatriculeEtab : {}", id);
        demandeMatriculeEtabRepository.deleteById(id);
    }
}
