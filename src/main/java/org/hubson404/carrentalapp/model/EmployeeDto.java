package org.hubson404.carrentalapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hubson404.carrentalapp.domain.enums.EmployeePosition;
import org.hubson404.carrentalapp.model.validators.EnumValue;
import org.hubson404.carrentalapp.model.validators.UpperCase;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    private Long id;

    @NotNull(message = "Value is mandatory")
    @Length(min = 2, message = "Input must be 2 characters or longer")
    private String firstName;

    @NotNull(message = "Value is mandatory")
    @Length(min = 2, message = "Input must be 2 characters or longer")
    private String lastName;

    @UpperCase
    @EnumValue(
            enumClass = EmployeePosition.class
    )
    private String position;

    @NotNull(message = "Value is mandatory")
    private DepartmentDto department;
}
