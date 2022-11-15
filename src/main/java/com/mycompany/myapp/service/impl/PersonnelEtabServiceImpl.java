package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.PersonnelEtab;
import com.mycompany.myapp.repository.PersonnelEtabRepository;
import com.mycompany.myapp.service.PersonnelEtabService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PersonnelEtab}.
 */
@Service
@Transactional
public class PersonnelEtabServiceImpl implements PersonnelEtabService {

    private final Logger log = LoggerFactory.getLogger(PersonnelEtabServiceImpl.class);

    private final PersonnelEtabRepository personnelEtabRepository;

    public PersonnelEtabServiceImpl(PersonnelEtabRepository personnelEtabRepository) {
        this.personnelEtabRepository = personnelEtabRepository;
    }

    @Override
    public PersonnelEtab save(PersonnelEtab personnelEtab) {
        log.debug("Request to save PersonnelEtab : {}", personnelEtab);
        return personnelEtabRepository.save(personnelEtab);
    }

    @Override
    public Optional<PersonnelEtab> partialUpdate(PersonnelEtab personnelEtab) {
        log.debug("Request to partially update PersonnelEtab : {}", personnelEtab);

        return personnelEtabRepository
            .findById(personnelEtab.getId())
            .map(existingPersonnelEtab -> {
                if (personnelEtab.getNom() != null) {
                    existingPersonnelEtab.setNom(personnelEtab.getNom());
                }
                if (personnelEtab.getPrenom() != null) {
                    existingPersonnelEtab.setPrenom(personnelEtab.getPrenom());
                }
                if (personnelEtab.getFonction() != null) {
                    existingPersonnelEtab.setFonction(personnelEtab.getFonction());
                }

                return existingPersonnelEtab;
            })
            .map(personnelEtabRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PersonnelEtab> findAll(Pageable pageable) {
        log.debug("Request to get all PersonnelEtabs");
        return personnelEtabRepository.findAll(pageable);
    }

    public Page<PersonnelEtab> findAllWithEagerRelationships(Pageable pageable) {
        return personnelEtabRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonnelEtab> findOne(Long id) {
        log.debug("Request to get PersonnelEtab : {}", id);
        return personnelEtabRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PersonnelEtab : {}", id);
        personnelEtabRepository.deleteById(id);
    }
}
