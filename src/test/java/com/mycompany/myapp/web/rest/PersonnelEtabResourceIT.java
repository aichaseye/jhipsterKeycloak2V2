package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.PersonnelEtab;
import com.mycompany.myapp.domain.enumeration.Fonction;
import com.mycompany.myapp.repository.PersonnelEtabRepository;
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
 * Integration tests for the {@link PersonnelEtabResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PersonnelEtabResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final Fonction DEFAULT_FONCTION = Fonction.Proviseur;
    private static final Fonction UPDATED_FONCTION = Fonction.Directeur;

    private static final String ENTITY_API_URL = "/api/personnel-etabs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PersonnelEtabRepository personnelEtabRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonnelEtabMockMvc;

    private PersonnelEtab personnelEtab;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonnelEtab createEntity(EntityManager em) {
        PersonnelEtab personnelEtab = new PersonnelEtab().nom(DEFAULT_NOM).prenom(DEFAULT_PRENOM).fonction(DEFAULT_FONCTION);
        return personnelEtab;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonnelEtab createUpdatedEntity(EntityManager em) {
        PersonnelEtab personnelEtab = new PersonnelEtab().nom(UPDATED_NOM).prenom(UPDATED_PRENOM).fonction(UPDATED_FONCTION);
        return personnelEtab;
    }

    @BeforeEach
    public void initTest() {
        personnelEtab = createEntity(em);
    }

    @Test
    @Transactional
    void createPersonnelEtab() throws Exception {
        int databaseSizeBeforeCreate = personnelEtabRepository.findAll().size();
        // Create the PersonnelEtab
        restPersonnelEtabMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personnelEtab))
            )
            .andExpect(status().isCreated());

        // Validate the PersonnelEtab in the database
        List<PersonnelEtab> personnelEtabList = personnelEtabRepository.findAll();
        assertThat(personnelEtabList).hasSize(databaseSizeBeforeCreate + 1);
        PersonnelEtab testPersonnelEtab = personnelEtabList.get(personnelEtabList.size() - 1);
        assertThat(testPersonnelEtab.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testPersonnelEtab.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testPersonnelEtab.getFonction()).isEqualTo(DEFAULT_FONCTION);
    }

    @Test
    @Transactional
    void createPersonnelEtabWithExistingId() throws Exception {
        // Create the PersonnelEtab with an existing ID
        personnelEtab.setId(1L);

        int databaseSizeBeforeCreate = personnelEtabRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonnelEtabMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personnelEtab))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonnelEtab in the database
        List<PersonnelEtab> personnelEtabList = personnelEtabRepository.findAll();
        assertThat(personnelEtabList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = personnelEtabRepository.findAll().size();
        // set the field null
        personnelEtab.setNom(null);

        // Create the PersonnelEtab, which fails.

        restPersonnelEtabMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personnelEtab))
            )
            .andExpect(status().isBadRequest());

        List<PersonnelEtab> personnelEtabList = personnelEtabRepository.findAll();
        assertThat(personnelEtabList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = personnelEtabRepository.findAll().size();
        // set the field null
        personnelEtab.setPrenom(null);

        // Create the PersonnelEtab, which fails.

        restPersonnelEtabMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personnelEtab))
            )
            .andExpect(status().isBadRequest());

        List<PersonnelEtab> personnelEtabList = personnelEtabRepository.findAll();
        assertThat(personnelEtabList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFonctionIsRequired() throws Exception {
        int databaseSizeBeforeTest = personnelEtabRepository.findAll().size();
        // set the field null
        personnelEtab.setFonction(null);

        // Create the PersonnelEtab, which fails.

        restPersonnelEtabMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personnelEtab))
            )
            .andExpect(status().isBadRequest());

        List<PersonnelEtab> personnelEtabList = personnelEtabRepository.findAll();
        assertThat(personnelEtabList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPersonnelEtabs() throws Exception {
        // Initialize the database
        personnelEtabRepository.saveAndFlush(personnelEtab);

        // Get all the personnelEtabList
        restPersonnelEtabMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personnelEtab.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].fonction").value(hasItem(DEFAULT_FONCTION.toString())));
    }

    @Test
    @Transactional
    void getPersonnelEtab() throws Exception {
        // Initialize the database
        personnelEtabRepository.saveAndFlush(personnelEtab);

        // Get the personnelEtab
        restPersonnelEtabMockMvc
            .perform(get(ENTITY_API_URL_ID, personnelEtab.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personnelEtab.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.fonction").value(DEFAULT_FONCTION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPersonnelEtab() throws Exception {
        // Get the personnelEtab
        restPersonnelEtabMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPersonnelEtab() throws Exception {
        // Initialize the database
        personnelEtabRepository.saveAndFlush(personnelEtab);

        int databaseSizeBeforeUpdate = personnelEtabRepository.findAll().size();

        // Update the personnelEtab
        PersonnelEtab updatedPersonnelEtab = personnelEtabRepository.findById(personnelEtab.getId()).get();
        // Disconnect from session so that the updates on updatedPersonnelEtab are not directly saved in db
        em.detach(updatedPersonnelEtab);
        updatedPersonnelEtab.nom(UPDATED_NOM).prenom(UPDATED_PRENOM).fonction(UPDATED_FONCTION);

        restPersonnelEtabMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPersonnelEtab.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPersonnelEtab))
            )
            .andExpect(status().isOk());

        // Validate the PersonnelEtab in the database
        List<PersonnelEtab> personnelEtabList = personnelEtabRepository.findAll();
        assertThat(personnelEtabList).hasSize(databaseSizeBeforeUpdate);
        PersonnelEtab testPersonnelEtab = personnelEtabList.get(personnelEtabList.size() - 1);
        assertThat(testPersonnelEtab.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPersonnelEtab.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testPersonnelEtab.getFonction()).isEqualTo(UPDATED_FONCTION);
    }

    @Test
    @Transactional
    void putNonExistingPersonnelEtab() throws Exception {
        int databaseSizeBeforeUpdate = personnelEtabRepository.findAll().size();
        personnelEtab.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonnelEtabMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personnelEtab.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personnelEtab))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonnelEtab in the database
        List<PersonnelEtab> personnelEtabList = personnelEtabRepository.findAll();
        assertThat(personnelEtabList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPersonnelEtab() throws Exception {
        int databaseSizeBeforeUpdate = personnelEtabRepository.findAll().size();
        personnelEtab.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonnelEtabMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personnelEtab))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonnelEtab in the database
        List<PersonnelEtab> personnelEtabList = personnelEtabRepository.findAll();
        assertThat(personnelEtabList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPersonnelEtab() throws Exception {
        int databaseSizeBeforeUpdate = personnelEtabRepository.findAll().size();
        personnelEtab.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonnelEtabMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personnelEtab))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonnelEtab in the database
        List<PersonnelEtab> personnelEtabList = personnelEtabRepository.findAll();
        assertThat(personnelEtabList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePersonnelEtabWithPatch() throws Exception {
        // Initialize the database
        personnelEtabRepository.saveAndFlush(personnelEtab);

        int databaseSizeBeforeUpdate = personnelEtabRepository.findAll().size();

        // Update the personnelEtab using partial update
        PersonnelEtab partialUpdatedPersonnelEtab = new PersonnelEtab();
        partialUpdatedPersonnelEtab.setId(personnelEtab.getId());

        partialUpdatedPersonnelEtab.nom(UPDATED_NOM).fonction(UPDATED_FONCTION);

        restPersonnelEtabMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonnelEtab.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonnelEtab))
            )
            .andExpect(status().isOk());

        // Validate the PersonnelEtab in the database
        List<PersonnelEtab> personnelEtabList = personnelEtabRepository.findAll();
        assertThat(personnelEtabList).hasSize(databaseSizeBeforeUpdate);
        PersonnelEtab testPersonnelEtab = personnelEtabList.get(personnelEtabList.size() - 1);
        assertThat(testPersonnelEtab.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPersonnelEtab.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testPersonnelEtab.getFonction()).isEqualTo(UPDATED_FONCTION);
    }

    @Test
    @Transactional
    void fullUpdatePersonnelEtabWithPatch() throws Exception {
        // Initialize the database
        personnelEtabRepository.saveAndFlush(personnelEtab);

        int databaseSizeBeforeUpdate = personnelEtabRepository.findAll().size();

        // Update the personnelEtab using partial update
        PersonnelEtab partialUpdatedPersonnelEtab = new PersonnelEtab();
        partialUpdatedPersonnelEtab.setId(personnelEtab.getId());

        partialUpdatedPersonnelEtab.nom(UPDATED_NOM).prenom(UPDATED_PRENOM).fonction(UPDATED_FONCTION);

        restPersonnelEtabMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonnelEtab.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonnelEtab))
            )
            .andExpect(status().isOk());

        // Validate the PersonnelEtab in the database
        List<PersonnelEtab> personnelEtabList = personnelEtabRepository.findAll();
        assertThat(personnelEtabList).hasSize(databaseSizeBeforeUpdate);
        PersonnelEtab testPersonnelEtab = personnelEtabList.get(personnelEtabList.size() - 1);
        assertThat(testPersonnelEtab.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPersonnelEtab.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testPersonnelEtab.getFonction()).isEqualTo(UPDATED_FONCTION);
    }

    @Test
    @Transactional
    void patchNonExistingPersonnelEtab() throws Exception {
        int databaseSizeBeforeUpdate = personnelEtabRepository.findAll().size();
        personnelEtab.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonnelEtabMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, personnelEtab.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personnelEtab))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonnelEtab in the database
        List<PersonnelEtab> personnelEtabList = personnelEtabRepository.findAll();
        assertThat(personnelEtabList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPersonnelEtab() throws Exception {
        int databaseSizeBeforeUpdate = personnelEtabRepository.findAll().size();
        personnelEtab.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonnelEtabMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personnelEtab))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonnelEtab in the database
        List<PersonnelEtab> personnelEtabList = personnelEtabRepository.findAll();
        assertThat(personnelEtabList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPersonnelEtab() throws Exception {
        int databaseSizeBeforeUpdate = personnelEtabRepository.findAll().size();
        personnelEtab.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonnelEtabMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personnelEtab))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonnelEtab in the database
        List<PersonnelEtab> personnelEtabList = personnelEtabRepository.findAll();
        assertThat(personnelEtabList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePersonnelEtab() throws Exception {
        // Initialize the database
        personnelEtabRepository.saveAndFlush(personnelEtab);

        int databaseSizeBeforeDelete = personnelEtabRepository.findAll().size();

        // Delete the personnelEtab
        restPersonnelEtabMockMvc
            .perform(delete(ENTITY_API_URL_ID, personnelEtab.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersonnelEtab> personnelEtabList = personnelEtabRepository.findAll();
        assertThat(personnelEtabList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
