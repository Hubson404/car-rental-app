package org.hubson404.carrentalapp.car;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hubson404.carrentalapp.department.DepartmentRepository;
import org.hubson404.carrentalapp.domain.Car;
import org.hubson404.carrentalapp.domain.Department;
import org.hubson404.carrentalapp.exceptions.DepartmentNotFoundException;
import org.hubson404.carrentalapp.model.CarDto;
import org.hubson404.carrentalapp.model.mappers.CarMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CarCreateService {

    private final CarRepository carRepository;
    private final DepartmentRepository departmentRepository;
    private final CarMapper carMapper;

    public CarDto createCar(CarDto carDTO) {
        checkMileage(carDTO);
        Car createdCar = getDepartmentFromRepository(carDTO);
        Car savedCar = carRepository.save(createdCar);
        return carMapper.toCarDto(savedCar);
    }

    private void checkMileage(CarDto carDTO) {
        if (carDTO.getMileage() == null) {
            log.info("Mileage was not specified, setting mileage to '0km'");
            carDTO.setMileage(0L);
        }
    }

    private Car getDepartmentFromRepository(CarDto carDto) {
        Car createdCar = carMapper.toCar(carDto);
        Optional<Department> byId = departmentRepository.findById(carDto.getDepartment().getId());
        byId.ifPresentOrElse(createdCar::setDepartment,
                () -> {
                    throw new DepartmentNotFoundException(carDto.getDepartment().getId());
                });
        return createdCar;
    }
}
