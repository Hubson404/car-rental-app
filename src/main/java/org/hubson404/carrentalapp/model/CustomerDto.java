package org.hubson404.carrentalapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private Long id;

    @NotNull(message = "Value is mandatory")
    @Length(min = 2, message = "Input must be 2 characters or longer")
    private String firstName;

    @NotNull(message = "Value is mandatory")
    @Length(min = 2, message = "Input must be 2 characters or longer")
    private String lastName;

    @NotNull(message = "Value is mandatory")
    @Email(message = "Value must be formatted properly")
    private String email;

    @NotNull(message = "Value is mandatory")
    @Length(min = 2, message = "Input must be 2 characters or longer")
    private String address;
}
