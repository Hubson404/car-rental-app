package org.hubson404.carrentalapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "car_reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarReservation {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Car car;

    private LocalDate rentalStartingDate;
    private LocalDate returnDate;

    @OneToOne
    private Department carRentalDepartment; //stream check latest car reservations for carReturnDepartment

    private boolean canceled;

    @OneToOne
    private Department carReturnDepartment;
    private Double totalCost;

}
