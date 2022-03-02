package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.TranferDetailsApprovals;
import com.mycompany.myapp.repository.TranferDetailsApprovalsRepository;
import com.mycompany.myapp.service.dto.TranferDetailsApprovalsDTO;
import com.mycompany.myapp.service.mapper.TranferDetailsApprovalsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TranferDetailsApprovals}.
 */
@Service
@Transactional
public class TranferDetailsApprovalsService {

    private final Logger log = LoggerFactory.getLogger(TranferDetailsApprovalsService.class);

    private final TranferDetailsApprovalsRepository tranferDetailsApprovalsRepository;

    private final TranferDetailsApprovalsMapper tranferDetailsApprovalsMapper;

    public TranferDetailsApprovalsService(
        TranferDetailsApprovalsRepository tranferDetailsApprovalsRepository,
        TranferDetailsApprovalsMapper tranferDetailsApprovalsMapper
    ) {
        this.tranferDetailsApprovalsRepository = tranferDetailsApprovalsRepository;
        this.tranferDetailsApprovalsMapper = tranferDetailsApprovalsMapper;
    }

    /**
     * Save a tranferDetailsApprovals.
     *
     * @param tranferDetailsApprovalsDTO the entity to save.
     * @return the persisted entity.
     */
    public TranferDetailsApprovalsDTO save(TranferDetailsApprovalsDTO tranferDetailsApprovalsDTO) {
        log.debug("Request to save TranferDetailsApprovals : {}", tranferDetailsApprovalsDTO);
        TranferDetailsApprovals tranferDetailsApprovals = tranferDetailsApprovalsMapper.toEntity(tranferDetailsApprovalsDTO);
        tranferDetailsApprovals = tranferDetailsApprovalsRepository.save(tranferDetailsApprovals);
        return tranferDetailsApprovalsMapper.toDto(tranferDetailsApprovals);
    }

    /**
     * Partially update a tranferDetailsApprovals.
     *
     * @param tranferDetailsApprovalsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TranferDetailsApprovalsDTO> partialUpdate(TranferDetailsApprovalsDTO tranferDetailsApprovalsDTO) {
        log.debug("Request to partially update TranferDetailsApprovals : {}", tranferDetailsApprovalsDTO);

        return tranferDetailsApprovalsRepository
            .findById(tranferDetailsApprovalsDTO.getId())
            .map(existingTranferDetailsApprovals -> {
                tranferDetailsApprovalsMapper.partialUpdate(existingTranferDetailsApprovals, tranferDetailsApprovalsDTO);

                return existingTranferDetailsApprovals;
            })
            .map(tranferDetailsApprovalsRepository::save)
            .map(tranferDetailsApprovalsMapper::toDto);
    }

    /**
     * Get all the tranferDetailsApprovals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TranferDetailsApprovalsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TranferDetailsApprovals");
        return tranferDetailsApprovalsRepository.findAll(pageable).map(tranferDetailsApprovalsMapper::toDto);
    }

    /**
     * Get one tranferDetailsApprovals by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TranferDetailsApprovalsDTO> findOne(Long id) {
        log.debug("Request to get TranferDetailsApprovals : {}", id);
        return tranferDetailsApprovalsRepository.findById(id).map(tranferDetailsApprovalsMapper::toDto);
    }

    /**
     * Delete the tranferDetailsApprovals by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TranferDetailsApprovals : {}", id);
        tranferDetailsApprovalsRepository.deleteById(id);
    }
}
