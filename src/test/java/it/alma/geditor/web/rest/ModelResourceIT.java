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

import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
import it.alma.geditor.domain.Model;
import it.alma.geditor.repository.ModelRepository;
import it.alma.geditor.web.rest.errors.ExceptionTranslator;

/**
 * Integration tests for the {@link ModelResource} REST controller.
 */
@SpringBootTest(classes = GrammarEditorApp.class)
public class ModelResourceIT {

	private static final String DEFAULT_NAME = "AAAAAAAAAA";
	private static final String UPDATED_NAME = "BBBBBBBBBB";

	private static final String DEFAULT_MAIL_NETWORK_NAME = "AAAAAAAAAA";
	private static final String UPDATED_MAIL_NETWORK_NAME = "BBBBBBBBBB";

	private static final Instant DEFAULT_INSERT_TS = Instant.ofEpochMilli(0L);
	private static final Instant UPDATED_INSERT_TS = Instant.now().truncatedTo(ChronoUnit.MILLIS);

	private static final Instant DEFAULT_LAST_UPDATE_TS = Instant.ofEpochMilli(0L);
	private static final Instant UPDATED_LAST_UPDATE_TS = Instant.now().truncatedTo(ChronoUnit.MILLIS);

	private static final Boolean DEFAULT_ACTIVATED = false;
	private static final Boolean UPDATED_ACTIVATED = true;

	@Autowired
	private ModelRepository modelRepository;

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

	private MockMvc restModelMockMvc;

	private Model model;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		final ModelResource modelResource = new ModelResource(modelRepository);
		this.restModelMockMvc = MockMvcBuilders.standaloneSetup(modelResource)
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
	public static Model createEntity(EntityManager em) {
		Model model = new Model();
		model.setName(DEFAULT_NAME);
		model.setMailNetworkName(DEFAULT_MAIL_NETWORK_NAME);
		model.setInsertTs(DEFAULT_INSERT_TS);
		model.setLastUpdateTs(DEFAULT_LAST_UPDATE_TS);
		model.setActivated(DEFAULT_ACTIVATED);
		return model;
	}
	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it,
	 * if they test an entity which requires the current entity.
	 */
	public static Model createUpdatedEntity(EntityManager em) {
		Model model = new Model();
		model.setName(DEFAULT_NAME);
		model.setMailNetworkName(DEFAULT_MAIL_NETWORK_NAME);
		model.setInsertTs(DEFAULT_INSERT_TS);
		model.setLastUpdateTs(DEFAULT_LAST_UPDATE_TS);
		model.setActivated(DEFAULT_ACTIVATED);
		return model;
	}

	@BeforeEach
	public void initTest() {
		model = createEntity(em);
	}

	@Test
	@Transactional
	public void createModel() throws Exception {
		int databaseSizeBeforeCreate = modelRepository.findAll().size();

		// Create the Model
		restModelMockMvc.perform(post("/api/models")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(model)))
		.andExpect(status().isCreated());

		// Validate the Model in the database
		List<Model> modelList = modelRepository.findAll();
		assertThat(modelList).hasSize(databaseSizeBeforeCreate + 1);
		Model testModel = modelList.get(modelList.size() - 1);
		assertThat(testModel.getName()).isEqualTo(DEFAULT_NAME);
		assertThat(testModel.getMailNetworkName()).isEqualTo(DEFAULT_MAIL_NETWORK_NAME);
		assertThat(testModel.getInsertTs()).isEqualTo(DEFAULT_INSERT_TS);
		assertThat(testModel.getLastUpdateTs()).isEqualTo(DEFAULT_LAST_UPDATE_TS);
		assertThat(testModel.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
	}

	@Test
	@Transactional
	public void createModelWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = modelRepository.findAll().size();

		// Create the Model with an existing ID
		model.setId(1L);

