package org.hubson404.carrentalapp.controllers;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.model.CarDto;
import org.hubson404.carrentalapp.services.CarService;
import org.hubson404.carrentalapp.wrappers.CarWrapper;
import org.hubson404.carrentalapp.wrappers.SearchParametersWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("/cars")
    public CarWrapper findAllCars() {
        return carService.findAllCars();
    }

    @GetMapping("/cars/{id}")
    public CarDto findCarById(@PathVariable Long id) {
        return carService.findCarById(id);
    }

    @PostMapping("/cars/search")
    public CarWrapper findCarsAvailableInGivenTimePeriod(@RequestBody SearchParametersWrapper searchParametersWrapper) {
        return carService.findFreeCarsInTimePeriod(searchParametersWrapper);
    }

    @PostMapping("/cars")
    @ResponseStatus(HttpStatus.CREATED)
    public CarDto addCar(@Valid @RequestBody CarDto carDTO) {
        return carService.createCar(carDTO);
    }

    @PatchMapping("/cars/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CarDto modifyCar(@PathVariable Long id, @RequestBody CarDto carDto) {
        return carService.modifyCar(id, carDto);
    }

    @DeleteMapping("/cars/{id}")
    public void deleteCarById(@PathVariable Long id) {
        carService.deleteCarById(id);
    }

}
