package org.hubson404.carrentalapp.services;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.CarReservation;
import org.hubson404.carrentalapp.domain.Department;
import org.hubson404.carrentalapp.exceptions.CarReservationNotFoundException;
import org.hubson404.carrentalapp.model.CarReservationDto;
import org.hubson404.carrentalapp.model.mappers.CarReservationMapper;
import org.hubson404.carrentalapp.repositories.CarReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CarReservationService {

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
        LocalDate rentalStartingDate = reservation.getRentalStartingDate();
        LocalDate returnDate = reservation.getReturnDate();

        long rentalDurationInDays = ChronoUnit.DAYS.between(rentalStartingDate, returnDate);

        Department rentalDepartment = reservation.getCarRentalDepartment();
        Department returnDepartment = reservation.getCarReturnDepartment();

        if (Objects.equals(rentalDepartment, returnDepartment)) {
            totalCost += differentReturnDepartmentCost;
        }

        return totalCost + rentalDurationInDays * costPerDay;
    }

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

    public void deleteReservationById(Long id) {
        carReservationRepository.findById(id)
                .ifPresentOrElse(r -> carReservationRepository.deleteById(r.getId()),
                        () -> {
                            throw new CarReservationNotFoundException(id);
                        });
    }

    public CarReservationDto cancelReservation(Long id) {

        long penalisedDaysBeforeRentalStart = 2L;

        CarReservation carReservation = carReservationRepository.findById(id)
                .orElseThrow(() -> new CarReservationNotFoundException(id));

        LocalDate rentalStartingDate = carReservation.getRentalStartingDate();
        LocalDate penalisedCancelDateDeadline = rentalStartingDate.minus(penalisedDaysBeforeRentalStart, ChronoUnit.DAYS);

        if (penalisedCancelDateDeadline.isAfter(LocalDate.now())) {
            carReservation.setTotalCost(0d);
        } else {
            Double totalCost = carReservation.getTotalCost();
            carReservation.setTotalCost(0.2 * totalCost);
        }

        carReservation.setCanceled(true);

        return carReservationMapper.toCarReservationDto(carReservation);
    }

}
