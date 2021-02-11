package org.hubson404.carrentalapp.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "car_returns")
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
    private LocalDate returnDate;

    @OneToOne
    private CarReservation reservation;

    private Long extraCharge;
    private String comments;

}
