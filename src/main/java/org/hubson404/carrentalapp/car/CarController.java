package org.hubson404.carrentalapp.car;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.Car;
import org.hubson404.carrentalapp.model.CarDTO;
import org.hubson404.carrentalapp.model.mappers.CarMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CarController {

    private final CarCreateService carCreateService;
    private final CarMapper carMapper;
    private final CarModifyService carModifyservice;
    private final CarFetchService carFetchService;


    @GetMapping("/cars")
    public List<CarDTO> findAllCars() {
        List<Car> cars = carFetchService.findAllCars();
        return toCarDtoList(cars);
    }

    @GetMapping("/cars/{id}")
    public CarDTO findCarById(@PathVariable Long id) {
        Car foundCar = carFetchService.findCarById(id);
        return carMapper.toCarDTO(foundCar);
    }

    @PostMapping("/cars")
    @ResponseStatus(HttpStatus.CREATED)
    public CarDTO addCar(@RequestBody CarDTO carDTO) {
        Car car = carCreateService.createCar(carDTO);
        return carMapper.toCarDTO(car);
    }

    @PatchMapping("/cars/{id}/modify")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CarDTO modifyCar(@PathVariable Long id, @RequestBody CarDTO carDTO) {
        Car modifiedCar = carModifyservice.modifyCar(id, carDTO);
        return carMapper.toCarDTO(modifiedCar);
    }

    @DeleteMapping("/cars/{id}")
    public void deleteCarById(@PathVariable Long id) {
        carModifyservice.deleteCarById(id);
    }

    private List<CarDTO> toCarDtoList(List<Car> cars) {
        return cars.stream().map(carMapper::toCarDTO).collect(Collectors.toList());
    }
}
