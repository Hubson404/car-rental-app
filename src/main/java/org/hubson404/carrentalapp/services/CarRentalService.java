package org.hubson404.carrentalapp.services;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.CarRental;
import org.hubson404.carrentalapp.domain.CarReservation;
import org.hubson404.carrentalapp.domain.Employee;
import org.hubson404.carrentalapp.exceptions.CarRentalNotFoundException;
import org.hubson404.carrentalapp.exceptions.CarReservationNotFoundException;
import org.hubson404.carrentalapp.exceptions.EmployeeNotFoundException;
import org.hubson404.carrentalapp.model.CarRentalDto;
import org.hubson404.carrentalapp.model.mappers.CarRentalMapper;
import org.hubson404.carrentalapp.repositories.CarRentalRepository;
import org.hubson404.carrentalapp.repositories.CarReservationRepository;
import org.hubson404.carrentalapp.repositories.EmployeeRepository;
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
        Employee employee = findEmployeeById(wrapper.getEmployeeId());
        CarReservation carReservation = findResercaationById(wrapper.getCarReservationId());
        final CarRental carRental = createCarRental(employee, carReservation, wrapper.getComments());
        CarRental savedCarRental = carRentalRepository.save(carRental);
        return carRentalMapper.toCarRentalDto(savedCarRental);
    }

    private Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id)
            .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    private CarReservation findResercaationById(Long id) {
        return carReservationRepository.findById(id)
            .orElseThrow(() -> new CarReservationNotFoundException(id));
    }

    private CarRental createCarRental(final Employee employee, final CarReservation carReservation, String comments) {
        CarRental carRental = new CarRental();
        carRental.setEmployee(employee);
        carRental.setReservation(carReservation);
        carRental.setRentalDate(carReservation.getRentalStartingDate());
        carRental.setComment(comments);
    }

    public CarRentalWrapper findAll() {
        List<CarRental> all = carRentalRepository.findAll();
        List<CarRentalDto> collect = all.stream().map(carRentalMapper::toCarRentalDto).collect(Collectors.toList());
        CarRentalWrapper carRentalWrapper = new CarRentalWrapper();
        carRentalWrapper.setCarRentals(collect);
        return carRentalWrapper;

    }

    public CarRentalDto findById(Long id) {
        CarRental carRental = carRentalRepository.findById(id).orElseThrow(() -> new CarRentalNotFoundException(id));
        return carRentalMapper.toCarRentalDto(carRental);
    }
}
