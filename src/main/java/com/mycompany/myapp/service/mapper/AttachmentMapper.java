package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Attachment;
import com.mycompany.myapp.domain.Card;
import com.mycompany.myapp.service.dto.AttachmentDTO;
import com.mycompany.myapp.service.dto.CardDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Attachment} and its DTO {@link AttachmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface AttachmentMapper extends EntityMapper<AttachmentDTO, Attachment> {
    @Mapping(target = "card", source = "card", qualifiedByName = "cardId")
    AttachmentDTO toDto(Attachment s);

    @Named("cardId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CardDTO toDtoCardId(Card card);
}
