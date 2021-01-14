package org.hubson404.carrentalapp.model.mappers;

import org.hubson404.carrentalapp.domain.CarRental;
import org.hubson404.carrentalapp.model.CarRentalDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(uses = {CarReservationMapper.class, EmployeeMapper.class, DepartmentMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CarRentalMapper {

    CarRental toCarRental(CarRentalDto carReservationDto);

    CarRentalDto toCarRentalDto(CarRental carReservation);
}
