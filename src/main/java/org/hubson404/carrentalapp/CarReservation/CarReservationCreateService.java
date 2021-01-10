package org.hubson404.carrentalapp.CarReservation;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.CarReservation;
import org.hubson404.carrentalapp.domain.Department;
import org.hubson404.carrentalapp.model.CarReservationDTO;
import org.hubson404.carrentalapp.model.mappers.CarReservationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CarReservationCreateService {

    private final CarReservationRepository carReservationRepository;
    private final CarReservationMapper carReservationMapper;


    public CarReservationDTO createReservation(CarReservationDTO carReservationDTO) {

        CarReservation carReservation = carReservationMapper.toCarReservation(carReservationDTO);
        carReservation.setTotalCost(calculateTotalCost(carReservation));

        CarReservation savedReservation = carReservationRepository.save(carReservation);
        return carReservationMapper.toCarReservationDTO(savedReservation);
    }

    private double calculateTotalCost(CarReservation reservation) {

        double totalCost = 0d;
        int differentReturnDepartmentCost = 50;

        Double costPerDay = reservation.getCar().getCostPerDay();
        Instant rentalStartingDate = reservation.getRentalStartingDate();
        Instant returnDate = reservation.getReturnDate();

        long rentalDurationInDays = ChronoUnit.DAYS.between(rentalStartingDate, returnDate);

        Department rentalDepartment = reservation.getCarRentalDepartment();
        Department returnDepartment = reservation.getCarReturnDepartment();

        if (Objects.equals(rentalDepartment, returnDepartment)) {
            totalCost += differentReturnDepartmentCost;
        }

        return totalCost + rentalDurationInDays * costPerDay;
    }


}
