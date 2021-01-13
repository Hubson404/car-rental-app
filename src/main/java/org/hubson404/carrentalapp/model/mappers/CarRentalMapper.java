package org.hubson404.carrentalapp.model.mappers;

import org.hubson404.carrentalapp.domain.CarRental;
import org.hubson404.carrentalapp.model.CarRentalDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(uses = {CarReservationMapper.class, EmployeeMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CarRentalMapper {

    CarRental toCarReservation(CarRentalDto carReservationDto);

    CarRentalDto toCarReservationDto(CarRental carReservation);
}
