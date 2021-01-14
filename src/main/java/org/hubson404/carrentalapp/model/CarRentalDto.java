package org.hubson404.carrentalapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarRentalDto {

    private Long id;

    private EmployeeDto employee;

    private String rentalDate;

    private CarReservationDto reservation;

    private String comment;

}
