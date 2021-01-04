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

    @Id
    @GeneratedValue
    private Long id;

    private String address;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "department",
            fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE)
    private Set<Employee> employees;

    @OneToMany(mappedBy = "department")
    private Set<Car> cars;

}
