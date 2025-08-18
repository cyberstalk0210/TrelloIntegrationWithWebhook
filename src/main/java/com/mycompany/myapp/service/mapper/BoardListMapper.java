package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Board;
import com.mycompany.myapp.domain.BoardList;
import com.mycompany.myapp.service.dto.BoardDTO;
import com.mycompany.myapp.service.dto.BoardListDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BoardList} and its DTO {@link BoardListDTO}.
 */
@Mapper(componentModel = "spring")
public interface BoardListMapper extends EntityMapper<BoardListDTO, BoardList> {
    @Mapping(target = "board", source = "board", qualifiedByName = "boardId")
    BoardListDTO toDto(BoardList s);

    @Named("boardId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BoardDTO toDtoBoardId(Board board);
}
