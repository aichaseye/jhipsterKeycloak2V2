package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.DemandeMatriculeApp;
import com.mycompany.myapp.repository.DemandeMatriculeAppRepository;
import com.mycompany.myapp.service.DemandeMatriculeAppService;
import java.util.Calendar;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DemandeMatriculeApp}.
 */
@Service
@Transactional
public class DemandeMatriculeAppServiceImpl implements DemandeMatriculeAppService {

    private final Logger log = LoggerFactory.getLogger(DemandeMatriculeAppServiceImpl.class);

    private final DemandeMatriculeAppRepository demandeMatriculeAppRepository;

    public DemandeMatriculeAppServiceImpl(DemandeMatriculeAppRepository demandeMatriculeAppRepository) {
        this.demandeMatriculeAppRepository = demandeMatriculeAppRepository;
    }

    @Override
    public DemandeMatriculeApp save(DemandeMatriculeApp demandeMatriculeApp) {
        log.debug("Request to save DemandeMatriculeApp : {}", demandeMatriculeApp);

        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        String date = String.valueOf(System.currentTimeMillis());

        String matricule = year.substring(year.length() - 2).concat(date.substring(date.length() - 4));

        demandeMatriculeApp.setNumeroDemandeApp(matricule);

        return demandeMatriculeAppRepository.save(demandeMatriculeApp);
    }

    @Override
    public Optional<DemandeMatriculeApp> partialUpdate(DemandeMatriculeApp demandeMatriculeApp) {
        log.debug("Request to partially update DemandeMatriculeApp : {}", demandeMatriculeApp);

        return demandeMatriculeAppRepository
            .findById(demandeMatriculeApp.getId())
            .map(existingDemandeMatriculeApp -> {
                if (demandeMatriculeApp.getNumeroDemandeApp() != null) {
                    existingDemandeMatriculeApp.setNumeroDemandeApp(demandeMatriculeApp.getNumeroDemandeApp());
                }
                if (demandeMatriculeApp.getNom() != null) {
                    existingDemandeMatriculeApp.setNom(demandeMatriculeApp.getNom());
                }
                if (demandeMatriculeApp.getPrenom() != null) {
                    existingDemandeMatriculeApp.setPrenom(demandeMatriculeApp.getPrenom());
                }
                if (demandeMatriculeApp.getNumIdNational() != null) {
                    existingDemandeMatriculeApp.setNumIdNational(demandeMatriculeApp.getNumIdNational());
                }
                if (demandeMatriculeApp.getSexe() != null) {
                    existingDemandeMatriculeApp.setSexe(demandeMatriculeApp.getSexe());
                }
                if (demandeMatriculeApp.getEmail() != null) {
                    existingDemandeMatriculeApp.setEmail(demandeMatriculeApp.getEmail());
                }
                if (demandeMatriculeApp.getDateNaissance() != null) {
                    existingDemandeMatriculeApp.setDateNaissance(demandeMatriculeApp.getDateNaissance());
                }
                if (demandeMatriculeApp.getLieuNaissance() != null) {
                    existingDemandeMatriculeApp.setLieuNaissance(demandeMatriculeApp.getLieuNaissance());
                }
                if (demandeMatriculeApp.getAdresse() != null) {
                    existingDemandeMatriculeApp.setAdresse(demandeMatriculeApp.getAdresse());
                }
                if (demandeMatriculeApp.getEtabFrequente() != null) {
                    existingDemandeMatriculeApp.setEtabFrequente(demandeMatriculeApp.getEtabFrequente());
                }
                if (demandeMatriculeApp.getMatriculeEtab() != null) {
                    existingDemandeMatriculeApp.setMatriculeEtab(demandeMatriculeApp.getMatriculeEtab());
                }

                return existingDemandeMatriculeApp;
            })
            .map(demandeMatriculeAppRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DemandeMatriculeApp> findAll(Pageable pageable) {
        log.debug("Request to get all DemandeMatriculeApps");
        return demandeMatriculeAppRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DemandeMatriculeApp> findOne(Long id) {
        log.debug("Request to get DemandeMatriculeApp : {}", id);
        return demandeMatriculeAppRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DemandeMatriculeApp : {}", id);
        demandeMatriculeAppRepository.deleteById(id);
    }
}
