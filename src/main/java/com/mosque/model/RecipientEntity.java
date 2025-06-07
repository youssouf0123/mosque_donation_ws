package com.mosque.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.Comment;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "recipient")
public class RecipientEntity extends AbstractEntity implements Serializable {

//	LocalDate (from java.time) is type-safe and represents a date without time.
//	It's JPA-compliant and maps well to SQL DATE.
//	Avoids problems with parsing and formatting strings.

    @NotEmpty
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    private Gender gender;
    @Column(name = "phone_number", length = 20, nullable = false)
//	@Pattern(regexp = "\\+?[0-9\\-\\s]+", message = "Invalid phone number") // todo: validation
    private String phoneNumber;
    @Comment("The Status of the Recipient")
    private Status status;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {INDIGENT, WIDOW, ORPHAN}

    public enum Gender {MALE, FEMALE}
}