		// An entity with an existing ID cannot be created, so this API call must fail
		restModelMockMvc.perform(post("/api/models")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(model)))
		.andExpect(status().isBadRequest());

		// Validate the Model in the database
		List<Model> modelList = modelRepository.findAll();
		assertThat(modelList).hasSize(databaseSizeBeforeCreate);
	}


	@Test
	@Transactional
	public void getAllModels() throws Exception {
		// Initialize the database
		modelRepository.saveAndFlush(model);

		// Get all the modelList
		restModelMockMvc.perform(get("/api/models?sort=id,desc"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(jsonPath("$.[*].id").value(hasItem(model.getId().intValue())))
		.andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
		.andExpect(jsonPath("$.[*].mailNetworkName").value(hasItem(DEFAULT_MAIL_NETWORK_NAME.toString())))
		.andExpect(jsonPath("$.[*].insertTs").value(hasItem(DEFAULT_INSERT_TS.toString())))
		.andExpect(jsonPath("$.[*].lastUpdateTs").value(hasItem(DEFAULT_LAST_UPDATE_TS.toString())))
		.andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())));
	}

	@Test
	@Transactional
	public void getModel() throws Exception {
		// Initialize the database
		modelRepository.saveAndFlush(model);

		// Get the model
		restModelMockMvc.perform(get("/api/models/{id}", model.getId()))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(jsonPath("$.id").value(model.getId().intValue()))
		.andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
		.andExpect(jsonPath("$.mailNetworkName").value(DEFAULT_MAIL_NETWORK_NAME.toString()))
		.andExpect(jsonPath("$.insertTs").value(DEFAULT_INSERT_TS.toString()))
		.andExpect(jsonPath("$.lastUpdateTs").value(DEFAULT_LAST_UPDATE_TS.toString()))
		.andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()));
	}

	@Test
	@Transactional
	public void getNonExistingModel() throws Exception {
		// Get the model
		restModelMockMvc.perform(get("/api/models/{id}", Long.MAX_VALUE))
		.andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateModel() throws Exception {
		// Initialize the database
		modelRepository.saveAndFlush(model);

		int databaseSizeBeforeUpdate = modelRepository.findAll().size();

		// Update the model
		Model updatedModel = modelRepository.findById(model.getId()).get();
		// Disconnect from session so that the updates on updatedModel are not directly saved in db
		em.detach(updatedModel);
		updatedModel.setName(DEFAULT_NAME);
		updatedModel.setMailNetworkName(DEFAULT_MAIL_NETWORK_NAME);
		updatedModel.setInsertTs(DEFAULT_INSERT_TS);
		updatedModel.setLastUpdateTs(DEFAULT_LAST_UPDATE_TS);
		updatedModel.setActivated(DEFAULT_ACTIVATED);

		restModelMockMvc.perform(put("/api/models")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(updatedModel)))
		.andExpect(status().isOk());

		// Validate the Model in the database
		List<Model> modelList = modelRepository.findAll();
		assertThat(modelList).hasSize(databaseSizeBeforeUpdate);
		Model testModel = modelList.get(modelList.size() - 1);
		assertThat(testModel.getName()).isEqualTo(UPDATED_NAME);
		assertThat(testModel.getMailNetworkName()).isEqualTo(UPDATED_MAIL_NETWORK_NAME);
		assertThat(testModel.getInsertTs()).isEqualTo(UPDATED_INSERT_TS);
		assertThat(testModel.getLastUpdateTs()).isEqualTo(UPDATED_LAST_UPDATE_TS);
		assertThat(testModel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
	}

	@Test
	@Transactional
	public void updateNonExistingModel() throws Exception {
		int databaseSizeBeforeUpdate = modelRepository.findAll().size();

		// Create the Model

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restModelMockMvc.perform(put("/api/models")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(model)))
		.andExpect(status().isBadRequest());

		// Validate the Model in the database
		List<Model> modelList = modelRepository.findAll();
		assertThat(modelList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	public void deleteModel() throws Exception {
		// Initialize the database
		modelRepository.saveAndFlush(model);

		int databaseSizeBeforeDelete = modelRepository.findAll().size();

		// Delete the model
		restModelMockMvc.perform(delete("/api/models/{id}", model.getId())
				.accept(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<Model> modelList = modelRepository.findAll();
		assertThat(modelList).hasSize(databaseSizeBeforeDelete - 1);
	}

	@Test
	@Transactional
	public void equalsVerifier() throws Exception {
		TestUtil.equalsVerifier(Model.class);
		Model model1 = new Model();
		model1.setId(1L);
		Model model2 = new Model();
		model2.setId(model1.getId());
		assertThat(model1).isEqualTo(model2);
		model2.setId(2L);
		assertThat(model1).isNotEqualTo(model2);
		model1.setId(null);
		assertThat(model1).isNotEqualTo(model2);
	}
}
