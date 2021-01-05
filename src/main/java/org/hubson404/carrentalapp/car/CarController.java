package org.hubson404.carrentalapp.car;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.Car;
import org.hubson404.carrentalapp.model.CarDTO;
import org.hubson404.carrentalapp.model.mappers.CarMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CarController {

    private final CarCreateService carCreateService;
    private final CarMapper carMapper;

    @PostMapping("/cars")
    public CarDTO createCar(CarDTO carDTO) {
        Car car = carCreateService.createCar(carMapper.toCar(carDTO));
        return carMapper.toCarDTO(car);
    }
}
