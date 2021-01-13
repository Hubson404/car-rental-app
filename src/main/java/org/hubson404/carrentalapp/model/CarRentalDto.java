package org.hubson404.carrentalapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hubson404.carrentalapp.domain.Employee;

import javax.persistence.OneToOne;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarRentalDto {

    private Long id;

    private Employee employee;

    private String rentalDate;

    @OneToOne
    private CarReservationDto reservation;

    private String comment;

}
