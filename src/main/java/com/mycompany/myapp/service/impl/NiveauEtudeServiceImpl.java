package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.NiveauEtude;
import com.mycompany.myapp.repository.NiveauEtudeRepository;
import com.mycompany.myapp.service.NiveauEtudeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NiveauEtude}.
 */
@Service
@Transactional
public class NiveauEtudeServiceImpl implements NiveauEtudeService {

    private final Logger log = LoggerFactory.getLogger(NiveauEtudeServiceImpl.class);

    private final NiveauEtudeRepository niveauEtudeRepository;

    public NiveauEtudeServiceImpl(NiveauEtudeRepository niveauEtudeRepository) {
        this.niveauEtudeRepository = niveauEtudeRepository;
    }

    @Override
    public NiveauEtude save(NiveauEtude niveauEtude) {
        log.debug("Request to save NiveauEtude : {}", niveauEtude);
        return niveauEtudeRepository.save(niveauEtude);
    }

    @Override
    public Optional<NiveauEtude> partialUpdate(NiveauEtude niveauEtude) {
        log.debug("Request to partially update NiveauEtude : {}", niveauEtude);

        return niveauEtudeRepository
            .findById(niveauEtude.getId())
            .map(existingNiveauEtude -> {
                if (niveauEtude.getNiveau() != null) {
                    existingNiveauEtude.setNiveau(niveauEtude.getNiveau());
                }
                if (niveauEtude.getAnneeEtude() != null) {
                    existingNiveauEtude.setAnneeEtude(niveauEtude.getAnneeEtude());
                }

                return existingNiveauEtude;
            })
            .map(niveauEtudeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NiveauEtude> findAll(Pageable pageable) {
        log.debug("Request to get all NiveauEtudes");
        return niveauEtudeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NiveauEtude> findOne(Long id) {
        log.debug("Request to get NiveauEtude : {}", id);
        return niveauEtudeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NiveauEtude : {}", id);
        niveauEtudeRepository.deleteById(id);
    }
}
