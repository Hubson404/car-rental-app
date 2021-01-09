package org.hubson404.carrentalapp.model.mappers;

import org.hubson404.carrentalapp.domain.CarReservation;
import org.hubson404.carrentalapp.model.CarReservationDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CarReservationMapper {

    CarReservation toCarReservation(CarReservationDTO carReservationDTO);

    CarReservationDTO toCarReservationDTO(CarReservation carReservation);
}
