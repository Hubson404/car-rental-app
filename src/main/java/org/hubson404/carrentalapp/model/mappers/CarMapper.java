package org.hubson404.carrentalapp.model.mappers;

import org.hubson404.carrentalapp.domain.Car;
import org.hubson404.carrentalapp.model.CarDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CarMapper {

    @Mapping(target = "department", ignore = true)
    Car toCar(CarDTO carDTO);

    @InheritInverseConfiguration
    CarDTO toCarDTO(Car car);
}
