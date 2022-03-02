package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ConsumptionDetails;
import com.mycompany.myapp.repository.ConsumptionDetailsRepository;
import com.mycompany.myapp.service.dto.ConsumptionDetailsDTO;
import com.mycompany.myapp.service.mapper.ConsumptionDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ConsumptionDetails}.
 */
@Service
@Transactional
public class ConsumptionDetailsService {

    private final Logger log = LoggerFactory.getLogger(ConsumptionDetailsService.class);

    private final ConsumptionDetailsRepository consumptionDetailsRepository;

    private final ConsumptionDetailsMapper consumptionDetailsMapper;

    public ConsumptionDetailsService(
        ConsumptionDetailsRepository consumptionDetailsRepository,
        ConsumptionDetailsMapper consumptionDetailsMapper
    ) {
        this.consumptionDetailsRepository = consumptionDetailsRepository;
        this.consumptionDetailsMapper = consumptionDetailsMapper;
    }

    /**
     * Save a consumptionDetails.
     *
     * @param consumptionDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public ConsumptionDetailsDTO save(ConsumptionDetailsDTO consumptionDetailsDTO) {
        log.debug("Request to save ConsumptionDetails : {}", consumptionDetailsDTO);
        ConsumptionDetails consumptionDetails = consumptionDetailsMapper.toEntity(consumptionDetailsDTO);
        consumptionDetails = consumptionDetailsRepository.save(consumptionDetails);
        return consumptionDetailsMapper.toDto(consumptionDetails);
    }

    /**
     * Partially update a consumptionDetails.
     *
     * @param consumptionDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ConsumptionDetailsDTO> partialUpdate(ConsumptionDetailsDTO consumptionDetailsDTO) {
        log.debug("Request to partially update ConsumptionDetails : {}", consumptionDetailsDTO);

        return consumptionDetailsRepository
            .findById(consumptionDetailsDTO.getId())
            .map(existingConsumptionDetails -> {
                consumptionDetailsMapper.partialUpdate(existingConsumptionDetails, consumptionDetailsDTO);

                return existingConsumptionDetails;
            })
            .map(consumptionDetailsRepository::save)
            .map(consumptionDetailsMapper::toDto);
    }

    /**
     * Get all the consumptionDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ConsumptionDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConsumptionDetails");
        return consumptionDetailsRepository.findAll(pageable).map(consumptionDetailsMapper::toDto);
    }

    /**
     * Get one consumptionDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConsumptionDetailsDTO> findOne(Long id) {
        log.debug("Request to get ConsumptionDetails : {}", id);
        return consumptionDetailsRepository.findById(id).map(consumptionDetailsMapper::toDto);
    }

    /**
     * Delete the consumptionDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ConsumptionDetails : {}", id);
        consumptionDetailsRepository.deleteById(id);
    }
}
