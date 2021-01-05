package org.hubson404.carrentalapp.car;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hubson404.carrentalapp.domain.Car;
import org.hubson404.carrentalapp.exceptions.InsufficientDataException;
import org.springframework.stereotype.Service;

import java.time.Year;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarCreateService {

    private final CarRepository carRepository;

    public Car createCar(Car car) {

        if (car.getBrand() == null || car.getBrand().isBlank()) {
            throw new InsufficientDataException("Car brand must be specified");
        }
        if (car.getModel() == null || car.getModel().isBlank()) {
            throw new InsufficientDataException("Car model must be specified");
        }
        if (car.getProductionYear() == null) {
            throw new InsufficientDataException("Car production year must be specified");
        } else if (car.getProductionYear() > Year.now().getValue()) {
            throw new IllegalArgumentException("Car production year cannot be greater than current year");
        }
        if (car.getMileage() == null || car.getModel().isBlank()) {
            log.info("Mileage was not specified, setting mileage to '0km'");
            car.setMileage(0L);
        }
        if (car.getCostPerDay() == null || car.getCostPerDay().equals(0d)) {
            throw new InsufficientDataException("Car cost per day must be specified");
        }
        if (car.getCarBodyType() == null) {
            throw new InsufficientDataException("Car body type must be specified");
        }
        if (car.getColor() == null) {
            throw new InsufficientDataException("Car color type must be specified");
        }
        if (car.getDepartment() == null) {
            throw new InsufficientDataException("Initial department must be specified");
        }

        return carRepository.save(car);
    }
}
