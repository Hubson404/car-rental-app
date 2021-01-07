package org.hubson404.carrentalapp.car;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.department.DepartmentRepository;
import org.hubson404.carrentalapp.domain.Car;
import org.hubson404.carrentalapp.domain.Department;
import org.hubson404.carrentalapp.domain.enums.CarBodyColor;
import org.hubson404.carrentalapp.domain.enums.CarBodyType;
import org.hubson404.carrentalapp.exceptions.DepartmentNotFoundException;
import org.hubson404.carrentalapp.exceptions.EmployeeNotFoundException;
import org.hubson404.carrentalapp.model.CarDTO;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CarModifyService {

    private final CarRepository carRepository;
    private final DepartmentRepository departmentRepository;


    public void deleteCarById(Long id) {
        carRepository.findById(id).ifPresentOrElse(
                car -> carRepository.deleteById(car.getId()),
                () -> {
                    throw new EmployeeNotFoundException("Could not find Car with id: " + id);
                });
    }

    public Car modifyCar(Long id, CarDTO carDTO) {

        Car foundCar = carRepository.findById(id).orElseThrow(
                () -> new EmployeeNotFoundException("Could not find Car with id: " + id));

        if (carDTO.getBrand() != null && !carDTO.getBrand().isBlank()) {
            foundCar.setBrand(carDTO.getBrand());
        }
        if (carDTO.getModel() != null && !carDTO.getModel().isBlank()) {
            foundCar.setModel(carDTO.getModel());
        }
        if (carDTO.getProductionYear() != null) {
            foundCar.setProductionYear(carDTO.getProductionYear());
        }
        if (carDTO.getMileage() != null && !carDTO.getModel().isBlank()) {
            foundCar.setMileage(carDTO.getMileage());
        }
        if (carDTO.getCostPerDay() != null && !Objects.equals(carDTO.getCostPerDay(), 0d)) {
            foundCar.setCostPerDay(carDTO.getCostPerDay());
        }
        if (carDTO.getCarBodyType() != null) {
            foundCar.setCarBodyType(CarBodyType.valueOf(carDTO.getCarBodyType()));
        }
        if (carDTO.getColor() != null) {
            foundCar.setColor(CarBodyColor.valueOf((carDTO.getColor())));
        }
        if (carDTO.getDepartment() != null && carDTO.getDepartment().getId() != null) {
            Optional<Department> byId = departmentRepository.findById(carDTO.getDepartment().getId());
            Department department = byId.orElseThrow(() -> new DepartmentNotFoundException("Given Department was not found"));
            foundCar.setDepartment(department);
        }

        return foundCar;
    }
}
