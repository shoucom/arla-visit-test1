package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Employee;
import com.mycompany.myapp.domain.Office;
import com.mycompany.myapp.domain.Visit;
import com.mycompany.myapp.domain.Visitor;
import com.mycompany.myapp.repository.VisitRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link VisitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VisitResourceIT {

    private static final Instant DEFAULT_IN_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_IN_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_OUT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OUT_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CAR_REGISTRATION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CAR_REGISTRATION_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CAR_PARKING_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CAR_PARKING_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE_TO_HOST = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE_TO_HOST = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/visits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVisitMockMvc;

    private Visit visit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Visit createEntity(EntityManager em) {
        Visit visit = new Visit()
            .inTime(DEFAULT_IN_TIME)
            .outTime(DEFAULT_OUT_TIME)
            .carRegistrationNumber(DEFAULT_CAR_REGISTRATION_NUMBER)
            .carParkingNumber(DEFAULT_CAR_PARKING_NUMBER)
            .messageToHost(DEFAULT_MESSAGE_TO_HOST);
        // Add required entity
        Visitor visitor;
        if (TestUtil.findAll(em, Visitor.class).isEmpty()) {
            visitor = VisitorResourceIT.createEntity(em);
            em.persist(visitor);
            em.flush();
        } else {
            visitor = TestUtil.findAll(em, Visitor.class).get(0);
        }
        visit.setVisitor(visitor);
        // Add required entity
        Office office;
        if (TestUtil.findAll(em, Office.class).isEmpty()) {
            office = OfficeResourceIT.createEntity(em);
            em.persist(office);
            em.flush();
        } else {
            office = TestUtil.findAll(em, Office.class).get(0);
        }
        visit.setOffice(office);
        // Add required entity
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            employee = EmployeeResourceIT.createEntity(em);
            em.persist(employee);
            em.flush();
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        visit.setHost(employee);
        return visit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Visit createUpdatedEntity(EntityManager em) {
        Visit visit = new Visit()
            .inTime(UPDATED_IN_TIME)
            .outTime(UPDATED_OUT_TIME)
            .carRegistrationNumber(UPDATED_CAR_REGISTRATION_NUMBER)
            .carParkingNumber(UPDATED_CAR_PARKING_NUMBER)
            .messageToHost(UPDATED_MESSAGE_TO_HOST);
        // Add required entity
        Visitor visitor;
        if (TestUtil.findAll(em, Visitor.class).isEmpty()) {
            visitor = VisitorResourceIT.createUpdatedEntity(em);
            em.persist(visitor);
            em.flush();
        } else {
            visitor = TestUtil.findAll(em, Visitor.class).get(0);
        }
        visit.setVisitor(visitor);
        // Add required entity
        Office office;
        if (TestUtil.findAll(em, Office.class).isEmpty()) {
            office = OfficeResourceIT.createUpdatedEntity(em);
            em.persist(office);
            em.flush();
        } else {
            office = TestUtil.findAll(em, Office.class).get(0);
        }
        visit.setOffice(office);
        // Add required entity
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            employee = EmployeeResourceIT.createUpdatedEntity(em);
            em.persist(employee);
            em.flush();
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        visit.setHost(employee);
        return visit;
    }

    @BeforeEach
    public void initTest() {
        visit = createEntity(em);
    }

    @Test
    @Transactional
    void createVisit() throws Exception {
        int databaseSizeBeforeCreate = visitRepository.findAll().size();
        // Create the Visit
        restVisitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(visit)))
            .andExpect(status().isCreated());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeCreate + 1);
        Visit testVisit = visitList.get(visitList.size() - 1);
        assertThat(testVisit.getInTime()).isEqualTo(DEFAULT_IN_TIME);
        assertThat(testVisit.getOutTime()).isEqualTo(DEFAULT_OUT_TIME);
        assertThat(testVisit.getCarRegistrationNumber()).isEqualTo(DEFAULT_CAR_REGISTRATION_NUMBER);
        assertThat(testVisit.getCarParkingNumber()).isEqualTo(DEFAULT_CAR_PARKING_NUMBER);
        assertThat(testVisit.getMessageToHost()).isEqualTo(DEFAULT_MESSAGE_TO_HOST);
    }

    @Test
    @Transactional
    void createVisitWithExistingId() throws Exception {
        // Create the Visit with an existing ID
        visit.setId(1L);

        int databaseSizeBeforeCreate = visitRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVisitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(visit)))
            .andExpect(status().isBadRequest());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVisits() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get all the visitList
        restVisitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(visit.getId().intValue())))
            .andExpect(jsonPath("$.[*].inTime").value(hasItem(DEFAULT_IN_TIME.toString())))
            .andExpect(jsonPath("$.[*].outTime").value(hasItem(DEFAULT_OUT_TIME.toString())))
            .andExpect(jsonPath("$.[*].carRegistrationNumber").value(hasItem(DEFAULT_CAR_REGISTRATION_NUMBER)))
            .andExpect(jsonPath("$.[*].carParkingNumber").value(hasItem(DEFAULT_CAR_PARKING_NUMBER)))
            .andExpect(jsonPath("$.[*].messageToHost").value(hasItem(DEFAULT_MESSAGE_TO_HOST)));
    }

    @Test
    @Transactional
    void getVisit() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get the visit
        restVisitMockMvc
            .perform(get(ENTITY_API_URL_ID, visit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(visit.getId().intValue()))
            .andExpect(jsonPath("$.inTime").value(DEFAULT_IN_TIME.toString()))
            .andExpect(jsonPath("$.outTime").value(DEFAULT_OUT_TIME.toString()))
            .andExpect(jsonPath("$.carRegistrationNumber").value(DEFAULT_CAR_REGISTRATION_NUMBER))
            .andExpect(jsonPath("$.carParkingNumber").value(DEFAULT_CAR_PARKING_NUMBER))
            .andExpect(jsonPath("$.messageToHost").value(DEFAULT_MESSAGE_TO_HOST));
    }

    @Test
    @Transactional
    void getNonExistingVisit() throws Exception {
        // Get the visit
        restVisitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVisit() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        int databaseSizeBeforeUpdate = visitRepository.findAll().size();

        // Update the visit
        Visit updatedVisit = visitRepository.findById(visit.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVisit are not directly saved in db
        em.detach(updatedVisit);
        updatedVisit
            .inTime(UPDATED_IN_TIME)
            .outTime(UPDATED_OUT_TIME)
            .carRegistrationNumber(UPDATED_CAR_REGISTRATION_NUMBER)
            .carParkingNumber(UPDATED_CAR_PARKING_NUMBER)
            .messageToHost(UPDATED_MESSAGE_TO_HOST);

        restVisitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVisit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVisit))
            )
            .andExpect(status().isOk());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate);
        Visit testVisit = visitList.get(visitList.size() - 1);
        assertThat(testVisit.getInTime()).isEqualTo(UPDATED_IN_TIME);
        assertThat(testVisit.getOutTime()).isEqualTo(UPDATED_OUT_TIME);
        assertThat(testVisit.getCarRegistrationNumber()).isEqualTo(UPDATED_CAR_REGISTRATION_NUMBER);
        assertThat(testVisit.getCarParkingNumber()).isEqualTo(UPDATED_CAR_PARKING_NUMBER);
        assertThat(testVisit.getMessageToHost()).isEqualTo(UPDATED_MESSAGE_TO_HOST);
    }

    @Test
    @Transactional
    void putNonExistingVisit() throws Exception {
        int databaseSizeBeforeUpdate = visitRepository.findAll().size();
        visit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVisitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, visit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(visit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVisit() throws Exception {
        int databaseSizeBeforeUpdate = visitRepository.findAll().size();
        visit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVisitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(visit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVisit() throws Exception {
        int databaseSizeBeforeUpdate = visitRepository.findAll().size();
        visit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVisitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(visit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVisitWithPatch() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        int databaseSizeBeforeUpdate = visitRepository.findAll().size();

        // Update the visit using partial update
        Visit partialUpdatedVisit = new Visit();
        partialUpdatedVisit.setId(visit.getId());

        partialUpdatedVisit
            .outTime(UPDATED_OUT_TIME)
            .carRegistrationNumber(UPDATED_CAR_REGISTRATION_NUMBER)
            .carParkingNumber(UPDATED_CAR_PARKING_NUMBER);

        restVisitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVisit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVisit))
            )
            .andExpect(status().isOk());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate);
        Visit testVisit = visitList.get(visitList.size() - 1);
        assertThat(testVisit.getInTime()).isEqualTo(DEFAULT_IN_TIME);
        assertThat(testVisit.getOutTime()).isEqualTo(UPDATED_OUT_TIME);
        assertThat(testVisit.getCarRegistrationNumber()).isEqualTo(UPDATED_CAR_REGISTRATION_NUMBER);
        assertThat(testVisit.getCarParkingNumber()).isEqualTo(UPDATED_CAR_PARKING_NUMBER);
        assertThat(testVisit.getMessageToHost()).isEqualTo(DEFAULT_MESSAGE_TO_HOST);
    }

    @Test
    @Transactional
    void fullUpdateVisitWithPatch() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        int databaseSizeBeforeUpdate = visitRepository.findAll().size();

        // Update the visit using partial update
        Visit partialUpdatedVisit = new Visit();
        partialUpdatedVisit.setId(visit.getId());

        partialUpdatedVisit
            .inTime(UPDATED_IN_TIME)
            .outTime(UPDATED_OUT_TIME)
            .carRegistrationNumber(UPDATED_CAR_REGISTRATION_NUMBER)
            .carParkingNumber(UPDATED_CAR_PARKING_NUMBER)
            .messageToHost(UPDATED_MESSAGE_TO_HOST);

        restVisitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVisit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVisit))
            )
            .andExpect(status().isOk());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate);
        Visit testVisit = visitList.get(visitList.size() - 1);
        assertThat(testVisit.getInTime()).isEqualTo(UPDATED_IN_TIME);
        assertThat(testVisit.getOutTime()).isEqualTo(UPDATED_OUT_TIME);
        assertThat(testVisit.getCarRegistrationNumber()).isEqualTo(UPDATED_CAR_REGISTRATION_NUMBER);
        assertThat(testVisit.getCarParkingNumber()).isEqualTo(UPDATED_CAR_PARKING_NUMBER);
        assertThat(testVisit.getMessageToHost()).isEqualTo(UPDATED_MESSAGE_TO_HOST);
    }

    @Test
    @Transactional
    void patchNonExistingVisit() throws Exception {
        int databaseSizeBeforeUpdate = visitRepository.findAll().size();
        visit.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVisitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, visit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(visit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVisit() throws Exception {
        int databaseSizeBeforeUpdate = visitRepository.findAll().size();
        visit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVisitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(visit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVisit() throws Exception {
        int databaseSizeBeforeUpdate = visitRepository.findAll().size();
        visit.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVisitMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(visit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVisit() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        int databaseSizeBeforeDelete = visitRepository.findAll().size();

        // Delete the visit
        restVisitMockMvc
            .perform(delete(ENTITY_API_URL_ID, visit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
