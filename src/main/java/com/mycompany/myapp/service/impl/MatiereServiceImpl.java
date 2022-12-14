package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Matiere;
import com.mycompany.myapp.repository.MatiereRepository;
import com.mycompany.myapp.service.MatiereService;
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
 * Service Implementation for managing {@link Matiere}.
 */
@Service
@Transactional
public class MatiereServiceImpl implements MatiereService {

    private final Logger log = LoggerFactory.getLogger(MatiereServiceImpl.class);

    private final MatiereRepository matiereRepository;

    public MatiereServiceImpl(MatiereRepository matiereRepository) {
        this.matiereRepository = matiereRepository;
    }

    @Override
    public Matiere save(Matiere matiere) {
        log.debug("Request to save Matiere : {}", matiere);

        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        String date = String.valueOf(System.currentTimeMillis());

        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();
        char letter = alphabet.charAt(rnd.nextInt(alphabet.length()));

        String matricule = year
            .substring(year.length() - 2)
            .concat(matiere.getReference().substring(matiere.getReference().length() - 2))
            .concat(date.substring(date.length() - 4))
            .concat(String.valueOf(letter));

        matiere.setMatriculeMatiere(matricule);

        return matiereRepository.save(matiere);
    }

    @Override
    public Optional<Matiere> partialUpdate(Matiere matiere) {
        log.debug("Request to partially update Matiere : {}", matiere);

        return matiereRepository
            .findById(matiere.getId())
            .map(existingMatiere -> {
                if (matiere.getNomMatiere() != null) {
                    existingMatiere.setNomMatiere(matiere.getNomMatiere());
                }
                if (matiere.getReference() != null) {
                    existingMatiere.setReference(matiere.getReference());
                }
                if (matiere.getTypeMatiere() != null) {
                    existingMatiere.setTypeMatiere(matiere.getTypeMatiere());
                }
                if (matiere.getImage() != null) {
                    existingMatiere.setImage(matiere.getImage());
                }
                if (matiere.getImageContentType() != null) {
                    existingMatiere.setImageContentType(matiere.getImageContentType());
                }
                if (matiere.getMatriculeMatiere() != null) {
                    existingMatiere.setMatriculeMatiere(matiere.getMatriculeMatiere());
                }

                return existingMatiere;
            })
            .map(matiereRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Matiere> findAll(Pageable pageable) {
        log.debug("Request to get all Matieres");
        return matiereRepository.findAll(pageable);
    }

    public Page<Matiere> findAllWithEagerRelationships(Pageable pageable) {
        return matiereRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Matiere> findOne(Long id) {
        log.debug("Request to get Matiere : {}", id);
        return matiereRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Matiere : {}", id);
        matiereRepository.deleteById(id);
    }
}
