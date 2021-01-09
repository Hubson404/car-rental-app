package org.hubson404.carrentalapp.CarReservation;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.CarReservation;
import org.hubson404.carrentalapp.model.CarReservationDTO;
import org.hubson404.carrentalapp.model.mappers.CarReservationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CarReservationCreateService {

    private final CarReservationRepository carReservationRepository;
    private final CarReservationMapper carReservationMapper;


    public CarReservation createReservation(CarReservationDTO carReservationDTO) {
        return carReservationRepository.save(carReservationMapper.toCarReservation(carReservationDTO));
    }
}
