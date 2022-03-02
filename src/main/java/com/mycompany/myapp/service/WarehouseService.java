package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Warehouse;
import com.mycompany.myapp.repository.WarehouseRepository;
import com.mycompany.myapp.service.dto.WarehouseDTO;
import com.mycompany.myapp.service.mapper.WarehouseMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Warehouse}.
 */
@Service
@Transactional
public class WarehouseService {

    private final Logger log = LoggerFactory.getLogger(WarehouseService.class);

    private final WarehouseRepository warehouseRepository;

    private final WarehouseMapper warehouseMapper;

    public WarehouseService(WarehouseRepository warehouseRepository, WarehouseMapper warehouseMapper) {
        this.warehouseRepository = warehouseRepository;
        this.warehouseMapper = warehouseMapper;
    }

    /**
     * Save a warehouse.
     *
     * @param warehouseDTO the entity to save.
     * @return the persisted entity.
     */
    public WarehouseDTO save(WarehouseDTO warehouseDTO) {
        log.debug("Request to save Warehouse : {}", warehouseDTO);
        Warehouse warehouse = warehouseMapper.toEntity(warehouseDTO);
        warehouse = warehouseRepository.save(warehouse);
        return warehouseMapper.toDto(warehouse);
    }

    /**
     * Partially update a warehouse.
     *
     * @param warehouseDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<WarehouseDTO> partialUpdate(WarehouseDTO warehouseDTO) {
        log.debug("Request to partially update Warehouse : {}", warehouseDTO);

        return warehouseRepository
            .findById(warehouseDTO.getId())
            .map(existingWarehouse -> {
                warehouseMapper.partialUpdate(existingWarehouse, warehouseDTO);

                return existingWarehouse;
            })
            .map(warehouseRepository::save)
            .map(warehouseMapper::toDto);
    }

    /**
     * Get all the warehouses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WarehouseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Warehouses");
        return warehouseRepository.findAll(pageable).map(warehouseMapper::toDto);
    }

    /**
     * Get all the warehouses with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<WarehouseDTO> findAllWithEagerRelationships(Pageable pageable) {
        return warehouseRepository.findAllWithEagerRelationships(pageable).map(warehouseMapper::toDto);
    }

    /**
     *  Get all the warehouses where ProductTransaction is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<WarehouseDTO> findAllWhereProductTransactionIsNull() {
        log.debug("Request to get all warehouses where ProductTransaction is null");
        return StreamSupport
            .stream(warehouseRepository.findAll().spliterator(), false)
            .filter(warehouse -> warehouse.getProductTransaction() == null)
            .map(warehouseMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one warehouse by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WarehouseDTO> findOne(Long id) {
        log.debug("Request to get Warehouse : {}", id);
        return warehouseRepository.findOneWithEagerRelationships(id).map(warehouseMapper::toDto);
    }

    /**
     * Delete the warehouse by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Warehouse : {}", id);
        warehouseRepository.deleteById(id);
    }
}
