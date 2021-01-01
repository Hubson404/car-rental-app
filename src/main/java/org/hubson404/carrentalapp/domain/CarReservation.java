package org.hubson404.carrentalapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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

    @OneToOne
    private Customer customer;

    @OneToOne
    private Car car;
    private Instant rentalStartingDate;
    private Instant returnDate;

    @OneToOne
    private Department carHireDepartment; //stream check latest car reservations for carReturnDepartment

    @OneToOne
    private Department carReturnDepartment;
    private Double totalCost;

}
