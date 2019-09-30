package it.alma.geditor.web.rest;

import it.alma.geditor.domain.CompilationLog;
import it.alma.geditor.repository.CompilationLogRepository;
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
 * REST controller for managing {@link it.alma.geditor.domain.CompilationLog}.
 */
@RestController
@RequestMapping("/api")
public class CompilationLogResource {

    private final Logger log = LoggerFactory.getLogger(CompilationLogResource.class);

    private static final String ENTITY_NAME = "compilationLog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompilationLogRepository compilationLogRepository;

    public CompilationLogResource(CompilationLogRepository compilationLogRepository) {
        this.compilationLogRepository = compilationLogRepository;
    }

    /**
     * {@code POST  /compilation-logs} : Create a new compilationLog.
     *
     * @param compilationLog the compilationLog to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new compilationLog, or with status {@code 400 (Bad Request)} if the compilationLog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/compilation-logs")
    public ResponseEntity<CompilationLog> createCompilationLog(@RequestBody CompilationLog compilationLog) throws URISyntaxException {
        log.debug("REST request to save CompilationLog : {}", compilationLog);
        if (compilationLog.getId() != null) {
            throw new BadRequestAlertException("A new compilationLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompilationLog result = compilationLogRepository.save(compilationLog);
        return ResponseEntity.created(new URI("/api/compilation-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /compilation-logs} : Updates an existing compilationLog.
     *
     * @param compilationLog the compilationLog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated compilationLog,
     * or with status {@code 400 (Bad Request)} if the compilationLog is not valid,
     * or with status {@code 500 (Internal Server Error)} if the compilationLog couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/compilation-logs")
    public ResponseEntity<CompilationLog> updateCompilationLog(@RequestBody CompilationLog compilationLog) throws URISyntaxException {
        log.debug("REST request to update CompilationLog : {}", compilationLog);
        if (compilationLog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompilationLog result = compilationLogRepository.save(compilationLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, compilationLog.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /compilation-logs} : get all the compilationLogs.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of compilationLogs in body.
     */
    @GetMapping("/compilation-logs")
    public List<CompilationLog> getAllCompilationLogs() {
        log.debug("REST request to get all CompilationLogs");
        return compilationLogRepository.findAll();
    }

    /**
     * {@code GET  /compilation-logs/:id} : get the "id" compilationLog.
     *
     * @param id the id of the compilationLog to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the compilationLog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/compilation-logs/{id}")
    public ResponseEntity<CompilationLog> getCompilationLog(@PathVariable Long id) {
        log.debug("REST request to get CompilationLog : {}", id);
        Optional<CompilationLog> compilationLog = compilationLogRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(compilationLog);
    }

    /**
     * {@code DELETE  /compilation-logs/:id} : delete the "id" compilationLog.
     *
     * @param id the id of the compilationLog to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/compilation-logs/{id}")
    public ResponseEntity<Void> deleteCompilationLog(@PathVariable Long id) {
        log.debug("REST request to delete CompilationLog : {}", id);
        compilationLogRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
