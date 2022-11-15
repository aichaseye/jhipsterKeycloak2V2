package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Apprenant;
import com.mycompany.myapp.domain.enumeration.Sexe;
import com.mycompany.myapp.repository.ApprenantRepository;
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

/**
 * Integration tests for the {@link ApprenantResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApprenantResourceIT {

    private static final String DEFAULT_MATRICULE_APP = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE_APP = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final Long DEFAULT_NUM_ID_NATIONAL = 1L;
    private static final Long UPDATED_NUM_ID_NATIONAL = 2L;

    private static final Sexe DEFAULT_SEXE = Sexe.Masclin;
    private static final Sexe UPDATED_SEXE = Sexe.Feminin;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_NAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAISSANCE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LIEU_NAISSANCE = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_NAISSANCE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/apprenants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApprenantRepository apprenantRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApprenantMockMvc;

    private Apprenant apprenant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Apprenant createEntity(EntityManager em) {
        Apprenant apprenant = new Apprenant()
            .matriculeApp(DEFAULT_MATRICULE_APP)
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .numIdNational(DEFAULT_NUM_ID_NATIONAL)
            .sexe(DEFAULT_SEXE)
            .email(DEFAULT_EMAIL)
            .dateNaissance(DEFAULT_DATE_NAISSANCE)
            .lieuNaissance(DEFAULT_LIEU_NAISSANCE)
            .adresse(DEFAULT_ADRESSE);
        return apprenant;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Apprenant createUpdatedEntity(EntityManager em) {
        Apprenant apprenant = new Apprenant()
            .matriculeApp(UPDATED_MATRICULE_APP)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .numIdNational(UPDATED_NUM_ID_NATIONAL)
            .sexe(UPDATED_SEXE)
            .email(UPDATED_EMAIL)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .lieuNaissance(UPDATED_LIEU_NAISSANCE)
            .adresse(UPDATED_ADRESSE);
        return apprenant;
    }

    @BeforeEach
    public void initTest() {
        apprenant = createEntity(em);
    }

    @Test
    @Transactional
    void createApprenant() throws Exception {
        int databaseSizeBeforeCreate = apprenantRepository.findAll().size();
        // Create the Apprenant
        restApprenantMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apprenant))
            )
            .andExpect(status().isCreated());

        // Validate the Apprenant in the database
        List<Apprenant> apprenantList = apprenantRepository.findAll();
        assertThat(apprenantList).hasSize(databaseSizeBeforeCreate + 1);
        Apprenant testApprenant = apprenantList.get(apprenantList.size() - 1);
        assertThat(testApprenant.getMatriculeApp()).isEqualTo(DEFAULT_MATRICULE_APP);
        assertThat(testApprenant.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testApprenant.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testApprenant.getNumIdNational()).isEqualTo(DEFAULT_NUM_ID_NATIONAL);
        assertThat(testApprenant.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testApprenant.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testApprenant.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testApprenant.getLieuNaissance()).isEqualTo(DEFAULT_LIEU_NAISSANCE);
        assertThat(testApprenant.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
    }

    @Test
    @Transactional
    void createApprenantWithExistingId() throws Exception {
        // Create the Apprenant with an existing ID
        apprenant.setId(1L);

        int databaseSizeBeforeCreate = apprenantRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApprenantMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apprenant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apprenant in the database
        List<Apprenant> apprenantList = apprenantRepository.findAll();
        assertThat(apprenantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = apprenantRepository.findAll().size();
        // set the field null
        apprenant.setNom(null);

        // Create the Apprenant, which fails.

        restApprenantMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apprenant))
            )
            .andExpect(status().isBadRequest());

        List<Apprenant> apprenantList = apprenantRepository.findAll();
        assertThat(apprenantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = apprenantRepository.findAll().size();
        // set the field null
        apprenant.setPrenom(null);

        // Create the Apprenant, which fails.

        restApprenantMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apprenant))
            )
            .andExpect(status().isBadRequest());

        List<Apprenant> apprenantList = apprenantRepository.findAll();
        assertThat(apprenantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumIdNationalIsRequired() throws Exception {
        int databaseSizeBeforeTest = apprenantRepository.findAll().size();
        // set the field null
        apprenant.setNumIdNational(null);

        // Create the Apprenant, which fails.

        restApprenantMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apprenant))
            )
            .andExpect(status().isBadRequest());

        List<Apprenant> apprenantList = apprenantRepository.findAll();
        assertThat(apprenantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSexeIsRequired() throws Exception {
        int databaseSizeBeforeTest = apprenantRepository.findAll().size();
        // set the field null
        apprenant.setSexe(null);

        // Create the Apprenant, which fails.

        restApprenantMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apprenant))
            )
            .andExpect(status().isBadRequest());

        List<Apprenant> apprenantList = apprenantRepository.findAll();
        assertThat(apprenantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = apprenantRepository.findAll().size();
        // set the field null
        apprenant.setEmail(null);

        // Create the Apprenant, which fails.

        restApprenantMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apprenant))
            )
            .andExpect(status().isBadRequest());

        List<Apprenant> apprenantList = apprenantRepository.findAll();
        assertThat(apprenantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateNaissanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = apprenantRepository.findAll().size();
        // set the field null
        apprenant.setDateNaissance(null);

        // Create the Apprenant, which fails.

        restApprenantMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apprenant))
            )
            .andExpect(status().isBadRequest());

        List<Apprenant> apprenantList = apprenantRepository.findAll();
        assertThat(apprenantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLieuNaissanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = apprenantRepository.findAll().size();
        // set the field null
        apprenant.setLieuNaissance(null);

        // Create the Apprenant, which fails.

        restApprenantMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apprenant))
            )
            .andExpect(status().isBadRequest());

        List<Apprenant> apprenantList = apprenantRepository.findAll();
        assertThat(apprenantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAdresseIsRequired() throws Exception {
        int databaseSizeBeforeTest = apprenantRepository.findAll().size();
        // set the field null
        apprenant.setAdresse(null);

        // Create the Apprenant, which fails.

        restApprenantMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apprenant))
            )
            .andExpect(status().isBadRequest());

        List<Apprenant> apprenantList = apprenantRepository.findAll();
        assertThat(apprenantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllApprenants() throws Exception {
        // Initialize the database
        apprenantRepository.saveAndFlush(apprenant);

        // Get all the apprenantList
        restApprenantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apprenant.getId().intValue())))
            .andExpect(jsonPath("$.[*].matriculeApp").value(hasItem(DEFAULT_MATRICULE_APP)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].numIdNational").value(hasItem(DEFAULT_NUM_ID_NATIONAL.intValue())))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].lieuNaissance").value(hasItem(DEFAULT_LIEU_NAISSANCE)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)));
    }

    @Test
    @Transactional
    void getApprenant() throws Exception {
        // Initialize the database
        apprenantRepository.saveAndFlush(apprenant);

        // Get the apprenant
        restApprenantMockMvc
            .perform(get(ENTITY_API_URL_ID, apprenant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(apprenant.getId().intValue()))
            .andExpect(jsonPath("$.matriculeApp").value(DEFAULT_MATRICULE_APP))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.numIdNational").value(DEFAULT_NUM_ID_NATIONAL.intValue()))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.lieuNaissance").value(DEFAULT_LIEU_NAISSANCE))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE));
    }

    @Test
    @Transactional
    void getNonExistingApprenant() throws Exception {
        // Get the apprenant
        restApprenantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewApprenant() throws Exception {
        // Initialize the database
        apprenantRepository.saveAndFlush(apprenant);

        int databaseSizeBeforeUpdate = apprenantRepository.findAll().size();

        // Update the apprenant
        Apprenant updatedApprenant = apprenantRepository.findById(apprenant.getId()).get();
        // Disconnect from session so that the updates on updatedApprenant are not directly saved in db
        em.detach(updatedApprenant);
        updatedApprenant
            .matriculeApp(UPDATED_MATRICULE_APP)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .numIdNational(UPDATED_NUM_ID_NATIONAL)
            .sexe(UPDATED_SEXE)
            .email(UPDATED_EMAIL)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .lieuNaissance(UPDATED_LIEU_NAISSANCE)
            .adresse(UPDATED_ADRESSE);

        restApprenantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedApprenant.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedApprenant))
            )
            .andExpect(status().isOk());

        // Validate the Apprenant in the database
        List<Apprenant> apprenantList = apprenantRepository.findAll();
        assertThat(apprenantList).hasSize(databaseSizeBeforeUpdate);
        Apprenant testApprenant = apprenantList.get(apprenantList.size() - 1);
        assertThat(testApprenant.getMatriculeApp()).isEqualTo(UPDATED_MATRICULE_APP);
        assertThat(testApprenant.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testApprenant.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testApprenant.getNumIdNational()).isEqualTo(UPDATED_NUM_ID_NATIONAL);
        assertThat(testApprenant.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testApprenant.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testApprenant.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testApprenant.getLieuNaissance()).isEqualTo(UPDATED_LIEU_NAISSANCE);
        assertThat(testApprenant.getAdresse()).isEqualTo(UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    void putNonExistingApprenant() throws Exception {
        int databaseSizeBeforeUpdate = apprenantRepository.findAll().size();
        apprenant.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApprenantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, apprenant.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apprenant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apprenant in the database
        List<Apprenant> apprenantList = apprenantRepository.findAll();
        assertThat(apprenantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApprenant() throws Exception {
        int databaseSizeBeforeUpdate = apprenantRepository.findAll().size();
        apprenant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApprenantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apprenant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apprenant in the database
        List<Apprenant> apprenantList = apprenantRepository.findAll();
        assertThat(apprenantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApprenant() throws Exception {
        int databaseSizeBeforeUpdate = apprenantRepository.findAll().size();
        apprenant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApprenantMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(apprenant))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Apprenant in the database
        List<Apprenant> apprenantList = apprenantRepository.findAll();
        assertThat(apprenantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApprenantWithPatch() throws Exception {
        // Initialize the database
        apprenantRepository.saveAndFlush(apprenant);

        int databaseSizeBeforeUpdate = apprenantRepository.findAll().size();

        // Update the apprenant using partial update
        Apprenant partialUpdatedApprenant = new Apprenant();
        partialUpdatedApprenant.setId(apprenant.getId());

        partialUpdatedApprenant
            .nom(UPDATED_NOM)
            .numIdNational(UPDATED_NUM_ID_NATIONAL)
            .sexe(UPDATED_SEXE)
            .email(UPDATED_EMAIL)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .lieuNaissance(UPDATED_LIEU_NAISSANCE);

        restApprenantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApprenant.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApprenant))
            )
            .andExpect(status().isOk());

        // Validate the Apprenant in the database
        List<Apprenant> apprenantList = apprenantRepository.findAll();
        assertThat(apprenantList).hasSize(databaseSizeBeforeUpdate);
        Apprenant testApprenant = apprenantList.get(apprenantList.size() - 1);
        assertThat(testApprenant.getMatriculeApp()).isEqualTo(DEFAULT_MATRICULE_APP);
        assertThat(testApprenant.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testApprenant.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testApprenant.getNumIdNational()).isEqualTo(UPDATED_NUM_ID_NATIONAL);
        assertThat(testApprenant.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testApprenant.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testApprenant.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testApprenant.getLieuNaissance()).isEqualTo(UPDATED_LIEU_NAISSANCE);
        assertThat(testApprenant.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
    }

    @Test
    @Transactional
    void fullUpdateApprenantWithPatch() throws Exception {
        // Initialize the database
        apprenantRepository.saveAndFlush(apprenant);

        int databaseSizeBeforeUpdate = apprenantRepository.findAll().size();

        // Update the apprenant using partial update
        Apprenant partialUpdatedApprenant = new Apprenant();
        partialUpdatedApprenant.setId(apprenant.getId());

        partialUpdatedApprenant
            .matriculeApp(UPDATED_MATRICULE_APP)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .numIdNational(UPDATED_NUM_ID_NATIONAL)
            .sexe(UPDATED_SEXE)
            .email(UPDATED_EMAIL)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .lieuNaissance(UPDATED_LIEU_NAISSANCE)
            .adresse(UPDATED_ADRESSE);

        restApprenantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApprenant.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApprenant))
            )
            .andExpect(status().isOk());

        // Validate the Apprenant in the database
        List<Apprenant> apprenantList = apprenantRepository.findAll();
        assertThat(apprenantList).hasSize(databaseSizeBeforeUpdate);
        Apprenant testApprenant = apprenantList.get(apprenantList.size() - 1);
        assertThat(testApprenant.getMatriculeApp()).isEqualTo(UPDATED_MATRICULE_APP);
        assertThat(testApprenant.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testApprenant.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testApprenant.getNumIdNational()).isEqualTo(UPDATED_NUM_ID_NATIONAL);
        assertThat(testApprenant.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testApprenant.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testApprenant.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testApprenant.getLieuNaissance()).isEqualTo(UPDATED_LIEU_NAISSANCE);
        assertThat(testApprenant.getAdresse()).isEqualTo(UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    void patchNonExistingApprenant() throws Exception {
        int databaseSizeBeforeUpdate = apprenantRepository.findAll().size();
        apprenant.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApprenantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, apprenant.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(apprenant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apprenant in the database
        List<Apprenant> apprenantList = apprenantRepository.findAll();
        assertThat(apprenantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApprenant() throws Exception {
        int databaseSizeBeforeUpdate = apprenantRepository.findAll().size();
        apprenant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApprenantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(apprenant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apprenant in the database
        List<Apprenant> apprenantList = apprenantRepository.findAll();
        assertThat(apprenantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApprenant() throws Exception {
        int databaseSizeBeforeUpdate = apprenantRepository.findAll().size();
        apprenant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApprenantMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(apprenant))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Apprenant in the database
        List<Apprenant> apprenantList = apprenantRepository.findAll();
        assertThat(apprenantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApprenant() throws Exception {
        // Initialize the database
        apprenantRepository.saveAndFlush(apprenant);

        int databaseSizeBeforeDelete = apprenantRepository.findAll().size();

        // Delete the apprenant
        restApprenantMockMvc
            .perform(delete(ENTITY_API_URL_ID, apprenant.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Apprenant> apprenantList = apprenantRepository.findAll();
        assertThat(apprenantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
