package org.hubson404.carrentalapp.car;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.Car;
import org.hubson404.carrentalapp.exceptions.CarNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CarFetchService {

    private final CarRepository carRepository;

    public Car findCarById(Long id) {
        Optional<Car> carRepositoryById = carRepository.findById(id);
        return carRepositoryById.orElseThrow(() -> new CarNotFoundException("Could not find Car by id: " + id));
    }

    public List<Car> findAllCars() {
        return carRepository.findAll();
    }
}
