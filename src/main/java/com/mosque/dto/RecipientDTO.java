package com.mosque.dto;

import com.mosque.model.RecipientEntity;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RecipientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private RecipientEntity.Gender gender;
    private String phoneNumber;
    private RecipientEntity.Status status;
}