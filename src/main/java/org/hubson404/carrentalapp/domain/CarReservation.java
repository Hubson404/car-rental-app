package org.hubson404.carrentalapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "car_reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarReservation {

    @Id
    @GeneratedValue
    private Long id;

    @CreationTimestamp
    private Instant reservationCreateDate;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Car car;
    private Instant rentalStartingDate;
    private Instant returnDate;

    @OneToOne
    private Department carRentalDepartment; //stream check latest car reservations for carReturnDepartment

    @OneToOne
    private Department carReturnDepartment;
    private Double totalCost;

}
