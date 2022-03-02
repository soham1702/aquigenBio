package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.RawMaterialOrder;
import com.mycompany.myapp.repository.RawMaterialOrderRepository;
import com.mycompany.myapp.service.dto.RawMaterialOrderDTO;
import com.mycompany.myapp.service.mapper.RawMaterialOrderMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RawMaterialOrder}.
 */
@Service
@Transactional
public class RawMaterialOrderService {

    private final Logger log = LoggerFactory.getLogger(RawMaterialOrderService.class);

    private final RawMaterialOrderRepository rawMaterialOrderRepository;

    private final RawMaterialOrderMapper rawMaterialOrderMapper;

    public RawMaterialOrderService(RawMaterialOrderRepository rawMaterialOrderRepository, RawMaterialOrderMapper rawMaterialOrderMapper) {
        this.rawMaterialOrderRepository = rawMaterialOrderRepository;
        this.rawMaterialOrderMapper = rawMaterialOrderMapper;
    }

    /**
     * Save a rawMaterialOrder.
     *
     * @param rawMaterialOrderDTO the entity to save.
     * @return the persisted entity.
     */
    public RawMaterialOrderDTO save(RawMaterialOrderDTO rawMaterialOrderDTO) {
        log.debug("Request to save RawMaterialOrder : {}", rawMaterialOrderDTO);
        RawMaterialOrder rawMaterialOrder = rawMaterialOrderMapper.toEntity(rawMaterialOrderDTO);
        rawMaterialOrder = rawMaterialOrderRepository.save(rawMaterialOrder);
        return rawMaterialOrderMapper.toDto(rawMaterialOrder);
    }

    /**
     * Partially update a rawMaterialOrder.
     *
     * @param rawMaterialOrderDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RawMaterialOrderDTO> partialUpdate(RawMaterialOrderDTO rawMaterialOrderDTO) {
        log.debug("Request to partially update RawMaterialOrder : {}", rawMaterialOrderDTO);

        return rawMaterialOrderRepository
            .findById(rawMaterialOrderDTO.getId())
            .map(existingRawMaterialOrder -> {
                rawMaterialOrderMapper.partialUpdate(existingRawMaterialOrder, rawMaterialOrderDTO);

                return existingRawMaterialOrder;
            })
            .map(rawMaterialOrderRepository::save)
            .map(rawMaterialOrderMapper::toDto);
    }

    /**
     * Get all the rawMaterialOrders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RawMaterialOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RawMaterialOrders");
        return rawMaterialOrderRepository.findAll(pageable).map(rawMaterialOrderMapper::toDto);
    }

    /**
     * Get one rawMaterialOrder by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RawMaterialOrderDTO> findOne(Long id) {
        log.debug("Request to get RawMaterialOrder : {}", id);
        return rawMaterialOrderRepository.findById(id).map(rawMaterialOrderMapper::toDto);
    }

    /**
     * Delete the rawMaterialOrder by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RawMaterialOrder : {}", id);
        rawMaterialOrderRepository.deleteById(id);
    }
}
