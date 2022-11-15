package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.DemandeMatriculeApp;
import com.mycompany.myapp.domain.enumeration.Sexe;
import com.mycompany.myapp.repository.DemandeMatriculeAppRepository;
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
 * Integration tests for the {@link DemandeMatriculeAppResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DemandeMatriculeAppResourceIT {

    private static final String DEFAULT_NUMERO_DEMANDE_APP = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_DEMANDE_APP = "BBBBBBBBBB";

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

    private static final String DEFAULT_ETAB_FREQUENTE = "AAAAAAAAAA";
    private static final String UPDATED_ETAB_FREQUENTE = "BBBBBBBBBB";

    private static final String DEFAULT_MATRICULE_ETAB = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE_ETAB = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/demande-matricule-apps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DemandeMatriculeAppRepository demandeMatriculeAppRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDemandeMatriculeAppMockMvc;

    private DemandeMatriculeApp demandeMatriculeApp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandeMatriculeApp createEntity(EntityManager em) {
        DemandeMatriculeApp demandeMatriculeApp = new DemandeMatriculeApp()
            .numeroDemandeApp(DEFAULT_NUMERO_DEMANDE_APP)
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .numIdNational(DEFAULT_NUM_ID_NATIONAL)
            .sexe(DEFAULT_SEXE)
            .email(DEFAULT_EMAIL)
            .dateNaissance(DEFAULT_DATE_NAISSANCE)
            .lieuNaissance(DEFAULT_LIEU_NAISSANCE)
            .adresse(DEFAULT_ADRESSE)
            .etabFrequente(DEFAULT_ETAB_FREQUENTE)
            .matriculeEtab(DEFAULT_MATRICULE_ETAB);
        return demandeMatriculeApp;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandeMatriculeApp createUpdatedEntity(EntityManager em) {
        DemandeMatriculeApp demandeMatriculeApp = new DemandeMatriculeApp()
            .numeroDemandeApp(UPDATED_NUMERO_DEMANDE_APP)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .numIdNational(UPDATED_NUM_ID_NATIONAL)
            .sexe(UPDATED_SEXE)
            .email(UPDATED_EMAIL)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .lieuNaissance(UPDATED_LIEU_NAISSANCE)
            .adresse(UPDATED_ADRESSE)
            .etabFrequente(UPDATED_ETAB_FREQUENTE)
            .matriculeEtab(UPDATED_MATRICULE_ETAB);
        return demandeMatriculeApp;
    }

    @BeforeEach
    public void initTest() {
        demandeMatriculeApp = createEntity(em);
    }

    @Test
    @Transactional
    void createDemandeMatriculeApp() throws Exception {
        int databaseSizeBeforeCreate = demandeMatriculeAppRepository.findAll().size();
        // Create the DemandeMatriculeApp
        restDemandeMatriculeAppMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatriculeApp))
            )
            .andExpect(status().isCreated());

        // Validate the DemandeMatriculeApp in the database
        List<DemandeMatriculeApp> demandeMatriculeAppList = demandeMatriculeAppRepository.findAll();
        assertThat(demandeMatriculeAppList).hasSize(databaseSizeBeforeCreate + 1);
        DemandeMatriculeApp testDemandeMatriculeApp = demandeMatriculeAppList.get(demandeMatriculeAppList.size() - 1);
        assertThat(testDemandeMatriculeApp.getNumeroDemandeApp()).isEqualTo(DEFAULT_NUMERO_DEMANDE_APP);
        assertThat(testDemandeMatriculeApp.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testDemandeMatriculeApp.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testDemandeMatriculeApp.getNumIdNational()).isEqualTo(DEFAULT_NUM_ID_NATIONAL);
        assertThat(testDemandeMatriculeApp.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testDemandeMatriculeApp.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDemandeMatriculeApp.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testDemandeMatriculeApp.getLieuNaissance()).isEqualTo(DEFAULT_LIEU_NAISSANCE);
        assertThat(testDemandeMatriculeApp.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testDemandeMatriculeApp.getEtabFrequente()).isEqualTo(DEFAULT_ETAB_FREQUENTE);
        assertThat(testDemandeMatriculeApp.getMatriculeEtab()).isEqualTo(DEFAULT_MATRICULE_ETAB);
    }

    @Test
    @Transactional
    void createDemandeMatriculeAppWithExistingId() throws Exception {
        // Create the DemandeMatriculeApp with an existing ID
        demandeMatriculeApp.setId(1L);

        int databaseSizeBeforeCreate = demandeMatriculeAppRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemandeMatriculeAppMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatriculeApp))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeMatriculeApp in the database
        List<DemandeMatriculeApp> demandeMatriculeAppList = demandeMatriculeAppRepository.findAll();
        assertThat(demandeMatriculeAppList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeMatriculeAppRepository.findAll().size();
        // set the field null
        demandeMatriculeApp.setNom(null);

        // Create the DemandeMatriculeApp, which fails.

        restDemandeMatriculeAppMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatriculeApp))
            )
            .andExpect(status().isBadRequest());

        List<DemandeMatriculeApp> demandeMatriculeAppList = demandeMatriculeAppRepository.findAll();
        assertThat(demandeMatriculeAppList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeMatriculeAppRepository.findAll().size();
        // set the field null
        demandeMatriculeApp.setPrenom(null);

        // Create the DemandeMatriculeApp, which fails.

        restDemandeMatriculeAppMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatriculeApp))
            )
            .andExpect(status().isBadRequest());

        List<DemandeMatriculeApp> demandeMatriculeAppList = demandeMatriculeAppRepository.findAll();
        assertThat(demandeMatriculeAppList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumIdNationalIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeMatriculeAppRepository.findAll().size();
        // set the field null
        demandeMatriculeApp.setNumIdNational(null);

        // Create the DemandeMatriculeApp, which fails.

        restDemandeMatriculeAppMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatriculeApp))
            )
            .andExpect(status().isBadRequest());

        List<DemandeMatriculeApp> demandeMatriculeAppList = demandeMatriculeAppRepository.findAll();
        assertThat(demandeMatriculeAppList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSexeIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeMatriculeAppRepository.findAll().size();
        // set the field null
        demandeMatriculeApp.setSexe(null);

        // Create the DemandeMatriculeApp, which fails.

        restDemandeMatriculeAppMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatriculeApp))
            )
            .andExpect(status().isBadRequest());

        List<DemandeMatriculeApp> demandeMatriculeAppList = demandeMatriculeAppRepository.findAll();
        assertThat(demandeMatriculeAppList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeMatriculeAppRepository.findAll().size();
        // set the field null
        demandeMatriculeApp.setEmail(null);

        // Create the DemandeMatriculeApp, which fails.

        restDemandeMatriculeAppMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatriculeApp))
            )
            .andExpect(status().isBadRequest());

        List<DemandeMatriculeApp> demandeMatriculeAppList = demandeMatriculeAppRepository.findAll();
        assertThat(demandeMatriculeAppList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateNaissanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeMatriculeAppRepository.findAll().size();
        // set the field null
        demandeMatriculeApp.setDateNaissance(null);

        // Create the DemandeMatriculeApp, which fails.

        restDemandeMatriculeAppMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatriculeApp))
            )
            .andExpect(status().isBadRequest());

        List<DemandeMatriculeApp> demandeMatriculeAppList = demandeMatriculeAppRepository.findAll();
        assertThat(demandeMatriculeAppList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLieuNaissanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeMatriculeAppRepository.findAll().size();
        // set the field null
        demandeMatriculeApp.setLieuNaissance(null);

        // Create the DemandeMatriculeApp, which fails.

        restDemandeMatriculeAppMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatriculeApp))
            )
            .andExpect(status().isBadRequest());

        List<DemandeMatriculeApp> demandeMatriculeAppList = demandeMatriculeAppRepository.findAll();
        assertThat(demandeMatriculeAppList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAdresseIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeMatriculeAppRepository.findAll().size();
        // set the field null
        demandeMatriculeApp.setAdresse(null);

        // Create the DemandeMatriculeApp, which fails.

        restDemandeMatriculeAppMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatriculeApp))
            )
            .andExpect(status().isBadRequest());

        List<DemandeMatriculeApp> demandeMatriculeAppList = demandeMatriculeAppRepository.findAll();
        assertThat(demandeMatriculeAppList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEtabFrequenteIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeMatriculeAppRepository.findAll().size();
        // set the field null
        demandeMatriculeApp.setEtabFrequente(null);

        // Create the DemandeMatriculeApp, which fails.

        restDemandeMatriculeAppMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatriculeApp))
            )
            .andExpect(status().isBadRequest());

        List<DemandeMatriculeApp> demandeMatriculeAppList = demandeMatriculeAppRepository.findAll();
        assertThat(demandeMatriculeAppList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMatriculeEtabIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeMatriculeAppRepository.findAll().size();
        // set the field null
        demandeMatriculeApp.setMatriculeEtab(null);

        // Create the DemandeMatriculeApp, which fails.

        restDemandeMatriculeAppMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatriculeApp))
            )
            .andExpect(status().isBadRequest());

        List<DemandeMatriculeApp> demandeMatriculeAppList = demandeMatriculeAppRepository.findAll();
        assertThat(demandeMatriculeAppList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDemandeMatriculeApps() throws Exception {
        // Initialize the database
        demandeMatriculeAppRepository.saveAndFlush(demandeMatriculeApp);

        // Get all the demandeMatriculeAppList
        restDemandeMatriculeAppMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demandeMatriculeApp.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroDemandeApp").value(hasItem(DEFAULT_NUMERO_DEMANDE_APP)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].numIdNational").value(hasItem(DEFAULT_NUM_ID_NATIONAL.intValue())))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].lieuNaissance").value(hasItem(DEFAULT_LIEU_NAISSANCE)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].etabFrequente").value(hasItem(DEFAULT_ETAB_FREQUENTE)))
            .andExpect(jsonPath("$.[*].matriculeEtab").value(hasItem(DEFAULT_MATRICULE_ETAB)));
    }

    @Test
    @Transactional
    void getDemandeMatriculeApp() throws Exception {
        // Initialize the database
        demandeMatriculeAppRepository.saveAndFlush(demandeMatriculeApp);

        // Get the demandeMatriculeApp
        restDemandeMatriculeAppMockMvc
            .perform(get(ENTITY_API_URL_ID, demandeMatriculeApp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(demandeMatriculeApp.getId().intValue()))
            .andExpect(jsonPath("$.numeroDemandeApp").value(DEFAULT_NUMERO_DEMANDE_APP))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.numIdNational").value(DEFAULT_NUM_ID_NATIONAL.intValue()))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.lieuNaissance").value(DEFAULT_LIEU_NAISSANCE))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.etabFrequente").value(DEFAULT_ETAB_FREQUENTE))
            .andExpect(jsonPath("$.matriculeEtab").value(DEFAULT_MATRICULE_ETAB));
    }

    @Test
    @Transactional
    void getNonExistingDemandeMatriculeApp() throws Exception {
        // Get the demandeMatriculeApp
        restDemandeMatriculeAppMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDemandeMatriculeApp() throws Exception {
        // Initialize the database
        demandeMatriculeAppRepository.saveAndFlush(demandeMatriculeApp);

        int databaseSizeBeforeUpdate = demandeMatriculeAppRepository.findAll().size();

        // Update the demandeMatriculeApp
        DemandeMatriculeApp updatedDemandeMatriculeApp = demandeMatriculeAppRepository.findById(demandeMatriculeApp.getId()).get();
        // Disconnect from session so that the updates on updatedDemandeMatriculeApp are not directly saved in db
        em.detach(updatedDemandeMatriculeApp);
        updatedDemandeMatriculeApp
            .numeroDemandeApp(UPDATED_NUMERO_DEMANDE_APP)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .numIdNational(UPDATED_NUM_ID_NATIONAL)
            .sexe(UPDATED_SEXE)
            .email(UPDATED_EMAIL)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .lieuNaissance(UPDATED_LIEU_NAISSANCE)
            .adresse(UPDATED_ADRESSE)
            .etabFrequente(UPDATED_ETAB_FREQUENTE)
            .matriculeEtab(UPDATED_MATRICULE_ETAB);

        restDemandeMatriculeAppMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDemandeMatriculeApp.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDemandeMatriculeApp))
            )
            .andExpect(status().isOk());

        // Validate the DemandeMatriculeApp in the database
        List<DemandeMatriculeApp> demandeMatriculeAppList = demandeMatriculeAppRepository.findAll();
        assertThat(demandeMatriculeAppList).hasSize(databaseSizeBeforeUpdate);
        DemandeMatriculeApp testDemandeMatriculeApp = demandeMatriculeAppList.get(demandeMatriculeAppList.size() - 1);
        assertThat(testDemandeMatriculeApp.getNumeroDemandeApp()).isEqualTo(UPDATED_NUMERO_DEMANDE_APP);
        assertThat(testDemandeMatriculeApp.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testDemandeMatriculeApp.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testDemandeMatriculeApp.getNumIdNational()).isEqualTo(UPDATED_NUM_ID_NATIONAL);
        assertThat(testDemandeMatriculeApp.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testDemandeMatriculeApp.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDemandeMatriculeApp.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testDemandeMatriculeApp.getLieuNaissance()).isEqualTo(UPDATED_LIEU_NAISSANCE);
        assertThat(testDemandeMatriculeApp.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testDemandeMatriculeApp.getEtabFrequente()).isEqualTo(UPDATED_ETAB_FREQUENTE);
        assertThat(testDemandeMatriculeApp.getMatriculeEtab()).isEqualTo(UPDATED_MATRICULE_ETAB);
    }

    @Test
    @Transactional
    void putNonExistingDemandeMatriculeApp() throws Exception {
        int databaseSizeBeforeUpdate = demandeMatriculeAppRepository.findAll().size();
        demandeMatriculeApp.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandeMatriculeAppMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demandeMatriculeApp.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatriculeApp))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeMatriculeApp in the database
        List<DemandeMatriculeApp> demandeMatriculeAppList = demandeMatriculeAppRepository.findAll();
        assertThat(demandeMatriculeAppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDemandeMatriculeApp() throws Exception {
        int databaseSizeBeforeUpdate = demandeMatriculeAppRepository.findAll().size();
        demandeMatriculeApp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeMatriculeAppMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatriculeApp))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeMatriculeApp in the database
        List<DemandeMatriculeApp> demandeMatriculeAppList = demandeMatriculeAppRepository.findAll();
        assertThat(demandeMatriculeAppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDemandeMatriculeApp() throws Exception {
        int databaseSizeBeforeUpdate = demandeMatriculeAppRepository.findAll().size();
        demandeMatriculeApp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeMatriculeAppMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatriculeApp))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandeMatriculeApp in the database
        List<DemandeMatriculeApp> demandeMatriculeAppList = demandeMatriculeAppRepository.findAll();
        assertThat(demandeMatriculeAppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDemandeMatriculeAppWithPatch() throws Exception {
        // Initialize the database
        demandeMatriculeAppRepository.saveAndFlush(demandeMatriculeApp);

        int databaseSizeBeforeUpdate = demandeMatriculeAppRepository.findAll().size();

        // Update the demandeMatriculeApp using partial update
        DemandeMatriculeApp partialUpdatedDemandeMatriculeApp = new DemandeMatriculeApp();
        partialUpdatedDemandeMatriculeApp.setId(demandeMatriculeApp.getId());

        partialUpdatedDemandeMatriculeApp
            .numeroDemandeApp(UPDATED_NUMERO_DEMANDE_APP)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .email(UPDATED_EMAIL)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .lieuNaissance(UPDATED_LIEU_NAISSANCE)
            .adresse(UPDATED_ADRESSE)
            .matriculeEtab(UPDATED_MATRICULE_ETAB);

        restDemandeMatriculeAppMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandeMatriculeApp.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandeMatriculeApp))
            )
            .andExpect(status().isOk());

        // Validate the DemandeMatriculeApp in the database
        List<DemandeMatriculeApp> demandeMatriculeAppList = demandeMatriculeAppRepository.findAll();
        assertThat(demandeMatriculeAppList).hasSize(databaseSizeBeforeUpdate);
        DemandeMatriculeApp testDemandeMatriculeApp = demandeMatriculeAppList.get(demandeMatriculeAppList.size() - 1);
        assertThat(testDemandeMatriculeApp.getNumeroDemandeApp()).isEqualTo(UPDATED_NUMERO_DEMANDE_APP);
        assertThat(testDemandeMatriculeApp.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testDemandeMatriculeApp.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testDemandeMatriculeApp.getNumIdNational()).isEqualTo(DEFAULT_NUM_ID_NATIONAL);
        assertThat(testDemandeMatriculeApp.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testDemandeMatriculeApp.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDemandeMatriculeApp.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testDemandeMatriculeApp.getLieuNaissance()).isEqualTo(UPDATED_LIEU_NAISSANCE);
        assertThat(testDemandeMatriculeApp.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testDemandeMatriculeApp.getEtabFrequente()).isEqualTo(DEFAULT_ETAB_FREQUENTE);
        assertThat(testDemandeMatriculeApp.getMatriculeEtab()).isEqualTo(UPDATED_MATRICULE_ETAB);
    }

    @Test
    @Transactional
    void fullUpdateDemandeMatriculeAppWithPatch() throws Exception {
        // Initialize the database
        demandeMatriculeAppRepository.saveAndFlush(demandeMatriculeApp);

        int databaseSizeBeforeUpdate = demandeMatriculeAppRepository.findAll().size();

        // Update the demandeMatriculeApp using partial update
        DemandeMatriculeApp partialUpdatedDemandeMatriculeApp = new DemandeMatriculeApp();
        partialUpdatedDemandeMatriculeApp.setId(demandeMatriculeApp.getId());

        partialUpdatedDemandeMatriculeApp
            .numeroDemandeApp(UPDATED_NUMERO_DEMANDE_APP)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .numIdNational(UPDATED_NUM_ID_NATIONAL)
            .sexe(UPDATED_SEXE)
            .email(UPDATED_EMAIL)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .lieuNaissance(UPDATED_LIEU_NAISSANCE)
            .adresse(UPDATED_ADRESSE)
            .etabFrequente(UPDATED_ETAB_FREQUENTE)
            .matriculeEtab(UPDATED_MATRICULE_ETAB);

        restDemandeMatriculeAppMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandeMatriculeApp.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandeMatriculeApp))
            )
            .andExpect(status().isOk());

        // Validate the DemandeMatriculeApp in the database
        List<DemandeMatriculeApp> demandeMatriculeAppList = demandeMatriculeAppRepository.findAll();
        assertThat(demandeMatriculeAppList).hasSize(databaseSizeBeforeUpdate);
        DemandeMatriculeApp testDemandeMatriculeApp = demandeMatriculeAppList.get(demandeMatriculeAppList.size() - 1);
        assertThat(testDemandeMatriculeApp.getNumeroDemandeApp()).isEqualTo(UPDATED_NUMERO_DEMANDE_APP);
        assertThat(testDemandeMatriculeApp.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testDemandeMatriculeApp.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testDemandeMatriculeApp.getNumIdNational()).isEqualTo(UPDATED_NUM_ID_NATIONAL);
        assertThat(testDemandeMatriculeApp.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testDemandeMatriculeApp.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDemandeMatriculeApp.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testDemandeMatriculeApp.getLieuNaissance()).isEqualTo(UPDATED_LIEU_NAISSANCE);
        assertThat(testDemandeMatriculeApp.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testDemandeMatriculeApp.getEtabFrequente()).isEqualTo(UPDATED_ETAB_FREQUENTE);
        assertThat(testDemandeMatriculeApp.getMatriculeEtab()).isEqualTo(UPDATED_MATRICULE_ETAB);
    }

    @Test
    @Transactional
    void patchNonExistingDemandeMatriculeApp() throws Exception {
        int databaseSizeBeforeUpdate = demandeMatriculeAppRepository.findAll().size();
        demandeMatriculeApp.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandeMatriculeAppMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, demandeMatriculeApp.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatriculeApp))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeMatriculeApp in the database
        List<DemandeMatriculeApp> demandeMatriculeAppList = demandeMatriculeAppRepository.findAll();
        assertThat(demandeMatriculeAppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDemandeMatriculeApp() throws Exception {
        int databaseSizeBeforeUpdate = demandeMatriculeAppRepository.findAll().size();
        demandeMatriculeApp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeMatriculeAppMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatriculeApp))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeMatriculeApp in the database
        List<DemandeMatriculeApp> demandeMatriculeAppList = demandeMatriculeAppRepository.findAll();
        assertThat(demandeMatriculeAppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDemandeMatriculeApp() throws Exception {
        int databaseSizeBeforeUpdate = demandeMatriculeAppRepository.findAll().size();
        demandeMatriculeApp.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeMatriculeAppMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatriculeApp))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandeMatriculeApp in the database
        List<DemandeMatriculeApp> demandeMatriculeAppList = demandeMatriculeAppRepository.findAll();
        assertThat(demandeMatriculeAppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDemandeMatriculeApp() throws Exception {
        // Initialize the database
        demandeMatriculeAppRepository.saveAndFlush(demandeMatriculeApp);

        int databaseSizeBeforeDelete = demandeMatriculeAppRepository.findAll().size();

        // Delete the demandeMatriculeApp
        restDemandeMatriculeAppMockMvc
            .perform(delete(ENTITY_API_URL_ID, demandeMatriculeApp.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DemandeMatriculeApp> demandeMatriculeAppList = demandeMatriculeAppRepository.findAll();
        assertThat(demandeMatriculeAppList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
