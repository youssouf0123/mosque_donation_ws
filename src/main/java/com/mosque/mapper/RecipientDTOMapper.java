package com.mosque.mapper;

import com.mosque.dto.RecipientDTO;
import com.mosque.model.RecipientEntity;

import java.util.List;
import java.util.stream.Collectors;

//@Mapper(componentModel = "spring") // IMPORTANT: enables Spring injection
public class RecipientDTOMapper {

    public static RecipientDTO toDto(RecipientEntity entity) {
        return RecipientDTO.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .dateOfBirth(entity.getDateOfBirth())
                .gender(entity.getGender())
                .phoneNumber(entity.getPhoneNumber())
                .status(entity.getStatus())
                .build();
    }

    public static List<RecipientDTO> toDto(List<RecipientEntity> entities) {
        return entities
                .stream()
                .map(RecipientDTOMapper::toDto)
                .collect(Collectors.toList());
    }

    public static RecipientEntity toEntity(RecipientDTO recipientDTO) {

        RecipientEntity recipient = new RecipientEntity();
        recipient.setId(recipientDTO.id());
        recipient.setFirstName(recipientDTO.firstName());
        recipient.setLastName(recipientDTO.lastName());
        recipient.setDateOfBirth(recipientDTO.dateOfBirth());
        recipient.setGender(recipientDTO.gender());
        recipient.setPhoneNumber(recipientDTO.phoneNumber());
        recipient.setStatus(recipientDTO.status());

        return recipient;
    }

//    List<RecipientEntity> toEntity(List<RecipientDTO> recipients);
}