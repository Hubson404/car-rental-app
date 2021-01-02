package org.hubson404.carrentalapp.model.mappers;

import org.hubson404.carrentalapp.domain.Car;
import org.hubson404.carrentalapp.model.CarDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CarMapper {

    Car toCar(CarDTO carDTO);

    CarDTO toCarDTO(Car car);
}
