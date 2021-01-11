package org.hubson404.carrentalapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hubson404.carrentalapp.domain.enums.CarBodyColor;
import org.hubson404.carrentalapp.domain.enums.CarBodyType;
import org.hubson404.carrentalapp.domain.enums.CarStatus;
import org.hubson404.carrentalapp.model.validators.EnumValue;
import org.hubson404.carrentalapp.model.validators.UpperCase;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {

    private Long id;

    @NotNull(message = "Value is mandatory")
    @Length(min = 1, message = "Input must be 1 characters or longer")
    private String model;

    @NotNull(message = "Value is mandatory")
    @Length(min = 2, message = "Input must be 2 characters or longer")
    private String brand;

    @NotNull(message = "Value is mandatory")
    @UpperCase
    @EnumValue(
            enumClass = CarBodyType.class
    )
    private String carBodyType;

    @NotNull(message = "Value is mandatory")
    @Min(value = 1950, message = "Production year must be later than 1950")
    private Integer productionYear;

    @NotNull(message = "Value is mandatory")
    @UpperCase
    @EnumValue(
            enumClass = CarBodyColor.class
    )
    private String color;

    @Min(value = 0, message = "Value must be 0 or greater")
    private Long mileage;

    @NotNull(message = "Value is mandatory")
    @UpperCase
    @EnumValue(
            enumClass = CarStatus.class
    )
    private String carStatus;

    @NotNull(message = "Value is mandatory")
    @Min(value = 0, message = "Value must be 0 or greater")
    private Double costPerDay;

    @NotNull(message = "Value is mandatory")
    private DepartmentDto department;
}
