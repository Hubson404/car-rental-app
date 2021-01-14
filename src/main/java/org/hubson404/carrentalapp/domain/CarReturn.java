package org.hubson404.carrentalapp.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity(name = "car_returns")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarReturn {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Employee employee;
    private LocalDateTime returnDate;

    @OneToOne
    private CarReservation reservation;

    private Long extraCharge;
    private String comments;

}
