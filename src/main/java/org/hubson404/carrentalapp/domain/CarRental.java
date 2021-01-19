package org.hubson404.carrentalapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Entity(name = "car_rentals")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarRental {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Employee employee;
    @OneToOne
    private CarReservation reservation;

    private LocalDate rentalDate;
    private String comment;

}
