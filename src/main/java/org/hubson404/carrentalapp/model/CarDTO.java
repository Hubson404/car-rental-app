package org.hubson404.carrentalapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {

    private Long id;
    private String model;
    private String brand;
    private String carBodyType;
    private Integer productionYear;
    private String color;
    private Long mileage;
    private String carStatus;
    private Double costPerDay;
    private DepartmentDTO department;
}
