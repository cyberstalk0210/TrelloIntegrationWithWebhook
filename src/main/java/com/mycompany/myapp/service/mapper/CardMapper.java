package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Board;
import com.mycompany.myapp.domain.BoardList;
import com.mycompany.myapp.domain.Card;
import com.mycompany.myapp.domain.Label;
import com.mycompany.myapp.service.dto.BoardDTO;
import com.mycompany.myapp.service.dto.BoardListDTO;
import com.mycompany.myapp.service.dto.CardDTO;
import com.mycompany.myapp.service.dto.LabelDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Card} and its DTO {@link CardDTO}.
 */
@Mapper(componentModel = "spring")
public interface CardMapper extends EntityMapper<CardDTO, Card> {
    @Mapping(target = "labels", source = "labels", qualifiedByName = "labelIdSet")
    @Mapping(target = "board", source = "board", qualifiedByName = "boardId")
    @Mapping(target = "boardList", source = "boardList", qualifiedByName = "boardListId")
    CardDTO toDto(Card s);

    @Mapping(target = "removeLabels", ignore = true)
    Card toEntity(CardDTO cardDTO);

    @Named("labelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LabelDTO toDtoLabelId(Label label);

    @Named("labelIdSet")
    default Set<LabelDTO> toDtoLabelIdSet(Set<Label> label) {
        return label.stream().map(this::toDtoLabelId).collect(Collectors.toSet());
    }

    @Named("boardId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BoardDTO toDtoBoardId(Board board);

    @Named("boardListId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BoardListDTO toDtoBoardListId(BoardList boardList);
}
