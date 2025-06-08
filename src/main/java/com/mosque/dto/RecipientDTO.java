package com.mosque.dto;

import com.mosque.model.RecipientEntity;

import java.time.LocalDate;

public record RecipientDTO(
        Long id,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        RecipientEntity.Gender gender,
        String phoneNumber,
        RecipientEntity.Status status
) {
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Long id;
        String firstName;
        String lastName;
        LocalDate dateOfBirth;
        RecipientEntity.Gender gender;
        String phoneNumber;
        RecipientEntity.Status status;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder dateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder gender(RecipientEntity.Gender gender) {
            this.gender = gender;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder status(RecipientEntity.Status status) {
            this.status = status;
            return this;
        }

        public RecipientDTO build() {
            return new RecipientDTO(id, firstName, lastName, dateOfBirth, gender, phoneNumber, status);
        }
    }
}