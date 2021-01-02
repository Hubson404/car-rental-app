package org.hubson404.carrentalapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {

    private Long id;

    private String address;

    private List<EmployeeDTO> employees;

    private List<CarDTO> cars;
}
