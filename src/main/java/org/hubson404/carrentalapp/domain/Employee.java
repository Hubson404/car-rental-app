package org.hubson404.carrentalapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hubson404.carrentalapp.domain.enums.EmployeePosition;

import javax.persistence.*;

@Entity(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private EmployeePosition position;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

}
