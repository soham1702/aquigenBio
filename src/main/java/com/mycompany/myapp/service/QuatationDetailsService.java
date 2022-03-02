package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.QuatationDetails;
import com.mycompany.myapp.repository.QuatationDetailsRepository;
import com.mycompany.myapp.service.dto.QuatationDetailsDTO;
import com.mycompany.myapp.service.mapper.QuatationDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link QuatationDetails}.
 */
@Service
@Transactional
public class QuatationDetailsService {

    private final Logger log = LoggerFactory.getLogger(QuatationDetailsService.class);

    private final QuatationDetailsRepository quatationDetailsRepository;

    private final QuatationDetailsMapper quatationDetailsMapper;

    public QuatationDetailsService(QuatationDetailsRepository quatationDetailsRepository, QuatationDetailsMapper quatationDetailsMapper) {
        this.quatationDetailsRepository = quatationDetailsRepository;
        this.quatationDetailsMapper = quatationDetailsMapper;
    }

    /**
     * Save a quatationDetails.
     *
     * @param quatationDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public QuatationDetailsDTO save(QuatationDetailsDTO quatationDetailsDTO) {
        log.debug("Request to save QuatationDetails : {}", quatationDetailsDTO);
        QuatationDetails quatationDetails = quatationDetailsMapper.toEntity(quatationDetailsDTO);
        quatationDetails = quatationDetailsRepository.save(quatationDetails);
        return quatationDetailsMapper.toDto(quatationDetails);
    }

    /**
     * Partially update a quatationDetails.
     *
     * @param quatationDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<QuatationDetailsDTO> partialUpdate(QuatationDetailsDTO quatationDetailsDTO) {
        log.debug("Request to partially update QuatationDetails : {}", quatationDetailsDTO);

        return quatationDetailsRepository
            .findById(quatationDetailsDTO.getId())
            .map(existingQuatationDetails -> {
                quatationDetailsMapper.partialUpdate(existingQuatationDetails, quatationDetailsDTO);

                return existingQuatationDetails;
            })
            .map(quatationDetailsRepository::save)
            .map(quatationDetailsMapper::toDto);
    }

    /**
     * Get all the quatationDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<QuatationDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all QuatationDetails");
        return quatationDetailsRepository.findAll(pageable).map(quatationDetailsMapper::toDto);
    }

    /**
     * Get one quatationDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<QuatationDetailsDTO> findOne(Long id) {
        log.debug("Request to get QuatationDetails : {}", id);
        return quatationDetailsRepository.findById(id).map(quatationDetailsMapper::toDto);
    }

    /**
     * Delete the quatationDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete QuatationDetails : {}", id);
        quatationDetailsRepository.deleteById(id);
    }
}
