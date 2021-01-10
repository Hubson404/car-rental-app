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

    private CustomerDTO customer;
    private CarDTO car;

    private String rentalStartingDate;
    private String returnDate;

    private DepartmentDTO carRentalDepartment;
    private DepartmentDTO carReturnDepartment;

    private Double totalCost;

    private boolean canceled;
}
