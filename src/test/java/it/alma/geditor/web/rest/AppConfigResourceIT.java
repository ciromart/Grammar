package it.alma.geditor.web.rest;

import it.alma.geditor.GrammarEditorApp;
import it.alma.geditor.domain.AppConfig;
import it.alma.geditor.repository.AppConfigRepository;
import it.alma.geditor.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static it.alma.geditor.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AppConfigResource} REST controller.
 */
@SpringBootTest(classes = GrammarEditorApp.class)
public class AppConfigResourceIT {

    private static final Long DEFAULT_CRITICAL_WORDS_MAX_FILE_SIZE = 1L;
    private static final Long UPDATED_CRITICAL_WORDS_MAX_FILE_SIZE = 2L;
    private static final Long SMALLER_CRITICAL_WORDS_MAX_FILE_SIZE = 1L - 1L;

    private static final Long DEFAULT_CRITICAL_WORDS_MAX_WORDS = 1L;
    private static final Long UPDATED_CRITICAL_WORDS_MAX_WORDS = 2L;
    private static final Long SMALLER_CRITICAL_WORDS_MAX_WORDS = 1L - 1L;

    private static final Long DEFAULT_ADDITIONAL_CONTEXT_MAX_FILE_SIZE = 1L;
    private static final Long UPDATED_ADDITIONAL_CONTEXT_MAX_FILE_SIZE = 2L;
    private static final Long SMALLER_ADDITIONAL_CONTEXT_MAX_FILE_SIZE = 1L - 1L;

    private static final Long DEFAULT_ADDITIONAL_CONTEXT_MAX_FILE_WORDS = 1L;
    private static final Long UPDATED_ADDITIONAL_CONTEXT_MAX_FILE_WORDS = 2L;
    private static final Long SMALLER_ADDITIONAL_CONTEXT_MAX_FILE_WORDS = 1L - 1L;

    private static final Long DEFAULT_MIN_OCCURENCY_CONTEXT = 1L;
    private static final Long UPDATED_MIN_OCCURENCY_CONTEXT = 2L;
    private static final Long SMALLER_MIN_OCCURENCY_CONTEXT = 1L - 1L;

    private static final Long DEFAULT_WINDOWS_MAX_WORDS = 1L;
    private static final Long UPDATED_WINDOWS_MAX_WORDS = 2L;
    private static final Long SMALLER_WINDOWS_MAX_WORDS = 1L - 1L;

    @Autowired
    private AppConfigRepository appConfigRepository;

    @Mock
    private AppConfigRepository appConfigRepositoryMock;

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

    private MockMvc restAppConfigMockMvc;

