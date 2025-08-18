package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Workspace;
import com.mycompany.myapp.repository.WorkspaceRepository;
import com.mycompany.myapp.service.dto.WorkspaceDTO;
import com.mycompany.myapp.service.mapper.WorkspaceMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Workspace}.
 */
@Service
@Transactional
public class WorkspaceService {

    private static final Logger LOG = LoggerFactory.getLogger(WorkspaceService.class);

    private final WorkspaceRepository workspaceRepository;

    private final WorkspaceMapper workspaceMapper;

    public WorkspaceService(WorkspaceRepository workspaceRepository, WorkspaceMapper workspaceMapper) {
        this.workspaceRepository = workspaceRepository;
        this.workspaceMapper = workspaceMapper;
    }

    /**
     * Save a workspace.
     *
     * @param workspaceDTO the entity to save.
     * @return the persisted entity.
     */
    public WorkspaceDTO save(WorkspaceDTO workspaceDTO) {
        LOG.debug("Request to save Workspace : {}", workspaceDTO);
        Workspace workspace = workspaceMapper.toEntity(workspaceDTO);
        workspace = workspaceRepository.save(workspace);
        return workspaceMapper.toDto(workspace);
    }

    /**
     * Update a workspace.
     *
     * @param workspaceDTO the entity to save.
     * @return the persisted entity.
     */
    public WorkspaceDTO update(WorkspaceDTO workspaceDTO) {
        LOG.debug("Request to update Workspace : {}", workspaceDTO);
        Workspace workspace = workspaceMapper.toEntity(workspaceDTO);
        workspace = workspaceRepository.save(workspace);
        return workspaceMapper.toDto(workspace);
    }

    /**
     * Partially update a workspace.
     *
     * @param workspaceDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<WorkspaceDTO> partialUpdate(WorkspaceDTO workspaceDTO) {
        LOG.debug("Request to partially update Workspace : {}", workspaceDTO);

        return workspaceRepository
            .findById(workspaceDTO.getId())
            .map(existingWorkspace -> {
                workspaceMapper.partialUpdate(existingWorkspace, workspaceDTO);

                return existingWorkspace;
            })
            .map(workspaceRepository::save)
            .map(workspaceMapper::toDto);
    }

    /**
     * Get all the workspaces.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<WorkspaceDTO> findAll() {
        LOG.debug("Request to get all Workspaces");
        return workspaceRepository.findAll().stream().map(workspaceMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one workspace by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WorkspaceDTO> findOne(Long id) {
        LOG.debug("Request to get Workspace : {}", id);
        return workspaceRepository.findById(id).map(workspaceMapper::toDto);
    }

    /**
     * Delete the workspace by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Workspace : {}", id);
        workspaceRepository.deleteById(id);
    }
}
