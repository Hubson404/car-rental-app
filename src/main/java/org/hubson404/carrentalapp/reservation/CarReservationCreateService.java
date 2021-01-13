package org.hubson404.carrentalapp.reservation;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.CarReservation;
import org.hubson404.carrentalapp.domain.Department;
import org.hubson404.carrentalapp.model.CarReservationDto;
import org.hubson404.carrentalapp.model.mappers.CarReservationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CarReservationCreateService {

    private final CarReservationRepository carReservationRepository;
    private final CarReservationMapper carReservationMapper;


    public CarReservationDto createReservation(CarReservationDto carReservationDTO) {
        CarReservation carReservation = carReservationMapper.toCarReservation(carReservationDTO);
        carReservation.setTotalCost(calculateTotalCost(carReservation));
        CarReservation savedReservation = carReservationRepository.save(carReservation);
        return carReservationMapper.toCarReservationDto(savedReservation);
    }

    private double calculateTotalCost(CarReservation reservation) {

        double totalCost = 0d;
        int differentReturnDepartmentCost = 50;

        Double costPerDay = reservation.getCar().getCostPerDay();
        LocalDateTime rentalStartingDate = reservation.getRentalStartingDate();
        LocalDateTime returnDate = reservation.getReturnDate();

        long rentalDurationInDays = ChronoUnit.DAYS.between(rentalStartingDate, returnDate);

        Department rentalDepartment = reservation.getCarRentalDepartment();
        Department returnDepartment = reservation.getCarReturnDepartment();

        if (Objects.equals(rentalDepartment, returnDepartment)) {
            totalCost += differentReturnDepartmentCost;
        }

        return totalCost + rentalDurationInDays * costPerDay;
    }


}
