package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.TranferRecieved;
import com.mycompany.myapp.repository.TranferRecievedRepository;
import com.mycompany.myapp.service.dto.TranferRecievedDTO;
import com.mycompany.myapp.service.mapper.TranferRecievedMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TranferRecieved}.
 */
@Service
@Transactional
public class TranferRecievedService {

    private final Logger log = LoggerFactory.getLogger(TranferRecievedService.class);

    private final TranferRecievedRepository tranferRecievedRepository;

    private final TranferRecievedMapper tranferRecievedMapper;

    public TranferRecievedService(TranferRecievedRepository tranferRecievedRepository, TranferRecievedMapper tranferRecievedMapper) {
        this.tranferRecievedRepository = tranferRecievedRepository;
        this.tranferRecievedMapper = tranferRecievedMapper;
    }

    /**
     * Save a tranferRecieved.
     *
     * @param tranferRecievedDTO the entity to save.
     * @return the persisted entity.
     */
    public TranferRecievedDTO save(TranferRecievedDTO tranferRecievedDTO) {
        log.debug("Request to save TranferRecieved : {}", tranferRecievedDTO);
        TranferRecieved tranferRecieved = tranferRecievedMapper.toEntity(tranferRecievedDTO);
        tranferRecieved = tranferRecievedRepository.save(tranferRecieved);
        return tranferRecievedMapper.toDto(tranferRecieved);
    }

    /**
     * Partially update a tranferRecieved.
     *
     * @param tranferRecievedDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TranferRecievedDTO> partialUpdate(TranferRecievedDTO tranferRecievedDTO) {
        log.debug("Request to partially update TranferRecieved : {}", tranferRecievedDTO);

        return tranferRecievedRepository
            .findById(tranferRecievedDTO.getId())
            .map(existingTranferRecieved -> {
                tranferRecievedMapper.partialUpdate(existingTranferRecieved, tranferRecievedDTO);

                return existingTranferRecieved;
            })
            .map(tranferRecievedRepository::save)
            .map(tranferRecievedMapper::toDto);
    }

    /**
     * Get all the tranferRecieveds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TranferRecievedDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TranferRecieveds");
        return tranferRecievedRepository.findAll(pageable).map(tranferRecievedMapper::toDto);
    }

    /**
     * Get one tranferRecieved by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TranferRecievedDTO> findOne(Long id) {
        log.debug("Request to get TranferRecieved : {}", id);
        return tranferRecievedRepository.findById(id).map(tranferRecievedMapper::toDto);
    }

    /**
     * Delete the tranferRecieved by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TranferRecieved : {}", id);
        tranferRecievedRepository.deleteById(id);
    }
}
