package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.DemandeMatriculeEtab;
import com.mycompany.myapp.repository.DemandeMatriculeEtabRepository;
import com.mycompany.myapp.service.DemandeMatriculeEtabService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.DemandeMatriculeEtab}.
 */
@RestController
@RequestMapping("/api")
public class DemandeMatriculeEtabResource {

    private final Logger log = LoggerFactory.getLogger(DemandeMatriculeEtabResource.class);

    private static final String ENTITY_NAME = "demandeMatriculeEtab";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DemandeMatriculeEtabService demandeMatriculeEtabService;

    private final DemandeMatriculeEtabRepository demandeMatriculeEtabRepository;

    public DemandeMatriculeEtabResource(
        DemandeMatriculeEtabService demandeMatriculeEtabService,
        DemandeMatriculeEtabRepository demandeMatriculeEtabRepository
    ) {
        this.demandeMatriculeEtabService = demandeMatriculeEtabService;
        this.demandeMatriculeEtabRepository = demandeMatriculeEtabRepository;
    }

    /**
     * {@code POST  /demande-matricule-etabs} : Create a new demandeMatriculeEtab.
     *
     * @param demandeMatriculeEtab the demandeMatriculeEtab to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new demandeMatriculeEtab, or with status {@code 400 (Bad Request)} if the demandeMatriculeEtab has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/demande-matricule-etabs")
    public ResponseEntity<DemandeMatriculeEtab> createDemandeMatriculeEtab(@Valid @RequestBody DemandeMatriculeEtab demandeMatriculeEtab)
        throws URISyntaxException {
        log.debug("REST request to save DemandeMatriculeEtab : {}", demandeMatriculeEtab);
        if (demandeMatriculeEtab.getId() != null) {
            throw new BadRequestAlertException("A new demandeMatriculeEtab cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DemandeMatriculeEtab result = demandeMatriculeEtabService.save(demandeMatriculeEtab);
        return ResponseEntity
            .created(new URI("/api/demande-matricule-etabs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /demande-matricule-etabs/:id} : Updates an existing demandeMatriculeEtab.
     *
     * @param id the id of the demandeMatriculeEtab to save.
     * @param demandeMatriculeEtab the demandeMatriculeEtab to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandeMatriculeEtab,
     * or with status {@code 400 (Bad Request)} if the demandeMatriculeEtab is not valid,
     * or with status {@code 500 (Internal Server Error)} if the demandeMatriculeEtab couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/demande-matricule-etabs/{id}")
    public ResponseEntity<DemandeMatriculeEtab> updateDemandeMatriculeEtab(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DemandeMatriculeEtab demandeMatriculeEtab
    ) throws URISyntaxException {
        log.debug("REST request to update DemandeMatriculeEtab : {}, {}", id, demandeMatriculeEtab);
        if (demandeMatriculeEtab.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandeMatriculeEtab.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandeMatriculeEtabRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DemandeMatriculeEtab result = demandeMatriculeEtabService.save(demandeMatriculeEtab);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, demandeMatriculeEtab.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /demande-matricule-etabs/:id} : Partial updates given fields of an existing demandeMatriculeEtab, field will ignore if it is null
     *
     * @param id the id of the demandeMatriculeEtab to save.
     * @param demandeMatriculeEtab the demandeMatriculeEtab to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandeMatriculeEtab,
     * or with status {@code 400 (Bad Request)} if the demandeMatriculeEtab is not valid,
     * or with status {@code 404 (Not Found)} if the demandeMatriculeEtab is not found,
     * or with status {@code 500 (Internal Server Error)} if the demandeMatriculeEtab couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/demande-matricule-etabs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DemandeMatriculeEtab> partialUpdateDemandeMatriculeEtab(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DemandeMatriculeEtab demandeMatriculeEtab
    ) throws URISyntaxException {
        log.debug("REST request to partial update DemandeMatriculeEtab partially : {}, {}", id, demandeMatriculeEtab);
        if (demandeMatriculeEtab.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandeMatriculeEtab.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandeMatriculeEtabRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DemandeMatriculeEtab> result = demandeMatriculeEtabService.partialUpdate(demandeMatriculeEtab);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, demandeMatriculeEtab.getId().toString())
        );
    }

    /**
     * {@code GET  /demande-matricule-etabs} : get all the demandeMatriculeEtabs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demandeMatriculeEtabs in body.
     */
    @GetMapping("/demande-matricule-etabs")
    public ResponseEntity<List<DemandeMatriculeEtab>> getAllDemandeMatriculeEtabs(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of DemandeMatriculeEtabs");
        Page<DemandeMatriculeEtab> page = demandeMatriculeEtabService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /demande-matricule-etabs/:id} : get the "id" demandeMatriculeEtab.
     *
     * @param id the id of the demandeMatriculeEtab to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the demandeMatriculeEtab, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/demande-matricule-etabs/{id}")
    public ResponseEntity<DemandeMatriculeEtab> getDemandeMatriculeEtab(@PathVariable Long id) {
        log.debug("REST request to get DemandeMatriculeEtab : {}", id);
        Optional<DemandeMatriculeEtab> demandeMatriculeEtab = demandeMatriculeEtabService.findOne(id);
        return ResponseUtil.wrapOrNotFound(demandeMatriculeEtab);
    }

    /**
     * {@code DELETE  /demande-matricule-etabs/:id} : delete the "id" demandeMatriculeEtab.
     *
     * @param id the id of the demandeMatriculeEtab to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/demande-matricule-etabs/{id}")
    public ResponseEntity<Void> deleteDemandeMatriculeEtab(@PathVariable Long id) {
        log.debug("REST request to delete DemandeMatriculeEtab : {}", id);
        demandeMatriculeEtabService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
