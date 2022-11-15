package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.PersonnelEtab;
import com.mycompany.myapp.repository.PersonnelEtabRepository;
import com.mycompany.myapp.service.PersonnelEtabService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PersonnelEtab}.
 */
@RestController
@RequestMapping("/api")
public class PersonnelEtabResource {

    private final Logger log = LoggerFactory.getLogger(PersonnelEtabResource.class);

    private static final String ENTITY_NAME = "personnelEtab";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonnelEtabService personnelEtabService;

    private final PersonnelEtabRepository personnelEtabRepository;

    public PersonnelEtabResource(PersonnelEtabService personnelEtabService, PersonnelEtabRepository personnelEtabRepository) {
        this.personnelEtabService = personnelEtabService;
        this.personnelEtabRepository = personnelEtabRepository;
    }

    /**
     * {@code POST  /personnel-etabs} : Create a new personnelEtab.
     *
     * @param personnelEtab the personnelEtab to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personnelEtab, or with status {@code 400 (Bad Request)} if the personnelEtab has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/personnel-etabs")
    public ResponseEntity<PersonnelEtab> createPersonnelEtab(@Valid @RequestBody PersonnelEtab personnelEtab) throws URISyntaxException {
        log.debug("REST request to save PersonnelEtab : {}", personnelEtab);
        if (personnelEtab.getId() != null) {
            throw new BadRequestAlertException("A new personnelEtab cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonnelEtab result = personnelEtabService.save(personnelEtab);
        return ResponseEntity
            .created(new URI("/api/personnel-etabs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /personnel-etabs/:id} : Updates an existing personnelEtab.
     *
     * @param id the id of the personnelEtab to save.
     * @param personnelEtab the personnelEtab to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personnelEtab,
     * or with status {@code 400 (Bad Request)} if the personnelEtab is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personnelEtab couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/personnel-etabs/{id}")
    public ResponseEntity<PersonnelEtab> updatePersonnelEtab(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PersonnelEtab personnelEtab
    ) throws URISyntaxException {
        log.debug("REST request to update PersonnelEtab : {}, {}", id, personnelEtab);
        if (personnelEtab.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personnelEtab.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personnelEtabRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PersonnelEtab result = personnelEtabService.save(personnelEtab);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, personnelEtab.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /personnel-etabs/:id} : Partial updates given fields of an existing personnelEtab, field will ignore if it is null
     *
     * @param id the id of the personnelEtab to save.
     * @param personnelEtab the personnelEtab to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personnelEtab,
     * or with status {@code 400 (Bad Request)} if the personnelEtab is not valid,
     * or with status {@code 404 (Not Found)} if the personnelEtab is not found,
     * or with status {@code 500 (Internal Server Error)} if the personnelEtab couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/personnel-etabs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PersonnelEtab> partialUpdatePersonnelEtab(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PersonnelEtab personnelEtab
    ) throws URISyntaxException {
        log.debug("REST request to partial update PersonnelEtab partially : {}, {}", id, personnelEtab);
        if (personnelEtab.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personnelEtab.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personnelEtabRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PersonnelEtab> result = personnelEtabService.partialUpdate(personnelEtab);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, personnelEtab.getId().toString())
        );
    }

    /**
     * {@code GET  /personnel-etabs} : get all the personnelEtabs.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personnelEtabs in body.
     */
    @GetMapping("/personnel-etabs")
    public ResponseEntity<List<PersonnelEtab>> getAllPersonnelEtabs(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of PersonnelEtabs");
        Page<PersonnelEtab> page;
        if (eagerload) {
            page = personnelEtabService.findAllWithEagerRelationships(pageable);
        } else {
            page = personnelEtabService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /personnel-etabs/:id} : get the "id" personnelEtab.
     *
     * @param id the id of the personnelEtab to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personnelEtab, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/personnel-etabs/{id}")
    public ResponseEntity<PersonnelEtab> getPersonnelEtab(@PathVariable Long id) {
        log.debug("REST request to get PersonnelEtab : {}", id);
        Optional<PersonnelEtab> personnelEtab = personnelEtabService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personnelEtab);
    }

    /**
     * {@code DELETE  /personnel-etabs/:id} : delete the "id" personnelEtab.
     *
     * @param id the id of the personnelEtab to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/personnel-etabs/{id}")
    public ResponseEntity<Void> deletePersonnelEtab(@PathVariable Long id) {
        log.debug("REST request to delete PersonnelEtab : {}", id);
        personnelEtabService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
