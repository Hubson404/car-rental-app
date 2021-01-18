package org.hubson404.carrentalapp.services;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.CarRental;
import org.hubson404.carrentalapp.domain.CarReservation;
import org.hubson404.carrentalapp.domain.Employee;
import org.hubson404.carrentalapp.domain.enums.CarStatus;
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

    private final CarReservationRepository carReservationRepository;
    private final CarRentalRepository carRentalRepository;
    private final EmployeeRepository employeeRepository;
    private final CarRentalMapper carRentalMapper;

    public CarRentalDto createCarRental(CarRentalCreateWrapper wrapper) {
        Employee employee = getEmployeeFromRepository(wrapper.getEmployeeId());
        CarReservation carReservation = getCarReservationFromRepository(wrapper.getCarReservationId());
        carReservation.getCar().setCarStatus(CarStatus.RENTED);
        CarRental savedCarRental = saveCarRental(wrapper, employee, carReservation);
        return carRentalMapper.toCarRentalDto(savedCarRental);
    }

    public CarRentalDto findById(Long id) {
        CarRental carRental = carRentalRepository.findById(id).orElseThrow(() -> new CarRentalNotFoundException(id));
        return carRentalMapper.toCarRentalDto(carRental);
    }

    public CarRentalWrapper findAll() {
        List<CarRental> cars = carRentalRepository.findAll();
        return toCarRentalWrapper(cars);
    }

    private CarRental saveCarRental(CarRentalCreateWrapper wrapper, Employee employee, CarReservation carReservation) {
        CarRental carRental = new CarRental();
        carRental.setEmployee(employee);
        carRental.setReservation(carReservation);
        carRental.setRentalDate(carReservation.getRentalStartingDate());
        carRental.setComment(wrapper.getComments());
        return carRentalRepository.save(carRental);
    }

    private CarReservation getCarReservationFromRepository(Long carReservationId) {
        CarReservation carReservation = carReservationRepository.findById(carReservationId)
                .orElseThrow(() -> new CarReservationNotFoundException(carReservationId));
        carReservation.getCar().setCarStatus(CarStatus.UNAVAILABLE);
        return carReservation;
    }

    private Employee getEmployeeFromRepository(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    }

    private CarRentalWrapper toCarRentalWrapper(List<CarRental> cars) {
        List<CarRentalDto> collect = cars.stream().map(carRentalMapper::toCarRentalDto).collect(Collectors.toList());
        CarRentalWrapper carRentalWrapper = new CarRentalWrapper();
        carRentalWrapper.setCarRentals(collect);
        return carRentalWrapper;
    }
}
