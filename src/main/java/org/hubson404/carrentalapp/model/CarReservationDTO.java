package org.hubson404.carrentalapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarReservationDTO {

    private Long id;
    private String reservationCreateDate;

    private CustomerDto customer;
    private CarDto car;

    private String rentalStartingDate;
    private String returnDate;

    private DepartmentDto carRentalDepartment;
    private DepartmentDto carReturnDepartment;

    private Double totalCost;

    private boolean canceled;
}
