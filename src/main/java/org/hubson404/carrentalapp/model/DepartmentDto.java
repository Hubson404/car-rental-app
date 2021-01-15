package org.hubson404.carrentalapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {

    @NotNull
    private CarRentalCompanyDto carRentalCompany;

    private Long id;

    @NotNull(message = "Value is mandatory")
    @Length(min = 2, message = "Input must be 2 characters or longer")
    private String address;

    public DepartmentDto(Long id, @NotNull(message = "Value is mandatory") @Length(min = 2, message = "Input must be 2 characters or longer") String address) {
        this.id = id;
        this.address = address;
    }

}
