package org.hubson404.carrentalapp.model.mappers;

import org.hubson404.carrentalapp.domain.Car;
import org.hubson404.carrentalapp.model.CarDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(uses = {DepartmentMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CarMapper {

    Car toCar(CarDto carDto);

    CarDto toCarDto(Car car);
}
