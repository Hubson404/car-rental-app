package org.hubson404.carrentalapp.car;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hubson404.carrentalapp.department.DepartmentRepository;
import org.hubson404.carrentalapp.domain.Car;
import org.hubson404.carrentalapp.domain.Department;
import org.hubson404.carrentalapp.exceptions.DepartmentNotFoundException;
import org.hubson404.carrentalapp.exceptions.InsufficientDataException;
import org.hubson404.carrentalapp.model.CarDTO;
import org.hubson404.carrentalapp.model.mappers.CarMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CarCreateService {

    private final CarRepository carRepository;
    private final DepartmentRepository departmentRepository;
    private final CarMapper carMapper;

    public Car createCar(CarDTO carDTO) {

        if (carDTO.getBrand() == null || carDTO.getBrand().isBlank()) {
            throw new InsufficientDataException("Car brand must be specified");
        }
        if (carDTO.getModel() == null || carDTO.getModel().isBlank()) {
            throw new InsufficientDataException("Car model must be specified");
        }
        if (carDTO.getProductionYear() == null) {
            throw new InsufficientDataException("Car production year must be specified");
        } else if (carDTO.getProductionYear() > Year.now().getValue()) {
            throw new IllegalArgumentException("Car production year cannot be greater than current year");
        }
        if (carDTO.getMileage() == null) {
            log.info("Mileage was not specified, setting mileage to '0km'");
            carDTO.setMileage(0L);
        }
        if (carDTO.getCostPerDay() == null || carDTO.getCostPerDay().equals(0d)) {
            throw new InsufficientDataException("Car cost per day must be specified");
        }
        if (carDTO.getCarBodyType() == null || carDTO.getCarBodyType().isBlank()) {
            throw new InsufficientDataException("Car body type must be specified");
        }
        if (carDTO.getColor() == null || carDTO.getColor().isBlank()) {
            throw new InsufficientDataException("Car color type must be specified");
        }
        if (carDTO.getCarStatus() == null || carDTO.getCarStatus().isBlank()) {
            throw new InsufficientDataException("Car status type must be specified");
        }
        if (carDTO.getDepartment() == null) {
            throw new InsufficientDataException("Initial department must be specified");
        }

        Car createdCar = carMapper.toCar(carDTO);

        Optional<Department> optionalDepartment = departmentRepository.findById(carDTO.getDepartment().getId());
        Department department = optionalDepartment.orElseThrow(() -> new DepartmentNotFoundException(
                "Could not find department with id: " + carDTO.getDepartment().getId()));
        createdCar.setDepartment(department);

        return carRepository.save(createdCar);

    }
}
