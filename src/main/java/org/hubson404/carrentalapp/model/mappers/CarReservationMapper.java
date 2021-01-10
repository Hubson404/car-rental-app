package org.hubson404.carrentalapp.model.mappers;

import org.hubson404.carrentalapp.domain.CarReservation;
import org.hubson404.carrentalapp.model.CarReservationDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(uses = {CarMapper.class, EmployeeMapper.class, DepartmentMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CarReservationMapper {

    CarReservation toCarReservation(CarReservationDTO carReservationDTO);

    CarReservationDTO toCarReservationDTO(CarReservation carReservation);
}
