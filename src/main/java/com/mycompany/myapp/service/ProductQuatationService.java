package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ProductQuatation;
import com.mycompany.myapp.repository.ProductQuatationRepository;
import com.mycompany.myapp.service.dto.ProductQuatationDTO;
import com.mycompany.myapp.service.mapper.ProductQuatationMapper;
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
 * Service Implementation for managing {@link ProductQuatation}.
 */
@Service
@Transactional
public class ProductQuatationService {

    private final Logger log = LoggerFactory.getLogger(ProductQuatationService.class);

    private final ProductQuatationRepository productQuatationRepository;

    private final ProductQuatationMapper productQuatationMapper;

    public ProductQuatationService(ProductQuatationRepository productQuatationRepository, ProductQuatationMapper productQuatationMapper) {
        this.productQuatationRepository = productQuatationRepository;
        this.productQuatationMapper = productQuatationMapper;
    }

    /**
     * Save a productQuatation.
     *
     * @param productQuatationDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductQuatationDTO save(ProductQuatationDTO productQuatationDTO) {
        log.debug("Request to save ProductQuatation : {}", productQuatationDTO);
        ProductQuatation productQuatation = productQuatationMapper.toEntity(productQuatationDTO);
        productQuatation = productQuatationRepository.save(productQuatation);
        return productQuatationMapper.toDto(productQuatation);
    }

    /**
     * Partially update a productQuatation.
     *
     * @param productQuatationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductQuatationDTO> partialUpdate(ProductQuatationDTO productQuatationDTO) {
        log.debug("Request to partially update ProductQuatation : {}", productQuatationDTO);

        return productQuatationRepository
            .findById(productQuatationDTO.getId())
            .map(existingProductQuatation -> {
                productQuatationMapper.partialUpdate(existingProductQuatation, productQuatationDTO);

                return existingProductQuatation;
            })
            .map(productQuatationRepository::save)
            .map(productQuatationMapper::toDto);
    }

    /**
     * Get all the productQuatations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductQuatationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductQuatations");
        return productQuatationRepository.findAll(pageable).map(productQuatationMapper::toDto);
    }

    /**
     *  Get all the productQuatations where QuatationDetails is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProductQuatationDTO> findAllWhereQuatationDetailsIsNull() {
        log.debug("Request to get all productQuatations where QuatationDetails is null");
        return StreamSupport
            .stream(productQuatationRepository.findAll().spliterator(), false)
            .filter(productQuatation -> productQuatation.getQuatationDetails() == null)
            .map(productQuatationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one productQuatation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductQuatationDTO> findOne(Long id) {
        log.debug("Request to get ProductQuatation : {}", id);
        return productQuatationRepository.findById(id).map(productQuatationMapper::toDto);
    }

    /**
     * Delete the productQuatation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductQuatation : {}", id);
        productQuatationRepository.deleteById(id);
    }
}
