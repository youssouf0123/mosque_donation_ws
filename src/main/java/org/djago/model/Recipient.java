package org.djago.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name = "recipient")
public class Recipient extends AbstractEntity implements Serializable {

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
    private String gender;
    @Column(name = "phone_number", length = 20, nullable = false)
//	@Pattern(regexp = "\\+?[0-9\\-\\s]+", message = "Invalid phone number") // todo: validation
    private String phoneNumber;
    @Comment("The Status of the Recipient")
    private Status status;

    enum Status {INDIGENT, WIDOW, ORPHAN}
}