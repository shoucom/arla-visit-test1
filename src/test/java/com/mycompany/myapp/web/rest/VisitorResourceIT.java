package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Visitor;
import com.mycompany.myapp.repository.VisitorRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VisitorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VisitorResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Long DEFAULT_MOBILE_NUMBER = 1L;
    private static final Long UPDATED_MOBILE_NUMBER = 2L;

    private static final String DEFAULT_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/visitors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVisitorMockMvc;

    private Visitor visitor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Visitor createEntity(EntityManager em) {
        Visitor visitor = new Visitor()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .mobileNumber(DEFAULT_MOBILE_NUMBER)
            .company(DEFAULT_COMPANY);
        return visitor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Visitor createUpdatedEntity(EntityManager em) {
        Visitor visitor = new Visitor()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .company(UPDATED_COMPANY);
        return visitor;
    }

    @BeforeEach
    public void initTest() {
        visitor = createEntity(em);
    }

    @Test
    @Transactional
    void createVisitor() throws Exception {
        int databaseSizeBeforeCreate = visitorRepository.findAll().size();
        // Create the Visitor
        restVisitorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(visitor)))
            .andExpect(status().isCreated());

        // Validate the Visitor in the database
        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeCreate + 1);
        Visitor testVisitor = visitorList.get(visitorList.size() - 1);
        assertThat(testVisitor.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testVisitor.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testVisitor.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testVisitor.getMobileNumber()).isEqualTo(DEFAULT_MOBILE_NUMBER);
        assertThat(testVisitor.getCompany()).isEqualTo(DEFAULT_COMPANY);
    }

    @Test
    @Transactional
    void createVisitorWithExistingId() throws Exception {
        // Create the Visitor with an existing ID
        visitor.setId(1L);

        int databaseSizeBeforeCreate = visitorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVisitorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(visitor)))
            .andExpect(status().isBadRequest());

        // Validate the Visitor in the database
        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = visitorRepository.findAll().size();
        // set the field null
        visitor.setFirstName(null);

        // Create the Visitor, which fails.

        restVisitorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(visitor)))
            .andExpect(status().isBadRequest());

        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = visitorRepository.findAll().size();
        // set the field null
        visitor.setLastName(null);

        // Create the Visitor, which fails.

        restVisitorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(visitor)))
            .andExpect(status().isBadRequest());

        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMobileNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = visitorRepository.findAll().size();
        // set the field null
        visitor.setMobileNumber(null);

        // Create the Visitor, which fails.

        restVisitorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(visitor)))
            .andExpect(status().isBadRequest());

        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVisitors() throws Exception {
        // Initialize the database
        visitorRepository.saveAndFlush(visitor);

        // Get all the visitorList
        restVisitorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(visitor.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].mobileNumber").value(hasItem(DEFAULT_MOBILE_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY)));
    }

    @Test
    @Transactional
    void getVisitor() throws Exception {
        // Initialize the database
        visitorRepository.saveAndFlush(visitor);

        // Get the visitor
        restVisitorMockMvc
            .perform(get(ENTITY_API_URL_ID, visitor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(visitor.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.mobileNumber").value(DEFAULT_MOBILE_NUMBER.intValue()))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY));
    }

    @Test
    @Transactional
    void getNonExistingVisitor() throws Exception {
        // Get the visitor
        restVisitorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVisitor() throws Exception {
        // Initialize the database
        visitorRepository.saveAndFlush(visitor);

        int databaseSizeBeforeUpdate = visitorRepository.findAll().size();

        // Update the visitor
        Visitor updatedVisitor = visitorRepository.findById(visitor.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVisitor are not directly saved in db
        em.detach(updatedVisitor);
        updatedVisitor
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .company(UPDATED_COMPANY);

        restVisitorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVisitor.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVisitor))
            )
            .andExpect(status().isOk());

        // Validate the Visitor in the database
        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeUpdate);
        Visitor testVisitor = visitorList.get(visitorList.size() - 1);
        assertThat(testVisitor.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testVisitor.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testVisitor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testVisitor.getMobileNumber()).isEqualTo(UPDATED_MOBILE_NUMBER);
        assertThat(testVisitor.getCompany()).isEqualTo(UPDATED_COMPANY);
    }

    @Test
    @Transactional
    void putNonExistingVisitor() throws Exception {
        int databaseSizeBeforeUpdate = visitorRepository.findAll().size();
        visitor.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVisitorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, visitor.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(visitor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Visitor in the database
        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVisitor() throws Exception {
        int databaseSizeBeforeUpdate = visitorRepository.findAll().size();
        visitor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVisitorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(visitor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Visitor in the database
        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVisitor() throws Exception {
        int databaseSizeBeforeUpdate = visitorRepository.findAll().size();
        visitor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVisitorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(visitor)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Visitor in the database
        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVisitorWithPatch() throws Exception {
        // Initialize the database
        visitorRepository.saveAndFlush(visitor);

        int databaseSizeBeforeUpdate = visitorRepository.findAll().size();

        // Update the visitor using partial update
        Visitor partialUpdatedVisitor = new Visitor();
        partialUpdatedVisitor.setId(visitor.getId());

        partialUpdatedVisitor.lastName(UPDATED_LAST_NAME).email(UPDATED_EMAIL).mobileNumber(UPDATED_MOBILE_NUMBER);

        restVisitorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVisitor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVisitor))
            )
            .andExpect(status().isOk());

        // Validate the Visitor in the database
        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeUpdate);
        Visitor testVisitor = visitorList.get(visitorList.size() - 1);
        assertThat(testVisitor.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testVisitor.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testVisitor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testVisitor.getMobileNumber()).isEqualTo(UPDATED_MOBILE_NUMBER);
        assertThat(testVisitor.getCompany()).isEqualTo(DEFAULT_COMPANY);
    }

    @Test
    @Transactional
    void fullUpdateVisitorWithPatch() throws Exception {
        // Initialize the database
        visitorRepository.saveAndFlush(visitor);

        int databaseSizeBeforeUpdate = visitorRepository.findAll().size();

        // Update the visitor using partial update
        Visitor partialUpdatedVisitor = new Visitor();
        partialUpdatedVisitor.setId(visitor.getId());

        partialUpdatedVisitor
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .company(UPDATED_COMPANY);

        restVisitorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVisitor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVisitor))
            )
            .andExpect(status().isOk());

        // Validate the Visitor in the database
        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeUpdate);
        Visitor testVisitor = visitorList.get(visitorList.size() - 1);
        assertThat(testVisitor.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testVisitor.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testVisitor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testVisitor.getMobileNumber()).isEqualTo(UPDATED_MOBILE_NUMBER);
        assertThat(testVisitor.getCompany()).isEqualTo(UPDATED_COMPANY);
    }

    @Test
    @Transactional
    void patchNonExistingVisitor() throws Exception {
        int databaseSizeBeforeUpdate = visitorRepository.findAll().size();
        visitor.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVisitorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, visitor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(visitor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Visitor in the database
        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVisitor() throws Exception {
        int databaseSizeBeforeUpdate = visitorRepository.findAll().size();
        visitor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVisitorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(visitor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Visitor in the database
        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVisitor() throws Exception {
        int databaseSizeBeforeUpdate = visitorRepository.findAll().size();
        visitor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVisitorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(visitor)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Visitor in the database
        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVisitor() throws Exception {
        // Initialize the database
        visitorRepository.saveAndFlush(visitor);

        int databaseSizeBeforeDelete = visitorRepository.findAll().size();

        // Delete the visitor
        restVisitorMockMvc
            .perform(delete(ENTITY_API_URL_ID, visitor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
