package it.alma.geditor.web.rest;

import it.alma.geditor.annotations.security.IsAdminPreAutorized;
import it.alma.geditor.domain.LmTemplate;
import it.alma.geditor.repository.LmTemplateRepository;
import it.alma.geditor.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link it.alma.geditor.domain.LmTemplate}.
 */
@RestController
@RequestMapping("/api")
public class LmTemplateResource {

    private final Logger log = LoggerFactory.getLogger(LmTemplateResource.class);

    private static final String ENTITY_NAME = "lmTemplate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LmTemplateRepository lmTemplateRepository;

    public LmTemplateResource(LmTemplateRepository lmTemplateRepository) {
        this.lmTemplateRepository = lmTemplateRepository;
    }

    /**
     * {@code POST  /lm-templates} : Create a new lmTemplate.
     *
     * @param lmTemplate the lmTemplate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lmTemplate, or with status {@code 400 (Bad Request)} if the lmTemplate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @IsAdminPreAutorized
    @PostMapping("/lm-templates")
    public ResponseEntity<LmTemplate> createLmTemplate(@RequestBody LmTemplate lmTemplate) throws URISyntaxException {
        log.debug("REST request to save LmTemplate : {}", lmTemplate);
        if (lmTemplate.getId() != null) {
            throw new BadRequestAlertException("A new lmTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LmTemplate result = lmTemplateRepository.save(lmTemplate);
        return ResponseEntity.created(new URI("/api/lm-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lm-templates} : Updates an existing lmTemplate.
     *
     * @param lmTemplate the lmTemplate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lmTemplate,
     * or with status {@code 400 (Bad Request)} if the lmTemplate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lmTemplate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @IsAdminPreAutorized
    @PutMapping("/lm-templates")
    public ResponseEntity<LmTemplate> updateLmTemplate(@RequestBody LmTemplate lmTemplate) throws URISyntaxException {
        log.debug("REST request to update LmTemplate : {}", lmTemplate);
        if (lmTemplate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LmTemplate result = lmTemplateRepository.save(lmTemplate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lmTemplate.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /lm-templates} : get all the lmTemplates.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lmTemplates in body.
     */
    @IsAdminPreAutorized
    @GetMapping("/lm-templates")
    public List<LmTemplate> getAllLmTemplates() {
        log.debug("REST request to get all LmTemplates");
        return lmTemplateRepository.findAll();
    }

    /**
     * {@code GET  /lm-templates/:id} : get the "id" lmTemplate.
     *
     * @param id the id of the lmTemplate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lmTemplate, or with status {@code 404 (Not Found)}.
     */
    @IsAdminPreAutorized
    @GetMapping("/lm-templates/{id}")
    public ResponseEntity<LmTemplate> getLmTemplate(@PathVariable Long id) {
        log.debug("REST request to get LmTemplate : {}", id);
        Optional<LmTemplate> lmTemplate = lmTemplateRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(lmTemplate);
    }

    /**
     * {@code DELETE  /lm-templates/:id} : delete the "id" lmTemplate.
     *
     * @param id the id of the lmTemplate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @IsAdminPreAutorized
    @DeleteMapping("/lm-templates/{id}")
    public ResponseEntity<Void> deleteLmTemplate(@PathVariable Long id) {
        log.debug("REST request to delete LmTemplate : {}", id);
        lmTemplateRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
