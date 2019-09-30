package it.alma.geditor.web.rest;

import it.alma.geditor.domain.AppConfig;
import it.alma.geditor.repository.AppConfigRepository;
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
 * REST controller for managing {@link it.alma.geditor.domain.AppConfig}.
 */
@RestController
@RequestMapping("/api")
public class AppConfigResource {

    private final Logger log = LoggerFactory.getLogger(AppConfigResource.class);

    private static final String ENTITY_NAME = "appConfig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppConfigRepository appConfigRepository;

    public AppConfigResource(AppConfigRepository appConfigRepository) {
        this.appConfigRepository = appConfigRepository;
    }

    /**
     * {@code POST  /app-configs} : Create a new appConfig.
     *
     * @param appConfig the appConfig to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appConfig, or with status {@code 400 (Bad Request)} if the appConfig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/app-configs")
    public ResponseEntity<AppConfig> createAppConfig(@RequestBody AppConfig appConfig) throws URISyntaxException {
        log.debug("REST request to save AppConfig : {}", appConfig);
        if (appConfig.getId() != null) {
            throw new BadRequestAlertException("A new appConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppConfig result = appConfigRepository.save(appConfig);
        return ResponseEntity.created(new URI("/api/app-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /app-configs} : Updates an existing appConfig.
     *
     * @param appConfig the appConfig to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appConfig,
     * or with status {@code 400 (Bad Request)} if the appConfig is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appConfig couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/app-configs")
    public ResponseEntity<AppConfig> updateAppConfig(@RequestBody AppConfig appConfig) throws URISyntaxException {
        log.debug("REST request to update AppConfig : {}", appConfig);
        if (appConfig.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AppConfig result = appConfigRepository.save(appConfig);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appConfig.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /app-configs} : get all the appConfigs.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appConfigs in body.
     */
    @GetMapping("/app-configs")
    public List<AppConfig> getAllAppConfigs() {
        log.debug("REST request to get all AppConfigs");
        return appConfigRepository.findAll();
    }

    /**
     * {@code GET  /app-configs/:id} : get the "id" appConfig.
     *
     * @param id the id of the appConfig to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appConfig, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/app-configs/{id}")
    public ResponseEntity<AppConfig> getAppConfig(@PathVariable Long id) {
        log.debug("REST request to get AppConfig : {}", id);
        Optional<AppConfig> appConfig = appConfigRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(appConfig);
    }

    /**
     * {@code DELETE  /app-configs/:id} : delete the "id" appConfig.
     *
     * @param id the id of the appConfig to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/app-configs/{id}")
    public ResponseEntity<Void> deleteAppConfig(@PathVariable Long id) {
        log.debug("REST request to delete AppConfig : {}", id);
        appConfigRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
