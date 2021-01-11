package org.hubson404.carrentalapp.car;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.department.DepartmentRepository;
import org.hubson404.carrentalapp.domain.Car;
import org.hubson404.carrentalapp.domain.Department;
import org.hubson404.carrentalapp.domain.enums.CarBodyColor;
import org.hubson404.carrentalapp.domain.enums.CarBodyType;
import org.hubson404.carrentalapp.domain.enums.CarStatus;
import org.hubson404.carrentalapp.exceptions.CarNotFoundException;
import org.hubson404.carrentalapp.exceptions.DepartmentNotFoundException;
import org.hubson404.carrentalapp.model.CarDto;
import org.hubson404.carrentalapp.model.mappers.CarMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class CarModifyService {

    private final CarRepository carRepository;
    private final DepartmentRepository departmentRepository;
    private final CarMapper carMapper;


    public void deleteCarById(Long id) {
        carRepository.findById(id).ifPresentOrElse(
                car -> carRepository.deleteById(car.getId()),
                () -> {
                    throw new CarNotFoundException(id);
                });
    }

    public CarDto modifyCar(Long id, CarDto carDTO) {

        Car foundCar = carRepository.findById(id).orElseThrow(
                () -> new CarNotFoundException(id));

        if (carDTO.getBrand() != null) {
            if (carDTO.getBrand().isBlank()) {
                throw new IllegalArgumentException("Brand field cannot be set to empty.");
            }
            foundCar.setBrand(carDTO.getBrand());
        }

        if (carDTO.getModel() != null) {
            if (carDTO.getModel().isBlank()) {
                throw new IllegalArgumentException("Model field cannot be set to empty.");
            }
            foundCar.setModel(carDTO.getModel());
        }

        if (carDTO.getProductionYear() != null) {
            if (carDTO.getProductionYear() <= 1950) {
                throw new IllegalArgumentException("Production year cannot be earlier than 1950.");
            } else if (carDTO.getProductionYear() > Year.now().getValue()) {
                throw new IllegalArgumentException("Production year cannot be greater than current year.");
            }
            foundCar.setProductionYear(carDTO.getProductionYear());
        }

        if (carDTO.getMileage() != null) {
            if (carDTO.getMileage() < 0) {
                throw new IllegalArgumentException("Mileage cannot be set to negative value.");
            }
            foundCar.setMileage(carDTO.getMileage());
        }

        if (carDTO.getCostPerDay() != null) {
            if (carDTO.getCostPerDay() < 0) {
                throw new IllegalArgumentException("Cost per day must be greater than zero.");
            }
            foundCar.setCostPerDay(carDTO.getCostPerDay());
        }

        if (carDTO.getCarBodyType() != null) {
            List<CarBodyType> enumValues = List.of(CarBodyType.values());
            if (!enumValues.contains(CarBodyType.valueOf(carDTO.getCarBodyType()))) {
                throw new IllegalArgumentException("Wrong body type given");
            }
            foundCar.setCarBodyType(CarBodyType.valueOf(carDTO.getCarBodyType()));
        }

        if (carDTO.getColor() != null) {
            List<CarBodyColor> enumValues = List.of(CarBodyColor.values());
            if (!enumValues.contains(CarBodyColor.valueOf(carDTO.getColor()))) {
                throw new IllegalArgumentException("Wrong body type given");
            }
            foundCar.setColor(CarBodyColor.valueOf((carDTO.getColor())));
        }

        if (carDTO.getCarStatus() != null) {
            List<CarStatus> enumValues = List.of(CarStatus.values());
            if (!enumValues.contains(CarStatus.valueOf(carDTO.getCarStatus()))) {
                throw new IllegalArgumentException("Wrong body type given");
            }
            foundCar.setCarStatus(CarStatus.valueOf((carDTO.getCarStatus())));
        }

        if (carDTO.getDepartment() != null && carDTO.getDepartment().getId() != null) {
            Optional<Department> byId = departmentRepository.findById(carDTO.getDepartment().getId());
            Department department = byId.orElseThrow(
                    () -> new DepartmentNotFoundException(carDTO.getDepartment().getId()));
            foundCar.setDepartment(department);
        }

        Car savedCar = carRepository.save(foundCar);

        return carMapper.toCarDto(savedCar);
    }
}
