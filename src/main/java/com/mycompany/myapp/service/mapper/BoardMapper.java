package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Board;
import com.mycompany.myapp.domain.Workspace;
import com.mycompany.myapp.service.dto.BoardDTO;
import com.mycompany.myapp.service.dto.WorkspaceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Board} and its DTO {@link BoardDTO}.
 */
@Mapper(componentModel = "spring")
public interface BoardMapper extends EntityMapper<BoardDTO, Board> {
    @Mapping(target = "workspace", source = "workspace")
    BoardDTO toDto(Board s);

    @Named("workspaceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WorkspaceDTO toDtoWorkspaceId(Workspace workspace);
}
