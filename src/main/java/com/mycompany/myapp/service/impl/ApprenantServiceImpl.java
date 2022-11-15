package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Apprenant;
import com.mycompany.myapp.domain.enumeration.Sexe;
import com.mycompany.myapp.repository.ApprenantRepository;
import com.mycompany.myapp.service.ApprenantService;
import java.util.Calendar;
import java.util.Optional;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Apprenant}.
 */
@Service
@Transactional
public class ApprenantServiceImpl implements ApprenantService {

    private final Logger log = LoggerFactory.getLogger(ApprenantServiceImpl.class);

    private final ApprenantRepository apprenantRepository;

    public ApprenantServiceImpl(ApprenantRepository apprenantRepository) {
        this.apprenantRepository = apprenantRepository;
    }

    @Override
    public Apprenant save(Apprenant apprenant) {
        log.debug("Request to save Apprenant : {}", apprenant);

        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        String date = String.valueOf(System.currentTimeMillis());

        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();
        char letter = alphabet.charAt(rnd.nextInt(alphabet.length()));

        String sexe = "M";
        if (apprenant.getSexe().equals(Sexe.Feminin)) {
            sexe = "F";
        }

        String matricule = year
            .substring(year.length() - 2)
            .concat(sexe)
            .concat(date.substring(date.length() - 4))
            .concat(String.valueOf(letter));

        apprenant.setMatriculeApp(matricule);

        return apprenantRepository.save(apprenant);
    }

    @Override
    public Optional<Apprenant> partialUpdate(Apprenant apprenant) {
        log.debug("Request to partially update Apprenant : {}", apprenant);

        return apprenantRepository
            .findById(apprenant.getId())
            .map(existingApprenant -> {
                if (apprenant.getMatriculeApp() != null) {
                    existingApprenant.setMatriculeApp(apprenant.getMatriculeApp());
                }
                if (apprenant.getNom() != null) {
                    existingApprenant.setNom(apprenant.getNom());
                }
                if (apprenant.getPrenom() != null) {
                    existingApprenant.setPrenom(apprenant.getPrenom());
                }
                if (apprenant.getNumIdNational() != null) {
                    existingApprenant.setNumIdNational(apprenant.getNumIdNational());
                }
                if (apprenant.getSexe() != null) {
                    existingApprenant.setSexe(apprenant.getSexe());
                }
                if (apprenant.getEmail() != null) {
                    existingApprenant.setEmail(apprenant.getEmail());
                }
                if (apprenant.getDateNaissance() != null) {
                    existingApprenant.setDateNaissance(apprenant.getDateNaissance());
                }
                if (apprenant.getLieuNaissance() != null) {
                    existingApprenant.setLieuNaissance(apprenant.getLieuNaissance());
                }
                if (apprenant.getAdresse() != null) {
                    existingApprenant.setAdresse(apprenant.getAdresse());
                }

                return existingApprenant;
            })
            .map(apprenantRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Apprenant> findAll(Pageable pageable) {
        log.debug("Request to get all Apprenants");
        return apprenantRepository.findAll(pageable);
    }

    public Page<Apprenant> findAllWithEagerRelationships(Pageable pageable) {
        return apprenantRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Apprenant> findOne(Long id) {
        log.debug("Request to get Apprenant : {}", id);
        return apprenantRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Apprenant : {}", id);
        apprenantRepository.deleteById(id);
    }
}
