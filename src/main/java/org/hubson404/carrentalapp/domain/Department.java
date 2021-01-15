package org.hubson404.carrentalapp.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "departments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {

    @ManyToOne
    @JoinColumn(name = "carRentalCompany_id")
    private CarRentalCompany carRentalCompany;

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String address;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "department",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Employee> employees;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "department",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Car> cars;

    public Department(Long id, String address, Set<Employee> employees, Set<Car> cars) {
        this.id = id;
        this.address = address;
        this.employees = employees;
        this.cars = cars;
    }

}
