package com.mosque.mapper;

import com.mosque.dto.RecipientRecord;
import com.mosque.model.RecipientEntity;

import java.util.List;
import java.util.stream.Collectors;

//@Mapper(componentModel = "spring") // IMPORTANT: enables Spring injection
public class RecipientRecordMapper {

    public static RecipientRecord toDto(RecipientEntity entity) {
        return RecipientRecord.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .dateOfBirth(entity.getDateOfBirth())
                .gender(entity.getGender())
                .phoneNumber(entity.getPhoneNumber())
                .status(entity.getStatus())
                .build();
    }

    public static List<RecipientRecord> toDto(List<RecipientEntity> entities) {
        return entities
                .stream()
                .map(RecipientRecordMapper::toDto)
                .collect(Collectors.toList());
    }

    public static RecipientEntity toEntity(RecipientRecord recipientRecord) {

        RecipientEntity recipient = new RecipientEntity();
        recipient.setId(recipientRecord.id());
        recipient.setFirstName(recipientRecord.firstName());
        recipient.setLastName(recipientRecord.lastName());
        recipient.setDateOfBirth(recipientRecord.dateOfBirth());
        recipient.setGender(recipientRecord.gender());
        recipient.setPhoneNumber(recipientRecord.phoneNumber());
        recipient.setStatus(recipientRecord.status());

        return recipient;
    }

//    List<RecipientEntity> toEntity(List<RecipientDTO> recipients);
}