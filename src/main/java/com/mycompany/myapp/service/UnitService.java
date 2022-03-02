package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Unit;
import com.mycompany.myapp.repository.UnitRepository;
import com.mycompany.myapp.service.dto.UnitDTO;
import com.mycompany.myapp.service.mapper.UnitMapper;
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
 * Service Implementation for managing {@link Unit}.
 */
@Service
@Transactional
public class UnitService {

    private final Logger log = LoggerFactory.getLogger(UnitService.class);

    private final UnitRepository unitRepository;

    private final UnitMapper unitMapper;

    public UnitService(UnitRepository unitRepository, UnitMapper unitMapper) {
        this.unitRepository = unitRepository;
        this.unitMapper = unitMapper;
    }

    /**
     * Save a unit.
     *
     * @param unitDTO the entity to save.
     * @return the persisted entity.
     */
    public UnitDTO save(UnitDTO unitDTO) {
        log.debug("Request to save Unit : {}", unitDTO);
        Unit unit = unitMapper.toEntity(unitDTO);
        unit = unitRepository.save(unit);
        return unitMapper.toDto(unit);
    }

    /**
     * Partially update a unit.
     *
     * @param unitDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UnitDTO> partialUpdate(UnitDTO unitDTO) {
        log.debug("Request to partially update Unit : {}", unitDTO);

        return unitRepository
            .findById(unitDTO.getId())
            .map(existingUnit -> {
                unitMapper.partialUpdate(existingUnit, unitDTO);

                return existingUnit;
            })
            .map(unitRepository::save)
            .map(unitMapper::toDto);
    }

    /**
     * Get all the units.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UnitDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Units");
        return unitRepository.findAll(pageable).map(unitMapper::toDto);
    }

    /**
     *  Get all the units where Product is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UnitDTO> findAllWhereProductIsNull() {
        log.debug("Request to get all units where Product is null");
        return StreamSupport
            .stream(unitRepository.findAll().spliterator(), false)
            .filter(unit -> unit.getProduct() == null)
            .map(unitMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the units where QuatationDetails is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UnitDTO> findAllWhereQuatationDetailsIsNull() {
        log.debug("Request to get all units where QuatationDetails is null");
        return StreamSupport
            .stream(unitRepository.findAll().spliterator(), false)
            .filter(unit -> unit.getQuatationDetails() == null)
            .map(unitMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one unit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UnitDTO> findOne(Long id) {
        log.debug("Request to get Unit : {}", id);
        return unitRepository.findById(id).map(unitMapper::toDto);
    }

    /**
     * Delete the unit by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Unit : {}", id);
        unitRepository.deleteById(id);
    }
}
