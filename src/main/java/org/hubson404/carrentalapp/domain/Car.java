package org.hubson404.carrentalapp.domain;

import lombok.*;
import org.hubson404.carrentalapp.domain.enums.CarBodyColor;
import org.hubson404.carrentalapp.domain.enums.CarBodyType;
import org.hubson404.carrentalapp.domain.enums.CarStatus;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cars")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {

    @Id
    @GeneratedValue
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

    //prawdopodobnie do usunięcia
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "car")
    private List<CarReservation> reservations;

    public Car(Object o, String mmm, String bbb, CarBodyType coupe, int i, CarBodyColor white, long l, CarStatus available, double v, Department warsaw) {
    }
}
