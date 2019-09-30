package it.alma.geditor.web.rest;

import it.alma.geditor.GrammarEditorApp;
import it.alma.geditor.domain.LmTemplate;
import it.alma.geditor.repository.LmTemplateRepository;
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
 * Integration tests for the {@link LmTemplateResource} REST controller.
 */
@SpringBootTest(classes = GrammarEditorApp.class)
public class LmTemplateResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_LANG_CODE = 1L;
    private static final Long UPDATED_LANG_CODE = 2L;

    private static final Long DEFAULT_COUNTRY_CODE = 1L;
    private static final Long UPDATED_COUNTRY_CODE = 2L;

    private static final Long DEFAULT_LM_STANDARD_CODE = 1L;
    private static final Long UPDATED_LM_STANDARD_CODE = 2L;

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    private static final Instant DEFAULT_INSERT_TS = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INSERT_TS = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_UPDATE_TS = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATE_TS = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;

    @Autowired
    private LmTemplateRepository lmTemplateRepository;

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

    private MockMvc restLmTemplateMockMvc;

    private LmTemplate lmTemplate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LmTemplateResource lmTemplateResource = new LmTemplateResource(lmTemplateRepository);
        this.restLmTemplateMockMvc = MockMvcBuilders.standaloneSetup(lmTemplateResource)
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
    public static LmTemplate createEntity(EntityManager em) {
        LmTemplate lmTemplate = new LmTemplate();
        lmTemplate.setName(DEFAULT_NAME);
        lmTemplate.setLangCode(DEFAULT_LANG_CODE);
        lmTemplate.setCountryCode(DEFAULT_COUNTRY_CODE);
        lmTemplate.setLmStandardCode(DEFAULT_LM_STANDARD_CODE);
        lmTemplate.setPath(DEFAULT_PATH);
        lmTemplate.setInsertTs(DEFAULT_INSERT_TS);
        lmTemplate.setLastUpdateTs(DEFAULT_LAST_UPDATE_TS);
        lmTemplate.setActivated(DEFAULT_ACTIVATED);
        return lmTemplate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LmTemplate createUpdatedEntity(EntityManager em) {
        LmTemplate lmTemplate = new LmTemplate();
        lmTemplate.setName(DEFAULT_NAME);
        lmTemplate.setLangCode(DEFAULT_LANG_CODE);
        lmTemplate.setCountryCode(DEFAULT_COUNTRY_CODE);
        lmTemplate.setLmStandardCode(DEFAULT_LM_STANDARD_CODE);
        lmTemplate.setPath(DEFAULT_PATH);
        lmTemplate.setInsertTs(DEFAULT_INSERT_TS);
        lmTemplate.setLastUpdateTs(DEFAULT_LAST_UPDATE_TS);
        lmTemplate.setActivated(DEFAULT_ACTIVATED);
        return lmTemplate;
    }

    @BeforeEach
    public void initTest() {
        lmTemplate = createEntity(em);
    }

    @Test
    @Transactional
    public void createLmTemplate() throws Exception {
        int databaseSizeBeforeCreate = lmTemplateRepository.findAll().size();

        // Create the LmTemplate
        restLmTemplateMockMvc.perform(post("/api/lm-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lmTemplate)))
            .andExpect(status().isCreated());

        // Validate the LmTemplate in the database
        List<LmTemplate> lmTemplateList = lmTemplateRepository.findAll();
        assertThat(lmTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        LmTemplate testLmTemplate = lmTemplateList.get(lmTemplateList.size() - 1);
        assertThat(testLmTemplate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLmTemplate.getLangCode()).isEqualTo(DEFAULT_LANG_CODE);
        assertThat(testLmTemplate.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testLmTemplate.getLmStandardCode()).isEqualTo(DEFAULT_LM_STANDARD_CODE);
        assertThat(testLmTemplate.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testLmTemplate.getInsertTs()).isEqualTo(DEFAULT_INSERT_TS);
        assertThat(testLmTemplate.getLastUpdateTs()).isEqualTo(DEFAULT_LAST_UPDATE_TS);
        assertThat(testLmTemplate.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
    }

    @Test
    @Transactional
    public void createLmTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lmTemplateRepository.findAll().size();

        // Create the LmTemplate with an existing ID
        lmTemplate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLmTemplateMockMvc.perform(post("/api/lm-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lmTemplate)))
            .andExpect(status().isBadRequest());

        // Validate the LmTemplate in the database
        List<LmTemplate> lmTemplateList = lmTemplateRepository.findAll();
        assertThat(lmTemplateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLmTemplates() throws Exception {
        // Initialize the database
        lmTemplateRepository.saveAndFlush(lmTemplate);

        // Get all the lmTemplateList
        restLmTemplateMockMvc.perform(get("/api/lm-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lmTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].langCode").value(hasItem(DEFAULT_LANG_CODE.intValue())))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE.intValue())))
            .andExpect(jsonPath("$.[*].lmStandardCode").value(hasItem(DEFAULT_LM_STANDARD_CODE.intValue())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH.toString())))
            .andExpect(jsonPath("$.[*].insertTs").value(hasItem(DEFAULT_INSERT_TS.toString())))
            .andExpect(jsonPath("$.[*].lastUpdateTs").value(hasItem(DEFAULT_LAST_UPDATE_TS.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getLmTemplate() throws Exception {
        // Initialize the database
        lmTemplateRepository.saveAndFlush(lmTemplate);

        // Get the lmTemplate
        restLmTemplateMockMvc.perform(get("/api/lm-templates/{id}", lmTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lmTemplate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.langCode").value(DEFAULT_LANG_CODE.intValue()))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE.intValue()))
            .andExpect(jsonPath("$.lmStandardCode").value(DEFAULT_LM_STANDARD_CODE.intValue()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH.toString()))
            .andExpect(jsonPath("$.insertTs").value(DEFAULT_INSERT_TS.toString()))
            .andExpect(jsonPath("$.lastUpdateTs").value(DEFAULT_LAST_UPDATE_TS.toString()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLmTemplate() throws Exception {
        // Get the lmTemplate
        restLmTemplateMockMvc.perform(get("/api/lm-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLmTemplate() throws Exception {
        // Initialize the database
        lmTemplateRepository.saveAndFlush(lmTemplate);

        int databaseSizeBeforeUpdate = lmTemplateRepository.findAll().size();

        // Update the lmTemplate
        LmTemplate updatedLmTemplate = lmTemplateRepository.findById(lmTemplate.getId()).get();
        // Disconnect from session so that the updates on updatedLmTemplate are not directly saved in db
        em.detach(updatedLmTemplate);
        updatedLmTemplate.setName(DEFAULT_NAME);
        updatedLmTemplate.setLangCode(DEFAULT_LANG_CODE);
        updatedLmTemplate.setCountryCode(DEFAULT_COUNTRY_CODE);
        updatedLmTemplate.setLmStandardCode(DEFAULT_LM_STANDARD_CODE);
        updatedLmTemplate.setPath(DEFAULT_PATH);
        updatedLmTemplate.setInsertTs(DEFAULT_INSERT_TS);
        updatedLmTemplate.setLastUpdateTs(DEFAULT_LAST_UPDATE_TS);
        updatedLmTemplate.setActivated(DEFAULT_ACTIVATED);

        restLmTemplateMockMvc.perform(put("/api/lm-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLmTemplate)))
            .andExpect(status().isOk());

        // Validate the LmTemplate in the database
        List<LmTemplate> lmTemplateList = lmTemplateRepository.findAll();
        assertThat(lmTemplateList).hasSize(databaseSizeBeforeUpdate);
        LmTemplate testLmTemplate = lmTemplateList.get(lmTemplateList.size() - 1);
        assertThat(testLmTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLmTemplate.getLangCode()).isEqualTo(UPDATED_LANG_CODE);
        assertThat(testLmTemplate.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testLmTemplate.getLmStandardCode()).isEqualTo(UPDATED_LM_STANDARD_CODE);
        assertThat(testLmTemplate.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testLmTemplate.getInsertTs()).isEqualTo(UPDATED_INSERT_TS);
        assertThat(testLmTemplate.getLastUpdateTs()).isEqualTo(UPDATED_LAST_UPDATE_TS);
        assertThat(testLmTemplate.getActivated()).isEqualTo(UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    public void updateNonExistingLmTemplate() throws Exception {
        int databaseSizeBeforeUpdate = lmTemplateRepository.findAll().size();

        // Create the LmTemplate

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLmTemplateMockMvc.perform(put("/api/lm-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lmTemplate)))
            .andExpect(status().isBadRequest());

        // Validate the LmTemplate in the database
        List<LmTemplate> lmTemplateList = lmTemplateRepository.findAll();
        assertThat(lmTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLmTemplate() throws Exception {
        // Initialize the database
        lmTemplateRepository.saveAndFlush(lmTemplate);

        int databaseSizeBeforeDelete = lmTemplateRepository.findAll().size();

        // Delete the lmTemplate
        restLmTemplateMockMvc.perform(delete("/api/lm-templates/{id}", lmTemplate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LmTemplate> lmTemplateList = lmTemplateRepository.findAll();
        assertThat(lmTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LmTemplate.class);
        LmTemplate lmTemplate1 = new LmTemplate();
        lmTemplate1.setId(1L);
        LmTemplate lmTemplate2 = new LmTemplate();
        lmTemplate2.setId(lmTemplate1.getId());
        assertThat(lmTemplate1).isEqualTo(lmTemplate2);
        lmTemplate2.setId(2L);
        assertThat(lmTemplate1).isNotEqualTo(lmTemplate2);
        lmTemplate1.setId(null);
        assertThat(lmTemplate1).isNotEqualTo(lmTemplate2);
    }
}
