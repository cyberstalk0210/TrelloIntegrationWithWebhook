package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Card;
import com.mycompany.myapp.domain.Label;
import com.mycompany.myapp.service.dto.CardDTO;
import com.mycompany.myapp.service.dto.LabelDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Label} and its DTO {@link LabelDTO}.
 */
@Mapper(componentModel = "spring")
public interface LabelMapper extends EntityMapper<LabelDTO, Label> {
    @Mapping(target = "cards", source = "cards", qualifiedByName = "cardIdSet")
    LabelDTO toDto(Label s);

    @Mapping(target = "cards", ignore = true)
    @Mapping(target = "removeCards", ignore = true)
    Label toEntity(LabelDTO labelDTO);

    @Named("cardId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CardDTO toDtoCardId(Card card);

    @Named("cardIdSet")
    default Set<CardDTO> toDtoCardIdSet(Set<Card> card) {
        return card.stream().map(this::toDtoCardId).collect(Collectors.toSet());
    }
}
