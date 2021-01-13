package org.hubson404.carrentalapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarReservationDto {

    private Long id;

    @NotNull(message = "Provide customer 'id'")
    private Long customer;

    @NotNull(message = "Provide rented car 'id'")
    private Long car;

    @NotNull(message = "Value is mandatory")
    private String rentalStartingDate;

    @NotNull(message = "Value is mandatory")
    private String returnDate;

    @NotNull(message = "Provide department 'id'")
    private Long carRentalDepartment;

    @NotNull(message = "Provide department 'id'")
    private Long carReturnDepartment;

    private Double totalCost;

    private boolean canceled;
}
