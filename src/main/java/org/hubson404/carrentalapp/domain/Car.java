package org.hubson404.carrentalapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hubson404.carrentalapp.domain.enums.CarBodyColor;
import org.hubson404.carrentalapp.domain.enums.CarBodyType;
import org.hubson404.carrentalapp.domain.enums.CarStatus;

import javax.persistence.*;

@Entity(name = "cars")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;

    private String brand;

    @Enumerated(EnumType.STRING)
    private CarBodyType carBodyType;

    private Integer productionYear;

    @Enumerated(EnumType.STRING)
    private CarBodyColor color;

    private Long mileage;

    @Enumerated(EnumType.STRING)
    private CarStatus carStatus;

    private Double costPerDay;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

}
