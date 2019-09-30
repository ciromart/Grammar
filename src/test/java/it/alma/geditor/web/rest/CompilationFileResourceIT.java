package it.alma.geditor.web.rest;

import static it.alma.geditor.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.persistence.EntityManager;

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

import it.alma.geditor.GrammarEditorApp;
import it.alma.geditor.domain.CompilationFile;
import it.alma.geditor.repository.CompilationFileRepository;
import it.alma.geditor.web.rest.errors.ExceptionTranslator;

/**
 * Integration tests for the {@link CompilationFileResource} REST controller.
 */
@SpringBootTest(classes = GrammarEditorApp.class)
public class CompilationFileResourceIT {

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    @Autowired
    private CompilationFileRepository compilationFileRepository;

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

    private MockMvc restCompilationFileMockMvc;

    private CompilationFile compilationFile;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompilationFileResource compilationFileResource = new CompilationFileResource(compilationFileRepository);
        this.restCompilationFileMockMvc = MockMvcBuilders.standaloneSetup(compilationFileResource)
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
    public static CompilationFile createEntity(EntityManager em) {
        CompilationFile compilationFile = new CompilationFile();
        compilationFile.setPath(DEFAULT_PATH);
        return compilationFile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompilationFile createUpdatedEntity(EntityManager em) {
        CompilationFile compilationFile = new CompilationFile();
        compilationFile.setPath(DEFAULT_PATH);
        return compilationFile;
    }

    @BeforeEach
    public void initTest() {
        compilationFile = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompilationFile() throws Exception {
        int databaseSizeBeforeCreate = compilationFileRepository.findAll().size();

        // Create the CompilationFile
        restCompilationFileMockMvc.perform(post("/api/compilation-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compilationFile)))
            .andExpect(status().isCreated());

        // Validate the CompilationFile in the database
        List<CompilationFile> compilationFileList = compilationFileRepository.findAll();
        assertThat(compilationFileList).hasSize(databaseSizeBeforeCreate + 1);
        CompilationFile testCompilationFile = compilationFileList.get(compilationFileList.size() - 1);
        assertThat(testCompilationFile.getPath()).isEqualTo(DEFAULT_PATH);
    }

    @Test
    @Transactional
    public void createCompilationFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = compilationFileRepository.findAll().size();

        // Create the CompilationFile with an existing ID
        compilationFile.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompilationFileMockMvc.perform(post("/api/compilation-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compilationFile)))
            .andExpect(status().isBadRequest());

        // Validate the CompilationFile in the database
        List<CompilationFile> compilationFileList = compilationFileRepository.findAll();
        assertThat(compilationFileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCompilationFiles() throws Exception {
        // Initialize the database
        compilationFileRepository.saveAndFlush(compilationFile);

        // Get all the compilationFileList
        restCompilationFileMockMvc.perform(get("/api/compilation-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compilationFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH.toString())));
    }
    
    @Test
    @Transactional
    public void getCompilationFile() throws Exception {
        // Initialize the database
        compilationFileRepository.saveAndFlush(compilationFile);

        // Get the compilationFile
        restCompilationFileMockMvc.perform(get("/api/compilation-files/{id}", compilationFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(compilationFile.getId().intValue()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompilationFile() throws Exception {
        // Get the compilationFile
        restCompilationFileMockMvc.perform(get("/api/compilation-files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompilationFile() throws Exception {
        // Initialize the database
        compilationFileRepository.saveAndFlush(compilationFile);

        int databaseSizeBeforeUpdate = compilationFileRepository.findAll().size();

        // Update the compilationFile
        CompilationFile updatedCompilationFile = compilationFileRepository.findById(compilationFile.getId()).get();
        // Disconnect from session so that the updates on updatedCompilationFile are not directly saved in db
        em.detach(updatedCompilationFile);
        updatedCompilationFile.setPath(UPDATED_PATH);

        restCompilationFileMockMvc.perform(put("/api/compilation-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompilationFile)))
            .andExpect(status().isOk());

        // Validate the CompilationFile in the database
        List<CompilationFile> compilationFileList = compilationFileRepository.findAll();
        assertThat(compilationFileList).hasSize(databaseSizeBeforeUpdate);
        CompilationFile testCompilationFile = compilationFileList.get(compilationFileList.size() - 1);
        assertThat(testCompilationFile.getPath()).isEqualTo(UPDATED_PATH);
    }

    @Test
    @Transactional
    public void updateNonExistingCompilationFile() throws Exception {
        int databaseSizeBeforeUpdate = compilationFileRepository.findAll().size();

        // Create the CompilationFile

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompilationFileMockMvc.perform(put("/api/compilation-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compilationFile)))
            .andExpect(status().isBadRequest());

        // Validate the CompilationFile in the database
        List<CompilationFile> compilationFileList = compilationFileRepository.findAll();
        assertThat(compilationFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompilationFile() throws Exception {
        // Initialize the database
        compilationFileRepository.saveAndFlush(compilationFile);

        int databaseSizeBeforeDelete = compilationFileRepository.findAll().size();

        // Delete the compilationFile
        restCompilationFileMockMvc.perform(delete("/api/compilation-files/{id}", compilationFile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompilationFile> compilationFileList = compilationFileRepository.findAll();
        assertThat(compilationFileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompilationFile.class);
        CompilationFile compilationFile1 = new CompilationFile();
        compilationFile1.setId(1L);
        CompilationFile compilationFile2 = new CompilationFile();
        compilationFile2.setId(compilationFile1.getId());
        assertThat(compilationFile1).isEqualTo(compilationFile2);
        compilationFile2.setId(2L);
        assertThat(compilationFile1).isNotEqualTo(compilationFile2);
        compilationFile1.setId(null);
        assertThat(compilationFile1).isNotEqualTo(compilationFile2);
    }
}
