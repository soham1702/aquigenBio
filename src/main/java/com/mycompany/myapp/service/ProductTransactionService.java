package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ProductTransaction;
import com.mycompany.myapp.repository.ProductTransactionRepository;
import com.mycompany.myapp.service.dto.ProductTransactionDTO;
import com.mycompany.myapp.service.mapper.ProductTransactionMapper;
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
 * Service Implementation for managing {@link ProductTransaction}.
 */
@Service
@Transactional
public class ProductTransactionService {

    private final Logger log = LoggerFactory.getLogger(ProductTransactionService.class);

    private final ProductTransactionRepository productTransactionRepository;

    private final ProductTransactionMapper productTransactionMapper;

    public ProductTransactionService(
        ProductTransactionRepository productTransactionRepository,
        ProductTransactionMapper productTransactionMapper
    ) {
        this.productTransactionRepository = productTransactionRepository;
        this.productTransactionMapper = productTransactionMapper;
    }

    /**
     * Save a productTransaction.
     *
     * @param productTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductTransactionDTO save(ProductTransactionDTO productTransactionDTO) {
        log.debug("Request to save ProductTransaction : {}", productTransactionDTO);
        ProductTransaction productTransaction = productTransactionMapper.toEntity(productTransactionDTO);
        productTransaction = productTransactionRepository.save(productTransaction);
        return productTransactionMapper.toDto(productTransaction);
    }

    /**
     * Partially update a productTransaction.
     *
     * @param productTransactionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductTransactionDTO> partialUpdate(ProductTransactionDTO productTransactionDTO) {
        log.debug("Request to partially update ProductTransaction : {}", productTransactionDTO);

        return productTransactionRepository
            .findById(productTransactionDTO.getId())
            .map(existingProductTransaction -> {
                productTransactionMapper.partialUpdate(existingProductTransaction, productTransactionDTO);

                return existingProductTransaction;
            })
            .map(productTransactionRepository::save)
            .map(productTransactionMapper::toDto);
    }

    /**
     * Get all the productTransactions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductTransactionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductTransactions");
        return productTransactionRepository.findAll(pageable).map(productTransactionMapper::toDto);
    }

    /**
     * Get all the productTransactions with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ProductTransactionDTO> findAllWithEagerRelationships(Pageable pageable) {
        return productTransactionRepository.findAllWithEagerRelationships(pageable).map(productTransactionMapper::toDto);
    }

    /**
     *  Get all the productTransactions where SecurityUser is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProductTransactionDTO> findAllWhereSecurityUserIsNull() {
        log.debug("Request to get all productTransactions where SecurityUser is null");
        return StreamSupport
            .stream(productTransactionRepository.findAll().spliterator(), false)
            .filter(productTransaction -> productTransaction.getSecurityUser() == null)
            .map(productTransactionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one productTransaction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductTransactionDTO> findOne(Long id) {
        log.debug("Request to get ProductTransaction : {}", id);
        return productTransactionRepository.findOneWithEagerRelationships(id).map(productTransactionMapper::toDto);
    }

    /**
     * Delete the productTransaction by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductTransaction : {}", id);
        productTransactionRepository.deleteById(id);
    }
}
