package org.hubson404.carrentalapp.reservation;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.CarReservation;
import org.hubson404.carrentalapp.exceptions.CarReservationNotFoundException;
import org.hubson404.carrentalapp.model.CarReservationDto;
import org.hubson404.carrentalapp.model.mappers.CarReservationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CarReservationFetchService {

    private final CarReservationRepository carReservationRepository;
    private final CarReservationMapper carReservationMapper;

    public List<CarReservationDto> findAll() {
        List<CarReservation> reservations = carReservationRepository.findAll();
        return reservations.stream().map(carReservationMapper::toCarReservationDto).collect(Collectors.toList());
    }

    public List<CarReservationDto> findReservationsByCarId(Long carId) {
        List<CarReservation> reservations = carReservationRepository.findCarReservationByCarId(carId);
        return reservations.stream().map(carReservationMapper::toCarReservationDto).collect(Collectors.toList());
    }

    public CarReservationDto findReservationById(Long id) {
        CarReservation carReservation = carReservationRepository.findById(id)
                .orElseThrow(() -> new CarReservationNotFoundException(id));
        return carReservationMapper.toCarReservationDto(carReservation);
    }


}
