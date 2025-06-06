package com.mosque.mapper;

import com.mosque.dto.RecipientDTO;
import com.mosque.model.RecipientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring") // IMPORTANT: enables Spring injection
public interface RecipientMapper {
    RecipientMapper INSTANCE = Mappers.getMapper(RecipientMapper.class);

    RecipientDTO toDto(RecipientEntity entity);

    RecipientEntity toEntity(RecipientDTO recipient);

    List<RecipientDTO> toDto(List<RecipientEntity> entities);

    List<RecipientEntity> toEntity(List<RecipientDTO> recipients);
}