    private AppConfig appConfig;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AppConfigResource appConfigResource = new AppConfigResource(appConfigRepository);
        this.restAppConfigMockMvc = MockMvcBuilders.standaloneSetup(appConfigResource)
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
    public static AppConfig createEntity(EntityManager em) {
        AppConfig appConfig = new AppConfig()
            .criticalWordsMaxFileSize(DEFAULT_CRITICAL_WORDS_MAX_FILE_SIZE)
            .criticalWordsMaxWords(DEFAULT_CRITICAL_WORDS_MAX_WORDS)
            .additionalContextMaxFileSize(DEFAULT_ADDITIONAL_CONTEXT_MAX_FILE_SIZE)
            .additionalContextMaxFileWords(DEFAULT_ADDITIONAL_CONTEXT_MAX_FILE_WORDS)
            .minOccurencyContext(DEFAULT_MIN_OCCURENCY_CONTEXT)
            .windowsMaxWords(DEFAULT_WINDOWS_MAX_WORDS);
        return appConfig;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppConfig createUpdatedEntity(EntityManager em) {
        AppConfig appConfig = new AppConfig()
            .criticalWordsMaxFileSize(UPDATED_CRITICAL_WORDS_MAX_FILE_SIZE)
            .criticalWordsMaxWords(UPDATED_CRITICAL_WORDS_MAX_WORDS)
            .additionalContextMaxFileSize(UPDATED_ADDITIONAL_CONTEXT_MAX_FILE_SIZE)
            .additionalContextMaxFileWords(UPDATED_ADDITIONAL_CONTEXT_MAX_FILE_WORDS)
            .minOccurencyContext(UPDATED_MIN_OCCURENCY_CONTEXT)
            .windowsMaxWords(UPDATED_WINDOWS_MAX_WORDS);
        return appConfig;
    }

    @BeforeEach
    public void initTest() {
        appConfig = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppConfig() throws Exception {
        int databaseSizeBeforeCreate = appConfigRepository.findAll().size();

        // Create the AppConfig
        restAppConfigMockMvc.perform(post("/api/app-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appConfig)))
            .andExpect(status().isCreated());

        // Validate the AppConfig in the database
        List<AppConfig> appConfigList = appConfigRepository.findAll();
        assertThat(appConfigList).hasSize(databaseSizeBeforeCreate + 1);
        AppConfig testAppConfig = appConfigList.get(appConfigList.size() - 1);
        assertThat(testAppConfig.getCriticalWordsMaxFileSize()).isEqualTo(DEFAULT_CRITICAL_WORDS_MAX_FILE_SIZE);
        assertThat(testAppConfig.getCriticalWordsMaxWords()).isEqualTo(DEFAULT_CRITICAL_WORDS_MAX_WORDS);
        assertThat(testAppConfig.getAdditionalContextMaxFileSize()).isEqualTo(DEFAULT_ADDITIONAL_CONTEXT_MAX_FILE_SIZE);
        assertThat(testAppConfig.getAdditionalContextMaxFileWords()).isEqualTo(DEFAULT_ADDITIONAL_CONTEXT_MAX_FILE_WORDS);
        assertThat(testAppConfig.getMinOccurencyContext()).isEqualTo(DEFAULT_MIN_OCCURENCY_CONTEXT);
        assertThat(testAppConfig.getWindowsMaxWords()).isEqualTo(DEFAULT_WINDOWS_MAX_WORDS);
    }

    @Test
    @Transactional
    public void createAppConfigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appConfigRepository.findAll().size();

        // Create the AppConfig with an existing ID
        appConfig.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppConfigMockMvc.perform(post("/api/app-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appConfig)))
            .andExpect(status().isBadRequest());

        // Validate the AppConfig in the database
        List<AppConfig> appConfigList = appConfigRepository.findAll();
        assertThat(appConfigList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAppConfigs() throws Exception {
        // Initialize the database
        appConfigRepository.saveAndFlush(appConfig);

        // Get all the appConfigList
        restAppConfigMockMvc.perform(get("/api/app-configs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].criticalWordsMaxFileSize").value(hasItem(DEFAULT_CRITICAL_WORDS_MAX_FILE_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].criticalWordsMaxWords").value(hasItem(DEFAULT_CRITICAL_WORDS_MAX_WORDS.intValue())))
            .andExpect(jsonPath("$.[*].additionalContextMaxFileSize").value(hasItem(DEFAULT_ADDITIONAL_CONTEXT_MAX_FILE_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].additionalContextMaxFileWords").value(hasItem(DEFAULT_ADDITIONAL_CONTEXT_MAX_FILE_WORDS.intValue())))
            .andExpect(jsonPath("$.[*].minOccurencyContext").value(hasItem(DEFAULT_MIN_OCCURENCY_CONTEXT.intValue())))
            .andExpect(jsonPath("$.[*].windowsMaxWords").value(hasItem(DEFAULT_WINDOWS_MAX_WORDS.intValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllAppConfigsWithEagerRelationshipsIsEnabled() throws Exception {
        AppConfigResource appConfigResource = new AppConfigResource(appConfigRepositoryMock);
        when(appConfigRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restAppConfigMockMvc = MockMvcBuilders.standaloneSetup(appConfigResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAppConfigMockMvc.perform(get("/api/app-configs?eagerload=true"))
        .andExpect(status().isOk());

        verify(appConfigRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllAppConfigsWithEagerRelationshipsIsNotEnabled() throws Exception {
        AppConfigResource appConfigResource = new AppConfigResource(appConfigRepositoryMock);
            when(appConfigRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restAppConfigMockMvc = MockMvcBuilders.standaloneSetup(appConfigResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAppConfigMockMvc.perform(get("/api/app-configs?eagerload=true"))
        .andExpect(status().isOk());

            verify(appConfigRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getAppConfig() throws Exception {
        // Initialize the database
        appConfigRepository.saveAndFlush(appConfig);

        // Get the appConfig
        restAppConfigMockMvc.perform(get("/api/app-configs/{id}", appConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(appConfig.getId().intValue()))
            .andExpect(jsonPath("$.criticalWordsMaxFileSize").value(DEFAULT_CRITICAL_WORDS_MAX_FILE_SIZE.intValue()))
            .andExpect(jsonPath("$.criticalWordsMaxWords").value(DEFAULT_CRITICAL_WORDS_MAX_WORDS.intValue()))
            .andExpect(jsonPath("$.additionalContextMaxFileSize").value(DEFAULT_ADDITIONAL_CONTEXT_MAX_FILE_SIZE.intValue()))
            .andExpect(jsonPath("$.additionalContextMaxFileWords").value(DEFAULT_ADDITIONAL_CONTEXT_MAX_FILE_WORDS.intValue()))
            .andExpect(jsonPath("$.minOccurencyContext").value(DEFAULT_MIN_OCCURENCY_CONTEXT.intValue()))
            .andExpect(jsonPath("$.windowsMaxWords").value(DEFAULT_WINDOWS_MAX_WORDS.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAppConfig() throws Exception {
        // Get the appConfig
        restAppConfigMockMvc.perform(get("/api/app-configs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppConfig() throws Exception {
        // Initialize the database
        appConfigRepository.saveAndFlush(appConfig);

        int databaseSizeBeforeUpdate = appConfigRepository.findAll().size();

        // Update the appConfig
        AppConfig updatedAppConfig = appConfigRepository.findById(appConfig.getId()).get();
        // Disconnect from session so that the updates on updatedAppConfig are not directly saved in db
        em.detach(updatedAppConfig);
        updatedAppConfig
            .criticalWordsMaxFileSize(UPDATED_CRITICAL_WORDS_MAX_FILE_SIZE)
            .criticalWordsMaxWords(UPDATED_CRITICAL_WORDS_MAX_WORDS)
            .additionalContextMaxFileSize(UPDATED_ADDITIONAL_CONTEXT_MAX_FILE_SIZE)
            .additionalContextMaxFileWords(UPDATED_ADDITIONAL_CONTEXT_MAX_FILE_WORDS)
            .minOccurencyContext(UPDATED_MIN_OCCURENCY_CONTEXT)
            .windowsMaxWords(UPDATED_WINDOWS_MAX_WORDS);

        restAppConfigMockMvc.perform(put("/api/app-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAppConfig)))
            .andExpect(status().isOk());

        // Validate the AppConfig in the database
        List<AppConfig> appConfigList = appConfigRepository.findAll();
        assertThat(appConfigList).hasSize(databaseSizeBeforeUpdate);
        AppConfig testAppConfig = appConfigList.get(appConfigList.size() - 1);
        assertThat(testAppConfig.getCriticalWordsMaxFileSize()).isEqualTo(UPDATED_CRITICAL_WORDS_MAX_FILE_SIZE);
        assertThat(testAppConfig.getCriticalWordsMaxWords()).isEqualTo(UPDATED_CRITICAL_WORDS_MAX_WORDS);
        assertThat(testAppConfig.getAdditionalContextMaxFileSize()).isEqualTo(UPDATED_ADDITIONAL_CONTEXT_MAX_FILE_SIZE);
        assertThat(testAppConfig.getAdditionalContextMaxFileWords()).isEqualTo(UPDATED_ADDITIONAL_CONTEXT_MAX_FILE_WORDS);
        assertThat(testAppConfig.getMinOccurencyContext()).isEqualTo(UPDATED_MIN_OCCURENCY_CONTEXT);
        assertThat(testAppConfig.getWindowsMaxWords()).isEqualTo(UPDATED_WINDOWS_MAX_WORDS);
    }

    @Test
    @Transactional
    public void updateNonExistingAppConfig() throws Exception {
        int databaseSizeBeforeUpdate = appConfigRepository.findAll().size();

        // Create the AppConfig

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppConfigMockMvc.perform(put("/api/app-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appConfig)))
            .andExpect(status().isBadRequest());

        // Validate the AppConfig in the database
        List<AppConfig> appConfigList = appConfigRepository.findAll();
        assertThat(appConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAppConfig() throws Exception {
        // Initialize the database
        appConfigRepository.saveAndFlush(appConfig);

        int databaseSizeBeforeDelete = appConfigRepository.findAll().size();

        // Delete the appConfig
        restAppConfigMockMvc.perform(delete("/api/app-configs/{id}", appConfig.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppConfig> appConfigList = appConfigRepository.findAll();
        assertThat(appConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppConfig.class);
        AppConfig appConfig1 = new AppConfig();
        appConfig1.setId(1L);
        AppConfig appConfig2 = new AppConfig();
        appConfig2.setId(appConfig1.getId());
        assertThat(appConfig1).isEqualTo(appConfig2);
        appConfig2.setId(2L);
        assertThat(appConfig1).isNotEqualTo(appConfig2);
        appConfig1.setId(null);
        assertThat(appConfig1).isNotEqualTo(appConfig2);
    }
}
