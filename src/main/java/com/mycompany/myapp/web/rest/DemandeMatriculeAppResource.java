package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.DemandeMatriculeApp;
import com.mycompany.myapp.repository.DemandeMatriculeAppRepository;
import com.mycompany.myapp.service.DemandeMatriculeAppService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.DemandeMatriculeApp}.
 */
@RestController
@RequestMapping("/api")
public class DemandeMatriculeAppResource {

    private final Logger log = LoggerFactory.getLogger(DemandeMatriculeAppResource.class);

    private static final String ENTITY_NAME = "demandeMatriculeApp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DemandeMatriculeAppService demandeMatriculeAppService;

    private final DemandeMatriculeAppRepository demandeMatriculeAppRepository;

    public DemandeMatriculeAppResource(
        DemandeMatriculeAppService demandeMatriculeAppService,
        DemandeMatriculeAppRepository demandeMatriculeAppRepository
    ) {
        this.demandeMatriculeAppService = demandeMatriculeAppService;
        this.demandeMatriculeAppRepository = demandeMatriculeAppRepository;
    }

    /**
     * {@code POST  /demande-matricule-apps} : Create a new demandeMatriculeApp.
     *
     * @param demandeMatriculeApp the demandeMatriculeApp to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new demandeMatriculeApp, or with status {@code 400 (Bad Request)} if the demandeMatriculeApp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/demande-matricule-apps")
    public ResponseEntity<DemandeMatriculeApp> createDemandeMatriculeApp(@Valid @RequestBody DemandeMatriculeApp demandeMatriculeApp)
        throws URISyntaxException {
        log.debug("REST request to save DemandeMatriculeApp : {}", demandeMatriculeApp);
        if (demandeMatriculeApp.getId() != null) {
            throw new BadRequestAlertException("A new demandeMatriculeApp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DemandeMatriculeApp result = demandeMatriculeAppService.save(demandeMatriculeApp);
        return ResponseEntity
            .created(new URI("/api/demande-matricule-apps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /demande-matricule-apps/:id} : Updates an existing demandeMatriculeApp.
     *
     * @param id the id of the demandeMatriculeApp to save.
     * @param demandeMatriculeApp the demandeMatriculeApp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandeMatriculeApp,
     * or with status {@code 400 (Bad Request)} if the demandeMatriculeApp is not valid,
     * or with status {@code 500 (Internal Server Error)} if the demandeMatriculeApp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/demande-matricule-apps/{id}")
    public ResponseEntity<DemandeMatriculeApp> updateDemandeMatriculeApp(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DemandeMatriculeApp demandeMatriculeApp
    ) throws URISyntaxException {
        log.debug("REST request to update DemandeMatriculeApp : {}, {}", id, demandeMatriculeApp);
        if (demandeMatriculeApp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandeMatriculeApp.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandeMatriculeAppRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DemandeMatriculeApp result = demandeMatriculeAppService.save(demandeMatriculeApp);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, demandeMatriculeApp.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /demande-matricule-apps/:id} : Partial updates given fields of an existing demandeMatriculeApp, field will ignore if it is null
     *
     * @param id the id of the demandeMatriculeApp to save.
     * @param demandeMatriculeApp the demandeMatriculeApp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandeMatriculeApp,
     * or with status {@code 400 (Bad Request)} if the demandeMatriculeApp is not valid,
     * or with status {@code 404 (Not Found)} if the demandeMatriculeApp is not found,
     * or with status {@code 500 (Internal Server Error)} if the demandeMatriculeApp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/demande-matricule-apps/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DemandeMatriculeApp> partialUpdateDemandeMatriculeApp(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DemandeMatriculeApp demandeMatriculeApp
    ) throws URISyntaxException {
        log.debug("REST request to partial update DemandeMatriculeApp partially : {}, {}", id, demandeMatriculeApp);
        if (demandeMatriculeApp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandeMatriculeApp.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandeMatriculeAppRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DemandeMatriculeApp> result = demandeMatriculeAppService.partialUpdate(demandeMatriculeApp);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, demandeMatriculeApp.getId().toString())
        );
    }

    /**
     * {@code GET  /demande-matricule-apps} : get all the demandeMatriculeApps.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demandeMatriculeApps in body.
     */
    @GetMapping("/demande-matricule-apps")
    public ResponseEntity<List<DemandeMatriculeApp>> getAllDemandeMatriculeApps(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of DemandeMatriculeApps");
        Page<DemandeMatriculeApp> page = demandeMatriculeAppService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /demande-matricule-apps/:id} : get the "id" demandeMatriculeApp.
     *
     * @param id the id of the demandeMatriculeApp to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the demandeMatriculeApp, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/demande-matricule-apps/{id}")
    public ResponseEntity<DemandeMatriculeApp> getDemandeMatriculeApp(@PathVariable Long id) {
        log.debug("REST request to get DemandeMatriculeApp : {}", id);
        Optional<DemandeMatriculeApp> demandeMatriculeApp = demandeMatriculeAppService.findOne(id);
        return ResponseUtil.wrapOrNotFound(demandeMatriculeApp);
    }

    /**
     * {@code DELETE  /demande-matricule-apps/:id} : delete the "id" demandeMatriculeApp.
     *
     * @param id the id of the demandeMatriculeApp to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/demande-matricule-apps/{id}")
    public ResponseEntity<Void> deleteDemandeMatriculeApp(@PathVariable Long id) {
        log.debug("REST request to delete DemandeMatriculeApp : {}", id);
        demandeMatriculeAppService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
