package it.alma.geditor.web.rest;

import it.alma.geditor.GrammarEditorApp;
import it.alma.geditor.domain.CompilationLog;
import it.alma.geditor.repository.CompilationLogRepository;
import it.alma.geditor.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static it.alma.geditor.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CompilationLogResource} REST controller.
 */
@SpringBootTest(classes = GrammarEditorApp.class)
public class CompilationLogResourceIT {

    private static final Instant DEFAULT_INSERT_TS = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INSERT_TS = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_INSERT_TS = Instant.ofEpochMilli(-1L);

    private static final Instant DEFAULT_LAST_UPDAT_TS = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDAT_TS = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_LAST_UPDAT_TS = Instant.ofEpochMilli(-1L);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_RPK_LINK = "AAAAAAAAAA";
    private static final String UPDATED_RPK_LINK = "BBBBBBBBBB";

    @Autowired
    private CompilationLogRepository compilationLogRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCompilationLogMockMvc;

    private CompilationLog compilationLog;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompilationLogResource compilationLogResource = new CompilationLogResource(compilationLogRepository);
        this.restCompilationLogMockMvc = MockMvcBuilders.standaloneSetup(compilationLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompilationLog createEntity(EntityManager em) {
        CompilationLog compilationLog = new CompilationLog()
            .insertTs(DEFAULT_INSERT_TS)
            .lastUpdatTs(DEFAULT_LAST_UPDAT_TS)
            .status(DEFAULT_STATUS)
            .rpkLink(DEFAULT_RPK_LINK);
        return compilationLog;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompilationLog createUpdatedEntity(EntityManager em) {
        CompilationLog compilationLog = new CompilationLog()
            .insertTs(UPDATED_INSERT_TS)
            .lastUpdatTs(UPDATED_LAST_UPDAT_TS)
            .status(UPDATED_STATUS)
            .rpkLink(UPDATED_RPK_LINK);
        return compilationLog;
    }

    @BeforeEach
    public void initTest() {
        compilationLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompilationLog() throws Exception {
        int databaseSizeBeforeCreate = compilationLogRepository.findAll().size();

        // Create the CompilationLog
        restCompilationLogMockMvc.perform(post("/api/compilation-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compilationLog)))
            .andExpect(status().isCreated());

        // Validate the CompilationLog in the database
        List<CompilationLog> compilationLogList = compilationLogRepository.findAll();
        assertThat(compilationLogList).hasSize(databaseSizeBeforeCreate + 1);
        CompilationLog testCompilationLog = compilationLogList.get(compilationLogList.size() - 1);
        assertThat(testCompilationLog.getInsertTs()).isEqualTo(DEFAULT_INSERT_TS);
        assertThat(testCompilationLog.getLastUpdatTs()).isEqualTo(DEFAULT_LAST_UPDAT_TS);
        assertThat(testCompilationLog.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCompilationLog.getRpkLink()).isEqualTo(DEFAULT_RPK_LINK);
    }

    @Test
    @Transactional
    public void createCompilationLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = compilationLogRepository.findAll().size();

        // Create the CompilationLog with an existing ID
        compilationLog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompilationLogMockMvc.perform(post("/api/compilation-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compilationLog)))
            .andExpect(status().isBadRequest());

        // Validate the CompilationLog in the database
        List<CompilationLog> compilationLogList = compilationLogRepository.findAll();
        assertThat(compilationLogList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCompilationLogs() throws Exception {
        // Initialize the database
        compilationLogRepository.saveAndFlush(compilationLog);

        // Get all the compilationLogList
        restCompilationLogMockMvc.perform(get("/api/compilation-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compilationLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].insertTs").value(hasItem(DEFAULT_INSERT_TS.toString())))
            .andExpect(jsonPath("$.[*].lastUpdatTs").value(hasItem(DEFAULT_LAST_UPDAT_TS.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].rpkLink").value(hasItem(DEFAULT_RPK_LINK.toString())));
    }
    
    @Test
    @Transactional
    public void getCompilationLog() throws Exception {
        // Initialize the database
        compilationLogRepository.saveAndFlush(compilationLog);

        // Get the compilationLog
        restCompilationLogMockMvc.perform(get("/api/compilation-logs/{id}", compilationLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(compilationLog.getId().intValue()))
            .andExpect(jsonPath("$.insertTs").value(DEFAULT_INSERT_TS.toString()))
            .andExpect(jsonPath("$.lastUpdatTs").value(DEFAULT_LAST_UPDAT_TS.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.rpkLink").value(DEFAULT_RPK_LINK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompilationLog() throws Exception {
        // Get the compilationLog
        restCompilationLogMockMvc.perform(get("/api/compilation-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompilationLog() throws Exception {
        // Initialize the database
        compilationLogRepository.saveAndFlush(compilationLog);

        int databaseSizeBeforeUpdate = compilationLogRepository.findAll().size();

        // Update the compilationLog
        CompilationLog updatedCompilationLog = compilationLogRepository.findById(compilationLog.getId()).get();
        // Disconnect from session so that the updates on updatedCompilationLog are not directly saved in db
        em.detach(updatedCompilationLog);
        updatedCompilationLog
            .insertTs(UPDATED_INSERT_TS)
            .lastUpdatTs(UPDATED_LAST_UPDAT_TS)
            .status(UPDATED_STATUS)
            .rpkLink(UPDATED_RPK_LINK);

        restCompilationLogMockMvc.perform(put("/api/compilation-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompilationLog)))
            .andExpect(status().isOk());

        // Validate the CompilationLog in the database
        List<CompilationLog> compilationLogList = compilationLogRepository.findAll();
        assertThat(compilationLogList).hasSize(databaseSizeBeforeUpdate);
        CompilationLog testCompilationLog = compilationLogList.get(compilationLogList.size() - 1);
        assertThat(testCompilationLog.getInsertTs()).isEqualTo(UPDATED_INSERT_TS);
        assertThat(testCompilationLog.getLastUpdatTs()).isEqualTo(UPDATED_LAST_UPDAT_TS);
        assertThat(testCompilationLog.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCompilationLog.getRpkLink()).isEqualTo(UPDATED_RPK_LINK);
    }

    @Test
    @Transactional
    public void updateNonExistingCompilationLog() throws Exception {
        int databaseSizeBeforeUpdate = compilationLogRepository.findAll().size();

        // Create the CompilationLog

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompilationLogMockMvc.perform(put("/api/compilation-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compilationLog)))
            .andExpect(status().isBadRequest());

        // Validate the CompilationLog in the database
        List<CompilationLog> compilationLogList = compilationLogRepository.findAll();
        assertThat(compilationLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompilationLog() throws Exception {
        // Initialize the database
        compilationLogRepository.saveAndFlush(compilationLog);

        int databaseSizeBeforeDelete = compilationLogRepository.findAll().size();

        // Delete the compilationLog
        restCompilationLogMockMvc.perform(delete("/api/compilation-logs/{id}", compilationLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompilationLog> compilationLogList = compilationLogRepository.findAll();
        assertThat(compilationLogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompilationLog.class);
        CompilationLog compilationLog1 = new CompilationLog();
        compilationLog1.setId(1L);
        CompilationLog compilationLog2 = new CompilationLog();
        compilationLog2.setId(compilationLog1.getId());
        assertThat(compilationLog1).isEqualTo(compilationLog2);
        compilationLog2.setId(2L);
        assertThat(compilationLog1).isNotEqualTo(compilationLog2);
        compilationLog1.setId(null);
        assertThat(compilationLog1).isNotEqualTo(compilationLog2);
    }
}
