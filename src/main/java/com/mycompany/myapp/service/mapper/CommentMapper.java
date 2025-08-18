package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Card;
import com.mycompany.myapp.domain.Comment;
import com.mycompany.myapp.service.dto.CardDTO;
import com.mycompany.myapp.service.dto.CommentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Comment} and its DTO {@link CommentDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {
    @Mapping(target = "card", source = "card", qualifiedByName = "cardId")
    CommentDTO toDto(Comment s);

    @Named("cardId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CardDTO toDtoCardId(Card card);
}
