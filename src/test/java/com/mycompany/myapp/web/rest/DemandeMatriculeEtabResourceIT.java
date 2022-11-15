package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.DemandeMatriculeEtab;
import com.mycompany.myapp.domain.enumeration.NomDep;
import com.mycompany.myapp.domain.enumeration.NomReg;
import com.mycompany.myapp.domain.enumeration.StatutEtab;
import com.mycompany.myapp.domain.enumeration.TypeEtab;
import com.mycompany.myapp.domain.enumeration.TypeInspection;
import com.mycompany.myapp.repository.DemandeMatriculeEtabRepository;
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
 * Integration tests for the {@link DemandeMatriculeEtabResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DemandeMatriculeEtabResourceIT {

    private static final String DEFAULT_NUMERO_DEMANDE_ETAB = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_DEMANDE_ETAB = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_ETAB = "AAAAAAAAAA";
    private static final String UPDATED_NOM_ETAB = "BBBBBBBBBB";

    private static final TypeEtab DEFAULT_TYPE_ETAB = TypeEtab.LyceeTechnique;
    private static final TypeEtab UPDATED_TYPE_ETAB = TypeEtab.CFP;

    private static final StatutEtab DEFAULT_STATUT = StatutEtab.Prive;
    private static final StatutEtab UPDATED_STATUT = StatutEtab.Public;

    private static final String DEFAULT_EMAIL_ETAB = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ETAB = "BBBBBBBBBB";

    private static final NomReg DEFAULT_REGION = NomReg.Dakar;
    private static final NomReg UPDATED_REGION = NomReg.Thies;

    private static final NomDep DEFAULT_DEPARTEMENT = NomDep.Dakar;
    private static final NomDep UPDATED_DEPARTEMENT = NomDep.Pikine;

    private static final String DEFAULT_COMMUNE = "AAAAAAAAAA";
    private static final String UPDATED_COMMUNE = "BBBBBBBBBB";

    private static final TypeInspection DEFAULT_TYPE_INSP = TypeInspection.IA;
    private static final TypeInspection UPDATED_TYPE_INSP = TypeInspection.IEF;

    private static final String ENTITY_API_URL = "/api/demande-matricule-etabs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DemandeMatriculeEtabRepository demandeMatriculeEtabRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDemandeMatriculeEtabMockMvc;

    private DemandeMatriculeEtab demandeMatriculeEtab;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandeMatriculeEtab createEntity(EntityManager em) {
        DemandeMatriculeEtab demandeMatriculeEtab = new DemandeMatriculeEtab()
            .numeroDemandeEtab(DEFAULT_NUMERO_DEMANDE_ETAB)
            .nomEtab(DEFAULT_NOM_ETAB)
            .typeEtab(DEFAULT_TYPE_ETAB)
            .statut(DEFAULT_STATUT)
            .emailEtab(DEFAULT_EMAIL_ETAB)
            .region(DEFAULT_REGION)
            .departement(DEFAULT_DEPARTEMENT)
            .commune(DEFAULT_COMMUNE)
            .typeInsp(DEFAULT_TYPE_INSP);
        return demandeMatriculeEtab;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandeMatriculeEtab createUpdatedEntity(EntityManager em) {
        DemandeMatriculeEtab demandeMatriculeEtab = new DemandeMatriculeEtab()
            .numeroDemandeEtab(UPDATED_NUMERO_DEMANDE_ETAB)
            .nomEtab(UPDATED_NOM_ETAB)
            .typeEtab(UPDATED_TYPE_ETAB)
            .statut(UPDATED_STATUT)
            .emailEtab(UPDATED_EMAIL_ETAB)
            .region(UPDATED_REGION)
            .departement(UPDATED_DEPARTEMENT)
            .commune(UPDATED_COMMUNE)
            .typeInsp(UPDATED_TYPE_INSP);
        return demandeMatriculeEtab;
    }

    @BeforeEach
    public void initTest() {
        demandeMatriculeEtab = createEntity(em);
    }

    @Test
    @Transactional
    void createDemandeMatriculeEtab() throws Exception {
        int databaseSizeBeforeCreate = demandeMatriculeEtabRepository.findAll().size();
        // Create the DemandeMatriculeEtab
        restDemandeMatriculeEtabMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatriculeEtab))
            )
            .andExpect(status().isCreated());

        // Validate the DemandeMatriculeEtab in the database
        List<DemandeMatriculeEtab> demandeMatriculeEtabList = demandeMatriculeEtabRepository.findAll();
        assertThat(demandeMatriculeEtabList).hasSize(databaseSizeBeforeCreate + 1);
        DemandeMatriculeEtab testDemandeMatriculeEtab = demandeMatriculeEtabList.get(demandeMatriculeEtabList.size() - 1);
        assertThat(testDemandeMatriculeEtab.getNumeroDemandeEtab()).isEqualTo(DEFAULT_NUMERO_DEMANDE_ETAB);
        assertThat(testDemandeMatriculeEtab.getNomEtab()).isEqualTo(DEFAULT_NOM_ETAB);
        assertThat(testDemandeMatriculeEtab.getTypeEtab()).isEqualTo(DEFAULT_TYPE_ETAB);
        assertThat(testDemandeMatriculeEtab.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testDemandeMatriculeEtab.getEmailEtab()).isEqualTo(DEFAULT_EMAIL_ETAB);
        assertThat(testDemandeMatriculeEtab.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testDemandeMatriculeEtab.getDepartement()).isEqualTo(DEFAULT_DEPARTEMENT);
        assertThat(testDemandeMatriculeEtab.getCommune()).isEqualTo(DEFAULT_COMMUNE);
        assertThat(testDemandeMatriculeEtab.getTypeInsp()).isEqualTo(DEFAULT_TYPE_INSP);
    }

    @Test
    @Transactional
    void createDemandeMatriculeEtabWithExistingId() throws Exception {
        // Create the DemandeMatriculeEtab with an existing ID
        demandeMatriculeEtab.setId(1L);

        int databaseSizeBeforeCreate = demandeMatriculeEtabRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemandeMatriculeEtabMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatriculeEtab))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeMatriculeEtab in the database
        List<DemandeMatriculeEtab> demandeMatriculeEtabList = demandeMatriculeEtabRepository.findAll();
        assertThat(demandeMatriculeEtabList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomEtabIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeMatriculeEtabRepository.findAll().size();
        // set the field null
        demandeMatriculeEtab.setNomEtab(null);

        // Create the DemandeMatriculeEtab, which fails.

        restDemandeMatriculeEtabMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatriculeEtab))
            )
            .andExpect(status().isBadRequest());

        List<DemandeMatriculeEtab> demandeMatriculeEtabList = demandeMatriculeEtabRepository.findAll();
        assertThat(demandeMatriculeEtabList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeEtabIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeMatriculeEtabRepository.findAll().size();
        // set the field null
        demandeMatriculeEtab.setTypeEtab(null);

        // Create the DemandeMatriculeEtab, which fails.

        restDemandeMatriculeEtabMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatriculeEtab))
            )
            .andExpect(status().isBadRequest());

        List<DemandeMatriculeEtab> demandeMatriculeEtabList = demandeMatriculeEtabRepository.findAll();
        assertThat(demandeMatriculeEtabList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatutIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeMatriculeEtabRepository.findAll().size();
        // set the field null
        demandeMatriculeEtab.setStatut(null);

        // Create the DemandeMatriculeEtab, which fails.

        restDemandeMatriculeEtabMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatriculeEtab))
            )
            .andExpect(status().isBadRequest());

        List<DemandeMatriculeEtab> demandeMatriculeEtabList = demandeMatriculeEtabRepository.findAll();
        assertThat(demandeMatriculeEtabList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailEtabIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeMatriculeEtabRepository.findAll().size();
        // set the field null
        demandeMatriculeEtab.setEmailEtab(null);

        // Create the DemandeMatriculeEtab, which fails.

        restDemandeMatriculeEtabMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatriculeEtab))
            )
            .andExpect(status().isBadRequest());

        List<DemandeMatriculeEtab> demandeMatriculeEtabList = demandeMatriculeEtabRepository.findAll();
        assertThat(demandeMatriculeEtabList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRegionIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeMatriculeEtabRepository.findAll().size();
        // set the field null
        demandeMatriculeEtab.setRegion(null);

        // Create the DemandeMatriculeEtab, which fails.

        restDemandeMatriculeEtabMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatriculeEtab))
            )
            .andExpect(status().isBadRequest());

        List<DemandeMatriculeEtab> demandeMatriculeEtabList = demandeMatriculeEtabRepository.findAll();
        assertThat(demandeMatriculeEtabList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDepartementIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeMatriculeEtabRepository.findAll().size();
        // set the field null
        demandeMatriculeEtab.setDepartement(null);

        // Create the DemandeMatriculeEtab, which fails.

        restDemandeMatriculeEtabMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatriculeEtab))
            )
            .andExpect(status().isBadRequest());

        List<DemandeMatriculeEtab> demandeMatriculeEtabList = demandeMatriculeEtabRepository.findAll();
        assertThat(demandeMatriculeEtabList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCommuneIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeMatriculeEtabRepository.findAll().size();
        // set the field null
        demandeMatriculeEtab.setCommune(null);

        // Create the DemandeMatriculeEtab, which fails.

        restDemandeMatriculeEtabMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatriculeEtab))
            )
            .andExpect(status().isBadRequest());

        List<DemandeMatriculeEtab> demandeMatriculeEtabList = demandeMatriculeEtabRepository.findAll();
        assertThat(demandeMatriculeEtabList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeInspIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeMatriculeEtabRepository.findAll().size();
        // set the field null
        demandeMatriculeEtab.setTypeInsp(null);

        // Create the DemandeMatriculeEtab, which fails.

        restDemandeMatriculeEtabMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatriculeEtab))
            )
            .andExpect(status().isBadRequest());

        List<DemandeMatriculeEtab> demandeMatriculeEtabList = demandeMatriculeEtabRepository.findAll();
        assertThat(demandeMatriculeEtabList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDemandeMatriculeEtabs() throws Exception {
        // Initialize the database
        demandeMatriculeEtabRepository.saveAndFlush(demandeMatriculeEtab);

        // Get all the demandeMatriculeEtabList
        restDemandeMatriculeEtabMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demandeMatriculeEtab.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroDemandeEtab").value(hasItem(DEFAULT_NUMERO_DEMANDE_ETAB)))
            .andExpect(jsonPath("$.[*].nomEtab").value(hasItem(DEFAULT_NOM_ETAB)))
            .andExpect(jsonPath("$.[*].typeEtab").value(hasItem(DEFAULT_TYPE_ETAB.toString())))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())))
            .andExpect(jsonPath("$.[*].emailEtab").value(hasItem(DEFAULT_EMAIL_ETAB)))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
            .andExpect(jsonPath("$.[*].departement").value(hasItem(DEFAULT_DEPARTEMENT.toString())))
            .andExpect(jsonPath("$.[*].commune").value(hasItem(DEFAULT_COMMUNE)))
            .andExpect(jsonPath("$.[*].typeInsp").value(hasItem(DEFAULT_TYPE_INSP.toString())));
    }

    @Test
    @Transactional
    void getDemandeMatriculeEtab() throws Exception {
        // Initialize the database
        demandeMatriculeEtabRepository.saveAndFlush(demandeMatriculeEtab);

        // Get the demandeMatriculeEtab
        restDemandeMatriculeEtabMockMvc
            .perform(get(ENTITY_API_URL_ID, demandeMatriculeEtab.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(demandeMatriculeEtab.getId().intValue()))
            .andExpect(jsonPath("$.numeroDemandeEtab").value(DEFAULT_NUMERO_DEMANDE_ETAB))
            .andExpect(jsonPath("$.nomEtab").value(DEFAULT_NOM_ETAB))
            .andExpect(jsonPath("$.typeEtab").value(DEFAULT_TYPE_ETAB.toString()))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT.toString()))
            .andExpect(jsonPath("$.emailEtab").value(DEFAULT_EMAIL_ETAB))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION.toString()))
            .andExpect(jsonPath("$.departement").value(DEFAULT_DEPARTEMENT.toString()))
            .andExpect(jsonPath("$.commune").value(DEFAULT_COMMUNE))
            .andExpect(jsonPath("$.typeInsp").value(DEFAULT_TYPE_INSP.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDemandeMatriculeEtab() throws Exception {
        // Get the demandeMatriculeEtab
        restDemandeMatriculeEtabMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDemandeMatriculeEtab() throws Exception {
        // Initialize the database
        demandeMatriculeEtabRepository.saveAndFlush(demandeMatriculeEtab);

        int databaseSizeBeforeUpdate = demandeMatriculeEtabRepository.findAll().size();

        // Update the demandeMatriculeEtab
        DemandeMatriculeEtab updatedDemandeMatriculeEtab = demandeMatriculeEtabRepository.findById(demandeMatriculeEtab.getId()).get();
        // Disconnect from session so that the updates on updatedDemandeMatriculeEtab are not directly saved in db
        em.detach(updatedDemandeMatriculeEtab);
        updatedDemandeMatriculeEtab
            .numeroDemandeEtab(UPDATED_NUMERO_DEMANDE_ETAB)
            .nomEtab(UPDATED_NOM_ETAB)
            .typeEtab(UPDATED_TYPE_ETAB)
            .statut(UPDATED_STATUT)
            .emailEtab(UPDATED_EMAIL_ETAB)
            .region(UPDATED_REGION)
            .departement(UPDATED_DEPARTEMENT)
            .commune(UPDATED_COMMUNE)
            .typeInsp(UPDATED_TYPE_INSP);

        restDemandeMatriculeEtabMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDemandeMatriculeEtab.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDemandeMatriculeEtab))
            )
            .andExpect(status().isOk());

        // Validate the DemandeMatriculeEtab in the database
        List<DemandeMatriculeEtab> demandeMatriculeEtabList = demandeMatriculeEtabRepository.findAll();
        assertThat(demandeMatriculeEtabList).hasSize(databaseSizeBeforeUpdate);
        DemandeMatriculeEtab testDemandeMatriculeEtab = demandeMatriculeEtabList.get(demandeMatriculeEtabList.size() - 1);
        assertThat(testDemandeMatriculeEtab.getNumeroDemandeEtab()).isEqualTo(UPDATED_NUMERO_DEMANDE_ETAB);
        assertThat(testDemandeMatriculeEtab.getNomEtab()).isEqualTo(UPDATED_NOM_ETAB);
        assertThat(testDemandeMatriculeEtab.getTypeEtab()).isEqualTo(UPDATED_TYPE_ETAB);
        assertThat(testDemandeMatriculeEtab.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testDemandeMatriculeEtab.getEmailEtab()).isEqualTo(UPDATED_EMAIL_ETAB);
        assertThat(testDemandeMatriculeEtab.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testDemandeMatriculeEtab.getDepartement()).isEqualTo(UPDATED_DEPARTEMENT);
        assertThat(testDemandeMatriculeEtab.getCommune()).isEqualTo(UPDATED_COMMUNE);
        assertThat(testDemandeMatriculeEtab.getTypeInsp()).isEqualTo(UPDATED_TYPE_INSP);
    }

    @Test
    @Transactional
    void putNonExistingDemandeMatriculeEtab() throws Exception {
        int databaseSizeBeforeUpdate = demandeMatriculeEtabRepository.findAll().size();
        demandeMatriculeEtab.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandeMatriculeEtabMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demandeMatriculeEtab.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatriculeEtab))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeMatriculeEtab in the database
        List<DemandeMatriculeEtab> demandeMatriculeEtabList = demandeMatriculeEtabRepository.findAll();
        assertThat(demandeMatriculeEtabList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDemandeMatriculeEtab() throws Exception {
        int databaseSizeBeforeUpdate = demandeMatriculeEtabRepository.findAll().size();
        demandeMatriculeEtab.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeMatriculeEtabMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatriculeEtab))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeMatriculeEtab in the database
        List<DemandeMatriculeEtab> demandeMatriculeEtabList = demandeMatriculeEtabRepository.findAll();
        assertThat(demandeMatriculeEtabList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDemandeMatriculeEtab() throws Exception {
        int databaseSizeBeforeUpdate = demandeMatriculeEtabRepository.findAll().size();
        demandeMatriculeEtab.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeMatriculeEtabMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatriculeEtab))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandeMatriculeEtab in the database
        List<DemandeMatriculeEtab> demandeMatriculeEtabList = demandeMatriculeEtabRepository.findAll();
        assertThat(demandeMatriculeEtabList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDemandeMatriculeEtabWithPatch() throws Exception {
        // Initialize the database
        demandeMatriculeEtabRepository.saveAndFlush(demandeMatriculeEtab);

        int databaseSizeBeforeUpdate = demandeMatriculeEtabRepository.findAll().size();

        // Update the demandeMatriculeEtab using partial update
        DemandeMatriculeEtab partialUpdatedDemandeMatriculeEtab = new DemandeMatriculeEtab();
        partialUpdatedDemandeMatriculeEtab.setId(demandeMatriculeEtab.getId());

        partialUpdatedDemandeMatriculeEtab
            .numeroDemandeEtab(UPDATED_NUMERO_DEMANDE_ETAB)
            .nomEtab(UPDATED_NOM_ETAB)
            .region(UPDATED_REGION)
            .departement(UPDATED_DEPARTEMENT)
            .commune(UPDATED_COMMUNE);

        restDemandeMatriculeEtabMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandeMatriculeEtab.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandeMatriculeEtab))
            )
            .andExpect(status().isOk());

        // Validate the DemandeMatriculeEtab in the database
        List<DemandeMatriculeEtab> demandeMatriculeEtabList = demandeMatriculeEtabRepository.findAll();
        assertThat(demandeMatriculeEtabList).hasSize(databaseSizeBeforeUpdate);
        DemandeMatriculeEtab testDemandeMatriculeEtab = demandeMatriculeEtabList.get(demandeMatriculeEtabList.size() - 1);
        assertThat(testDemandeMatriculeEtab.getNumeroDemandeEtab()).isEqualTo(UPDATED_NUMERO_DEMANDE_ETAB);
        assertThat(testDemandeMatriculeEtab.getNomEtab()).isEqualTo(UPDATED_NOM_ETAB);
        assertThat(testDemandeMatriculeEtab.getTypeEtab()).isEqualTo(DEFAULT_TYPE_ETAB);
        assertThat(testDemandeMatriculeEtab.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testDemandeMatriculeEtab.getEmailEtab()).isEqualTo(DEFAULT_EMAIL_ETAB);
        assertThat(testDemandeMatriculeEtab.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testDemandeMatriculeEtab.getDepartement()).isEqualTo(UPDATED_DEPARTEMENT);
        assertThat(testDemandeMatriculeEtab.getCommune()).isEqualTo(UPDATED_COMMUNE);
        assertThat(testDemandeMatriculeEtab.getTypeInsp()).isEqualTo(DEFAULT_TYPE_INSP);
    }

    @Test
    @Transactional
    void fullUpdateDemandeMatriculeEtabWithPatch() throws Exception {
        // Initialize the database
        demandeMatriculeEtabRepository.saveAndFlush(demandeMatriculeEtab);

        int databaseSizeBeforeUpdate = demandeMatriculeEtabRepository.findAll().size();

        // Update the demandeMatriculeEtab using partial update
        DemandeMatriculeEtab partialUpdatedDemandeMatriculeEtab = new DemandeMatriculeEtab();
        partialUpdatedDemandeMatriculeEtab.setId(demandeMatriculeEtab.getId());

        partialUpdatedDemandeMatriculeEtab
            .numeroDemandeEtab(UPDATED_NUMERO_DEMANDE_ETAB)
            .nomEtab(UPDATED_NOM_ETAB)
            .typeEtab(UPDATED_TYPE_ETAB)
            .statut(UPDATED_STATUT)
            .emailEtab(UPDATED_EMAIL_ETAB)
            .region(UPDATED_REGION)
            .departement(UPDATED_DEPARTEMENT)
            .commune(UPDATED_COMMUNE)
            .typeInsp(UPDATED_TYPE_INSP);

        restDemandeMatriculeEtabMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandeMatriculeEtab.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandeMatriculeEtab))
            )
            .andExpect(status().isOk());

        // Validate the DemandeMatriculeEtab in the database
        List<DemandeMatriculeEtab> demandeMatriculeEtabList = demandeMatriculeEtabRepository.findAll();
        assertThat(demandeMatriculeEtabList).hasSize(databaseSizeBeforeUpdate);
        DemandeMatriculeEtab testDemandeMatriculeEtab = demandeMatriculeEtabList.get(demandeMatriculeEtabList.size() - 1);
        assertThat(testDemandeMatriculeEtab.getNumeroDemandeEtab()).isEqualTo(UPDATED_NUMERO_DEMANDE_ETAB);
        assertThat(testDemandeMatriculeEtab.getNomEtab()).isEqualTo(UPDATED_NOM_ETAB);
        assertThat(testDemandeMatriculeEtab.getTypeEtab()).isEqualTo(UPDATED_TYPE_ETAB);
        assertThat(testDemandeMatriculeEtab.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testDemandeMatriculeEtab.getEmailEtab()).isEqualTo(UPDATED_EMAIL_ETAB);
        assertThat(testDemandeMatriculeEtab.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testDemandeMatriculeEtab.getDepartement()).isEqualTo(UPDATED_DEPARTEMENT);
        assertThat(testDemandeMatriculeEtab.getCommune()).isEqualTo(UPDATED_COMMUNE);
        assertThat(testDemandeMatriculeEtab.getTypeInsp()).isEqualTo(UPDATED_TYPE_INSP);
    }

    @Test
    @Transactional
    void patchNonExistingDemandeMatriculeEtab() throws Exception {
        int databaseSizeBeforeUpdate = demandeMatriculeEtabRepository.findAll().size();
        demandeMatriculeEtab.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandeMatriculeEtabMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, demandeMatriculeEtab.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatriculeEtab))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeMatriculeEtab in the database
        List<DemandeMatriculeEtab> demandeMatriculeEtabList = demandeMatriculeEtabRepository.findAll();
        assertThat(demandeMatriculeEtabList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDemandeMatriculeEtab() throws Exception {
        int databaseSizeBeforeUpdate = demandeMatriculeEtabRepository.findAll().size();
        demandeMatriculeEtab.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeMatriculeEtabMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatriculeEtab))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeMatriculeEtab in the database
        List<DemandeMatriculeEtab> demandeMatriculeEtabList = demandeMatriculeEtabRepository.findAll();
        assertThat(demandeMatriculeEtabList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDemandeMatriculeEtab() throws Exception {
        int databaseSizeBeforeUpdate = demandeMatriculeEtabRepository.findAll().size();
        demandeMatriculeEtab.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeMatriculeEtabMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeMatriculeEtab))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandeMatriculeEtab in the database
        List<DemandeMatriculeEtab> demandeMatriculeEtabList = demandeMatriculeEtabRepository.findAll();
        assertThat(demandeMatriculeEtabList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDemandeMatriculeEtab() throws Exception {
        // Initialize the database
        demandeMatriculeEtabRepository.saveAndFlush(demandeMatriculeEtab);

        int databaseSizeBeforeDelete = demandeMatriculeEtabRepository.findAll().size();

        // Delete the demandeMatriculeEtab
        restDemandeMatriculeEtabMockMvc
            .perform(delete(ENTITY_API_URL_ID, demandeMatriculeEtab.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DemandeMatriculeEtab> demandeMatriculeEtabList = demandeMatriculeEtabRepository.findAll();
        assertThat(demandeMatriculeEtabList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
