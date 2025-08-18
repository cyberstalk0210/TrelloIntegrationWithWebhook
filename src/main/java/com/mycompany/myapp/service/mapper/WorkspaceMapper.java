package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Workspace;
import com.mycompany.myapp.service.dto.WorkspaceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Workspace} and its DTO {@link WorkspaceDTO}.
 */
@Mapper(componentModel = "spring")
public interface WorkspaceMapper extends EntityMapper<WorkspaceDTO, Workspace> {}
