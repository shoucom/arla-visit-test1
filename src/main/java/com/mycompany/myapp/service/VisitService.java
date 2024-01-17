package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Visit;
import com.mycompany.myapp.repository.VisitRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Visit}.
 */
@Service
@Transactional
public class VisitService {

    private final Logger log = LoggerFactory.getLogger(VisitService.class);

    private final VisitRepository visitRepository;

    public VisitService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    /**
     * Save a visit.
     *
     * @param visit the entity to save.
     * @return the persisted entity.
     */
    public Visit save(Visit visit) {
        log.debug("Request to save Visit : {}", visit);
        return visitRepository.save(visit);
    }

    /**
     * Update a visit.
     *
     * @param visit the entity to save.
     * @return the persisted entity.
     */
    public Visit update(Visit visit) {
        log.debug("Request to update Visit : {}", visit);
        return visitRepository.save(visit);
    }

    /**
     * Partially update a visit.
     *
     * @param visit the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Visit> partialUpdate(Visit visit) {
        log.debug("Request to partially update Visit : {}", visit);

        return visitRepository
            .findById(visit.getId())
            .map(existingVisit -> {
                if (visit.getInTime() != null) {
                    existingVisit.setInTime(visit.getInTime());
                }
                if (visit.getOutTime() != null) {
                    existingVisit.setOutTime(visit.getOutTime());
                }
                if (visit.getCarRegistrationNumber() != null) {
                    existingVisit.setCarRegistrationNumber(visit.getCarRegistrationNumber());
                }
                if (visit.getCarParkingNumber() != null) {
                    existingVisit.setCarParkingNumber(visit.getCarParkingNumber());
                }
                if (visit.getMessageToHost() != null) {
                    existingVisit.setMessageToHost(visit.getMessageToHost());
                }

                return existingVisit;
            })
            .map(visitRepository::save);
    }

    /**
     * Get all the visits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Visit> findAll(Pageable pageable) {
        log.debug("Request to get all Visits");
        return visitRepository.findAll(pageable);
    }

    /**
     * Get one visit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Visit> findOne(Long id) {
        log.debug("Request to get Visit : {}", id);
        return visitRepository.findById(id);
    }

    /**
     * Delete the visit by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Visit : {}", id);
        visitRepository.deleteById(id);
    }
}
