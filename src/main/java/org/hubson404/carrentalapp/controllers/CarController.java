package org.hubson404.carrentalapp.controllers;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.model.CarDto;
import org.hubson404.carrentalapp.services.CarService;
import org.hubson404.carrentalapp.wrappers.CarWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("/cars")
    public CarWrapper findAllCars() {
        List<CarDto> allCars = carService.findAllCars();
        CarWrapper carWrapper = new CarWrapper();
        carWrapper.setCars(allCars);
        return carWrapper;
    }

    @GetMapping("/cars/{id}")
    public CarDto findCarById(@PathVariable Long id) {
        return carService.findCarById(id);
    }

    @PostMapping("/cars")
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addCar(@Valid @RequestBody CarDto carDTO) throws URISyntaxException {
        final CarDto createdCar = carService.createCar(carDTO);
        return ResponseEntity.created(new URI("/cars/" + carDTO.getId())).build();
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
