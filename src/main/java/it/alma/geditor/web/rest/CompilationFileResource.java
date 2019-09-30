package it.alma.geditor.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import it.alma.geditor.domain.CompilationFile;
import it.alma.geditor.repository.CompilationFileRepository;
import it.alma.geditor.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link it.alma.geditor.domain.CompilationFile}.
 */
@RestController
@RequestMapping("/api")
public class CompilationFileResource {

    private final Logger log = LoggerFactory.getLogger(CompilationFileResource.class);

    private static final String ENTITY_NAME = "compilationFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompilationFileRepository compilationFileRepository;

    public CompilationFileResource(CompilationFileRepository compilationFileRepository) {
        this.compilationFileRepository = compilationFileRepository;
    }

    /**
     * {@code POST  /compilation-files} : Create a new compilationFile.
     *
     * @param compilationFile the compilationFile to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new compilationFile, or with status {@code 400 (Bad Request)} if the compilationFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/compilation-files")
    public ResponseEntity<CompilationFile> createCompilationFile(@RequestBody CompilationFile compilationFile) throws URISyntaxException {
        log.debug("REST request to save CompilationFile : {}", compilationFile);
        if (compilationFile.getId() != null) {
            throw new BadRequestAlertException("A new compilationFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompilationFile result = compilationFileRepository.save(compilationFile);
        return ResponseEntity.created(new URI("/api/compilation-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /compilation-files} : Updates an existing compilationFile.
     *
     * @param compilationFile the compilationFile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated compilationFile,
     * or with status {@code 400 (Bad Request)} if the compilationFile is not valid,
     * or with status {@code 500 (Internal Server Error)} if the compilationFile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/compilation-files")
    public ResponseEntity<CompilationFile> updateCompilationFile(@RequestBody CompilationFile compilationFile) throws URISyntaxException {
        log.debug("REST request to update CompilationFile : {}", compilationFile);
        if (compilationFile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompilationFile result = compilationFileRepository.save(compilationFile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, compilationFile.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /compilation-files} : get all the compilationFiles.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of compilationFiles in body.
     */
    @GetMapping("/compilation-files")
    public List<CompilationFile> getAllCompilationFiles() {
        log.debug("REST request to get all CompilationFiles");
        return compilationFileRepository.findAll();
    }

    /**
     * {@code GET  /compilation-files/:id} : get the "id" compilationFile.
     *
     * @param id the id of the compilationFile to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the compilationFile, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/compilation-files/{id}")
    public ResponseEntity<CompilationFile> getCompilationFile(@PathVariable Long id) {
        log.debug("REST request to get CompilationFile : {}", id);
        Optional<CompilationFile> compilationFile = compilationFileRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(compilationFile);
    }

    /**
     * {@code DELETE  /compilation-files/:id} : delete the "id" compilationFile.
     *
     * @param id the id of the compilationFile to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/compilation-files/{id}")
    public ResponseEntity<Void> deleteCompilationFile(@PathVariable Long id) {
        log.debug("REST request to delete CompilationFile : {}", id);
        compilationFileRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
