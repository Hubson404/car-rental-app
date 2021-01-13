package org.hubson404.carrentalapp.services;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.CarRental;
import org.hubson404.carrentalapp.domain.CarReservation;
import org.hubson404.carrentalapp.domain.Employee;
import org.hubson404.carrentalapp.employee.EmployeeRepository;
import org.hubson404.carrentalapp.exceptions.CarReservationNotFoundException;
import org.hubson404.carrentalapp.exceptions.EmployeeNotFoundException;
import org.hubson404.carrentalapp.model.CarRentalDto;
import org.hubson404.carrentalapp.model.mappers.CarRentalMapper;
import org.hubson404.carrentalapp.repositories.CarRentalRepository;
import org.hubson404.carrentalapp.reservation.CarReservationRepository;
import org.hubson404.carrentalapp.wrappers.CarRentalCreateWrapper;
import org.hubson404.carrentalapp.wrappers.CarRentalWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CarRentalService {

    private final CarRentalRepository carRentalRepository;
    private final CarReservationRepository carReservationRepository;
    private final EmployeeRepository employeeRepository;
    private final CarRentalMapper carRentalMapper;


    public CarRentalDto createCarRental(CarRentalCreateWrapper wrapper) {

        Employee employee = employeeRepository.findById(wrapper.getEmployeeId())
                .orElseThrow(() -> new EmployeeNotFoundException(wrapper.getEmployeeId()));
        CarReservation carReservation = carReservationRepository.findById(wrapper.getCarReservationId())
                .orElseThrow(() -> new CarReservationNotFoundException(wrapper.getCarReservationId()));

        CarRental carRental = new CarRental();
        carRental.setEmployee(employee);
        carRental.setReservation(carReservation);
        carRental.setRentalDate(carReservation.getRentalStartingDate());
        carRental.setComment(wrapper.getComments());

        CarRental savedCarRental = carRentalRepository.save(carRental);

        return carRentalMapper.toCarReservationDto(savedCarRental);
    }

    public CarRentalWrapper findAll() {
        List<CarRental> all = carRentalRepository.findAll();
        List<CarRentalDto> collect = all.stream().map(carRentalMapper::toCarReservationDto).collect(Collectors.toList());
        CarRentalWrapper carRentalWrapper = new CarRentalWrapper();
        carRentalWrapper.setCarRentals(collect);
        return carRentalWrapper;

    }
}
