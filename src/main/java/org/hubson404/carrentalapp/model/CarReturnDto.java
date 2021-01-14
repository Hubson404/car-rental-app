package org.hubson404.carrentalapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarReturnDto {

    private Long id;

    private EmployeeDto employee;

    private String returnDate;

    private CarReservationDto reservation;

    private Long extraCharge;

    private String comments;
}
