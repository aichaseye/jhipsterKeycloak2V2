package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Etablissement;
import com.mycompany.myapp.domain.enumeration.TypeEtab;
import com.mycompany.myapp.repository.EtablissementRepository;
import com.mycompany.myapp.service.EtablissementService;
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
 * Service Implementation for managing {@link Etablissement}.
 */
@Service
@Transactional
public class EtablissementServiceImpl implements EtablissementService {

    private final Logger log = LoggerFactory.getLogger(EtablissementServiceImpl.class);

    private final EtablissementRepository etablissementRepository;

    public EtablissementServiceImpl(EtablissementRepository etablissementRepository) {
        this.etablissementRepository = etablissementRepository;
    }

    @Override
    public Etablissement save(Etablissement etablissement) {
        log.debug("Request to save Etablissement : {}", etablissement);

        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        String date = String.valueOf(System.currentTimeMillis());
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();
        char letter = alphabet.charAt(rnd.nextInt(alphabet.length()));

        String type = "L";
        if (etablissement.getTypeEtab().equals(TypeEtab.CFP)) {
            type = "C";
        }

        String matricule = year
            .substring(year.length() - 2)
            .concat(type)
            .concat(date.substring(date.length() - 3))
            .concat(String.valueOf(letter));

        etablissement.setMatriculeEtab(matricule);
        return etablissementRepository.save(etablissement);
    }

    @Override
    public Optional<Etablissement> partialUpdate(Etablissement etablissement) {
        log.debug("Request to partially update Etablissement : {}", etablissement);

        return etablissementRepository
            .findById(etablissement.getId())
            .map(existingEtablissement -> {
                if (etablissement.getMatriculeEtab() != null) {
                    existingEtablissement.setMatriculeEtab(etablissement.getMatriculeEtab());
                }
                if (etablissement.getNomEtab() != null) {
                    existingEtablissement.setNomEtab(etablissement.getNomEtab());
                }
                if (etablissement.getTypeEtab() != null) {
                    existingEtablissement.setTypeEtab(etablissement.getTypeEtab());
                }
                if (etablissement.getStatut() != null) {
                    existingEtablissement.setStatut(etablissement.getStatut());
                }
                if (etablissement.getEmailEtab() != null) {
                    existingEtablissement.setEmailEtab(etablissement.getEmailEtab());
                }
                if (etablissement.getRegion() != null) {
                    existingEtablissement.setRegion(etablissement.getRegion());
                }
                if (etablissement.getDepartement() != null) {
                    existingEtablissement.setDepartement(etablissement.getDepartement());
                }
                if (etablissement.getCommune() != null) {
                    existingEtablissement.setCommune(etablissement.getCommune());
                }
                if (etablissement.getTypeInsp() != null) {
                    existingEtablissement.setTypeInsp(etablissement.getTypeInsp());
                }

                return existingEtablissement;
            })
            .map(etablissementRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Etablissement> findAll(Pageable pageable) {
        log.debug("Request to get all Etablissements");
        return etablissementRepository.findAll(pageable);
    }

    public Page<Etablissement> findAllWithEagerRelationships(Pageable pageable) {
        return etablissementRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Etablissement> findOne(Long id) {
        log.debug("Request to get Etablissement : {}", id);
        return etablissementRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Etablissement : {}", id);
        etablissementRepository.deleteById(id);
    }
}
