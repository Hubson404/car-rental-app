package org.hubson404.carrentalapp.car;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.Car;
import org.hubson404.carrentalapp.exceptions.CarNotFoundException;
import org.hubson404.carrentalapp.model.CarDto;
import org.hubson404.carrentalapp.model.mappers.CarMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CarFetchService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public CarDto findCarById(Long id) {
        Optional<Car> carRepositoryById = carRepository.findById(id);
        Car car = carRepositoryById.orElseThrow(() -> new CarNotFoundException(id));
        return carMapper.toCarDto(car);
    }

    public List<CarDto> findAllCars() {
        List<Car> all = carRepository.findAll();
        return all.stream().map(carMapper::toCarDto).collect(Collectors.toList());
    }
}
