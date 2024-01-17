package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Office;
import com.mycompany.myapp.repository.OfficeRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Office}.
 */
@Service
@Transactional
public class OfficeService {

    private final Logger log = LoggerFactory.getLogger(OfficeService.class);

    private final OfficeRepository officeRepository;

    public OfficeService(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    /**
     * Save a office.
     *
     * @param office the entity to save.
     * @return the persisted entity.
     */
    public Office save(Office office) {
        log.debug("Request to save Office : {}", office);
        return officeRepository.save(office);
    }

    /**
     * Update a office.
     *
     * @param office the entity to save.
     * @return the persisted entity.
     */
    public Office update(Office office) {
        log.debug("Request to update Office : {}", office);
        return officeRepository.save(office);
    }

    /**
     * Partially update a office.
     *
     * @param office the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Office> partialUpdate(Office office) {
        log.debug("Request to partially update Office : {}", office);

        return officeRepository
            .findById(office.getId())
            .map(existingOffice -> {
                if (office.getName() != null) {
                    existingOffice.setName(office.getName());
                }
                if (office.getAddress() != null) {
                    existingOffice.setAddress(office.getAddress());
                }
                if (office.getPhone() != null) {
                    existingOffice.setPhone(office.getPhone());
                }
                if (office.getTimeZone() != null) {
                    existingOffice.setTimeZone(office.getTimeZone());
                }
                if (office.getWifiPassword() != null) {
                    existingOffice.setWifiPassword(office.getWifiPassword());
                }
                if (office.getLanguage() != null) {
                    existingOffice.setLanguage(office.getLanguage());
                }

                return existingOffice;
            })
            .map(officeRepository::save);
    }

    /**
     * Get all the offices.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Office> findAll() {
        log.debug("Request to get all Offices");
        return officeRepository.findAll();
    }

    /**
     * Get one office by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Office> findOne(Long id) {
        log.debug("Request to get Office : {}", id);
        return officeRepository.findById(id);
    }

    /**
     * Delete the office by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Office : {}", id);
        officeRepository.deleteById(id);
    }
}
