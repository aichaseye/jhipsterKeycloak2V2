package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.DemandeMatriculeApp;
import com.mycompany.myapp.domain.EtatDemande;
import com.mycompany.myapp.domain.enumeration.Etat;
import com.mycompany.myapp.repository.EtatDemandeRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link EtatDemandeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EtatDemandeResourceIT {

    private static final Etat DEFAULT_ETAT = Etat.Disponible;
    private static final Etat UPDATED_ETAT = Etat.Indisponible;

    private static final LocalDate DEFAULT_DATE_DISPONIBILITE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DISPONIBILITE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/etat-demandes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EtatDemandeRepository etatDemandeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEtatDemandeMockMvc;

    private EtatDemande etatDemande;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtatDemande createEntity(EntityManager em) {
        EtatDemande etatDemande = new EtatDemande()
            .etat(DEFAULT_ETAT)
            .dateDisponibilite(DEFAULT_DATE_DISPONIBILITE)
            .description(DEFAULT_DESCRIPTION);
        // Add required entity
        DemandeMatriculeApp demandeMatriculeApp;
        if (TestUtil.findAll(em, DemandeMatriculeApp.class).isEmpty()) {
            demandeMatriculeApp = DemandeMatriculeAppResourceIT.createEntity(em);
            em.persist(demandeMatriculeApp);
            em.flush();
        } else {
            demandeMatriculeApp = TestUtil.findAll(em, DemandeMatriculeApp.class).get(0);
        }
        etatDemande.setDemandeMatriculeApp(demandeMatriculeApp);
        return etatDemande;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtatDemande createUpdatedEntity(EntityManager em) {
        EtatDemande etatDemande = new EtatDemande()
            .etat(UPDATED_ETAT)
            .dateDisponibilite(UPDATED_DATE_DISPONIBILITE)
            .description(UPDATED_DESCRIPTION);
        // Add required entity
        DemandeMatriculeApp demandeMatriculeApp;
        if (TestUtil.findAll(em, DemandeMatriculeApp.class).isEmpty()) {
            demandeMatriculeApp = DemandeMatriculeAppResourceIT.createUpdatedEntity(em);
            em.persist(demandeMatriculeApp);
            em.flush();
        } else {
            demandeMatriculeApp = TestUtil.findAll(em, DemandeMatriculeApp.class).get(0);
        }
        etatDemande.setDemandeMatriculeApp(demandeMatriculeApp);
        return etatDemande;
    }

    @BeforeEach
    public void initTest() {
        etatDemande = createEntity(em);
    }

    @Test
    @Transactional
    void createEtatDemande() throws Exception {
        int databaseSizeBeforeCreate = etatDemandeRepository.findAll().size();
        // Create the EtatDemande
        restEtatDemandeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(etatDemande))
            )
            .andExpect(status().isCreated());

        // Validate the EtatDemande in the database
        List<EtatDemande> etatDemandeList = etatDemandeRepository.findAll();
        assertThat(etatDemandeList).hasSize(databaseSizeBeforeCreate + 1);
        EtatDemande testEtatDemande = etatDemandeList.get(etatDemandeList.size() - 1);
        assertThat(testEtatDemande.getEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testEtatDemande.getDateDisponibilite()).isEqualTo(DEFAULT_DATE_DISPONIBILITE);
        assertThat(testEtatDemande.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createEtatDemandeWithExistingId() throws Exception {
        // Create the EtatDemande with an existing ID
        etatDemande.setId(1L);

        int databaseSizeBeforeCreate = etatDemandeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtatDemandeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(etatDemande))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtatDemande in the database
        List<EtatDemande> etatDemandeList = etatDemandeRepository.findAll();
        assertThat(etatDemandeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEtatIsRequired() throws Exception {
        int databaseSizeBeforeTest = etatDemandeRepository.findAll().size();
        // set the field null
        etatDemande.setEtat(null);

        // Create the EtatDemande, which fails.

        restEtatDemandeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(etatDemande))
            )
            .andExpect(status().isBadRequest());

        List<EtatDemande> etatDemandeList = etatDemandeRepository.findAll();
        assertThat(etatDemandeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEtatDemandes() throws Exception {
        // Initialize the database
        etatDemandeRepository.saveAndFlush(etatDemande);

        // Get all the etatDemandeList
        restEtatDemandeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etatDemande.getId().intValue())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())))
            .andExpect(jsonPath("$.[*].dateDisponibilite").value(hasItem(DEFAULT_DATE_DISPONIBILITE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    void getEtatDemande() throws Exception {
        // Initialize the database
        etatDemandeRepository.saveAndFlush(etatDemande);

        // Get the etatDemande
        restEtatDemandeMockMvc
            .perform(get(ENTITY_API_URL_ID, etatDemande.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(etatDemande.getId().intValue()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()))
            .andExpect(jsonPath("$.dateDisponibilite").value(DEFAULT_DATE_DISPONIBILITE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingEtatDemande() throws Exception {
        // Get the etatDemande
        restEtatDemandeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEtatDemande() throws Exception {
        // Initialize the database
        etatDemandeRepository.saveAndFlush(etatDemande);

        int databaseSizeBeforeUpdate = etatDemandeRepository.findAll().size();

        // Update the etatDemande
        EtatDemande updatedEtatDemande = etatDemandeRepository.findById(etatDemande.getId()).get();
        // Disconnect from session so that the updates on updatedEtatDemande are not directly saved in db
        em.detach(updatedEtatDemande);
        updatedEtatDemande.etat(UPDATED_ETAT).dateDisponibilite(UPDATED_DATE_DISPONIBILITE).description(UPDATED_DESCRIPTION);

        restEtatDemandeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEtatDemande.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEtatDemande))
            )
            .andExpect(status().isOk());

        // Validate the EtatDemande in the database
        List<EtatDemande> etatDemandeList = etatDemandeRepository.findAll();
        assertThat(etatDemandeList).hasSize(databaseSizeBeforeUpdate);
        EtatDemande testEtatDemande = etatDemandeList.get(etatDemandeList.size() - 1);
        assertThat(testEtatDemande.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testEtatDemande.getDateDisponibilite()).isEqualTo(UPDATED_DATE_DISPONIBILITE);
        assertThat(testEtatDemande.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingEtatDemande() throws Exception {
        int databaseSizeBeforeUpdate = etatDemandeRepository.findAll().size();
        etatDemande.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtatDemandeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, etatDemande.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(etatDemande))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtatDemande in the database
        List<EtatDemande> etatDemandeList = etatDemandeRepository.findAll();
        assertThat(etatDemandeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEtatDemande() throws Exception {
        int databaseSizeBeforeUpdate = etatDemandeRepository.findAll().size();
        etatDemande.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtatDemandeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(etatDemande))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtatDemande in the database
        List<EtatDemande> etatDemandeList = etatDemandeRepository.findAll();
        assertThat(etatDemandeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEtatDemande() throws Exception {
        int databaseSizeBeforeUpdate = etatDemandeRepository.findAll().size();
        etatDemande.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtatDemandeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(etatDemande))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EtatDemande in the database
        List<EtatDemande> etatDemandeList = etatDemandeRepository.findAll();
        assertThat(etatDemandeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEtatDemandeWithPatch() throws Exception {
        // Initialize the database
        etatDemandeRepository.saveAndFlush(etatDemande);

        int databaseSizeBeforeUpdate = etatDemandeRepository.findAll().size();

        // Update the etatDemande using partial update
        EtatDemande partialUpdatedEtatDemande = new EtatDemande();
        partialUpdatedEtatDemande.setId(etatDemande.getId());

        partialUpdatedEtatDemande.description(UPDATED_DESCRIPTION);

        restEtatDemandeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEtatDemande.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEtatDemande))
            )
            .andExpect(status().isOk());

        // Validate the EtatDemande in the database
        List<EtatDemande> etatDemandeList = etatDemandeRepository.findAll();
        assertThat(etatDemandeList).hasSize(databaseSizeBeforeUpdate);
        EtatDemande testEtatDemande = etatDemandeList.get(etatDemandeList.size() - 1);
        assertThat(testEtatDemande.getEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testEtatDemande.getDateDisponibilite()).isEqualTo(DEFAULT_DATE_DISPONIBILITE);
        assertThat(testEtatDemande.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateEtatDemandeWithPatch() throws Exception {
        // Initialize the database
        etatDemandeRepository.saveAndFlush(etatDemande);

        int databaseSizeBeforeUpdate = etatDemandeRepository.findAll().size();

        // Update the etatDemande using partial update
        EtatDemande partialUpdatedEtatDemande = new EtatDemande();
        partialUpdatedEtatDemande.setId(etatDemande.getId());

        partialUpdatedEtatDemande.etat(UPDATED_ETAT).dateDisponibilite(UPDATED_DATE_DISPONIBILITE).description(UPDATED_DESCRIPTION);

        restEtatDemandeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEtatDemande.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEtatDemande))
            )
            .andExpect(status().isOk());

        // Validate the EtatDemande in the database
        List<EtatDemande> etatDemandeList = etatDemandeRepository.findAll();
        assertThat(etatDemandeList).hasSize(databaseSizeBeforeUpdate);
        EtatDemande testEtatDemande = etatDemandeList.get(etatDemandeList.size() - 1);
        assertThat(testEtatDemande.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testEtatDemande.getDateDisponibilite()).isEqualTo(UPDATED_DATE_DISPONIBILITE);
        assertThat(testEtatDemande.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingEtatDemande() throws Exception {
        int databaseSizeBeforeUpdate = etatDemandeRepository.findAll().size();
        etatDemande.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtatDemandeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, etatDemande.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(etatDemande))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtatDemande in the database
        List<EtatDemande> etatDemandeList = etatDemandeRepository.findAll();
        assertThat(etatDemandeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEtatDemande() throws Exception {
        int databaseSizeBeforeUpdate = etatDemandeRepository.findAll().size();
        etatDemande.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtatDemandeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(etatDemande))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtatDemande in the database
        List<EtatDemande> etatDemandeList = etatDemandeRepository.findAll();
        assertThat(etatDemandeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEtatDemande() throws Exception {
        int databaseSizeBeforeUpdate = etatDemandeRepository.findAll().size();
        etatDemande.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtatDemandeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(etatDemande))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EtatDemande in the database
        List<EtatDemande> etatDemandeList = etatDemandeRepository.findAll();
        assertThat(etatDemandeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEtatDemande() throws Exception {
        // Initialize the database
        etatDemandeRepository.saveAndFlush(etatDemande);

        int databaseSizeBeforeDelete = etatDemandeRepository.findAll().size();

        // Delete the etatDemande
        restEtatDemandeMockMvc
            .perform(delete(ENTITY_API_URL_ID, etatDemande.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EtatDemande> etatDemandeList = etatDemandeRepository.findAll();
        assertThat(etatDemandeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
