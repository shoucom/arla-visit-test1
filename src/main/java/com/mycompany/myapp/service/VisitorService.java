package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Visitor;
import com.mycompany.myapp.repository.VisitorRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Visitor}.
 */
@Service
@Transactional
public class VisitorService {

    private final Logger log = LoggerFactory.getLogger(VisitorService.class);

    private final VisitorRepository visitorRepository;

    public VisitorService(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    /**
     * Save a visitor.
     *
     * @param visitor the entity to save.
     * @return the persisted entity.
     */
    public Visitor save(Visitor visitor) {
        log.debug("Request to save Visitor : {}", visitor);
        return visitorRepository.save(visitor);
    }

    /**
     * Update a visitor.
     *
     * @param visitor the entity to save.
     * @return the persisted entity.
     */
    public Visitor update(Visitor visitor) {
        log.debug("Request to update Visitor : {}", visitor);
        return visitorRepository.save(visitor);
    }

    /**
     * Partially update a visitor.
     *
     * @param visitor the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Visitor> partialUpdate(Visitor visitor) {
        log.debug("Request to partially update Visitor : {}", visitor);

        return visitorRepository
            .findById(visitor.getId())
            .map(existingVisitor -> {
                if (visitor.getFirstName() != null) {
                    existingVisitor.setFirstName(visitor.getFirstName());
                }
                if (visitor.getLastName() != null) {
                    existingVisitor.setLastName(visitor.getLastName());
                }
                if (visitor.getEmail() != null) {
                    existingVisitor.setEmail(visitor.getEmail());
                }
                if (visitor.getMobileNumber() != null) {
                    existingVisitor.setMobileNumber(visitor.getMobileNumber());
                }
                if (visitor.getCompany() != null) {
                    existingVisitor.setCompany(visitor.getCompany());
                }

                return existingVisitor;
            })
            .map(visitorRepository::save);
    }

    /**
     * Get all the visitors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Visitor> findAll(Pageable pageable) {
        log.debug("Request to get all Visitors");
        return visitorRepository.findAll(pageable);
    }

    /**
     * Get one visitor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Visitor> findOne(Long id) {
        log.debug("Request to get Visitor : {}", id);
        return visitorRepository.findById(id);
    }

    /**
     * Delete the visitor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Visitor : {}", id);
        visitorRepository.deleteById(id);
    }
}
