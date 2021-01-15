package org.hubson404.carrentalapp.model.mappers;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.Car;
import org.hubson404.carrentalapp.domain.CarReservation;
import org.hubson404.carrentalapp.domain.Customer;
import org.hubson404.carrentalapp.domain.Department;
import org.hubson404.carrentalapp.exceptions.CarNotFoundException;
import org.hubson404.carrentalapp.exceptions.CustomerNotFoundException;
import org.hubson404.carrentalapp.exceptions.DepartmentNotFoundException;
import org.hubson404.carrentalapp.model.CarReservationDto;
import org.hubson404.carrentalapp.repositories.CarRepository;
import org.hubson404.carrentalapp.repositories.CustomerRepository;
import org.hubson404.carrentalapp.repositories.DepartmentRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CarReservationMapper {

    private final CarRepository carRepository;
    private final DepartmentRepository departmentRepository;
    private final CustomerRepository customerRepository;

    public CarReservation toCarReservation(CarReservationDto carReservationDto) {

        CarReservation carReservation = new CarReservation();

        carReservation.setCar(getCar(carReservationDto));
        carReservation.setCarRentalDepartment(getDepartment(carReservationDto.getCarRentalDepartment()));
        carReservation.setCarReturnDepartment(getDepartment(carReservationDto.getCarReturnDepartment()));
        carReservation.setCustomer(getCustomer(carReservationDto));
        carReservation.setRentalStartingDate(getDate(carReservationDto.getRentalStartingDate()));
        carReservation.setReturnDate(getDate(carReservationDto.getReturnDate()));

        return carReservation;
    }

    private Car getCar(CarReservationDto carReservationDto) {
        Long carId = carReservationDto.getCar();
        return carRepository.findById(carId).orElseThrow(() -> new CarNotFoundException(carId));
    }

    private LocalDateTime getDate(String dateString) {
        return LocalDateTime.parse(dateString);
    }

    private Customer getCustomer(CarReservationDto carReservationDto) {
        Long customerId = carReservationDto.getCustomer();
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    private Department getDepartment(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new DepartmentNotFoundException(departmentId));
    }

    public CarReservationDto toCarReservationDto(CarReservation carReservation) {

        return CarReservationDto.builder()
                .id(carReservation.getId())
                .car(carReservation.getCar().getId())
                .customer(carReservation.getCustomer().getId())
                .carRentalDepartment(carReservation.getCarRentalDepartment().getId())
                .carReturnDepartment(carReservation.getCarReturnDepartment().getId())
                .rentalStartingDate(carReservation.getRentalStartingDate().toString())
                .returnDate(carReservation.getReturnDate().toString())
                .totalCost(carReservation.getTotalCost())
                .canceled(carReservation.isCanceled())
                .build();

    }

}
