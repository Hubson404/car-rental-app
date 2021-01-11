package org.hubson404.carrentalapp.car;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.model.CarDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CarController {

    private final CarCreateService carCreateService;
    private final CarModifyService carModifyservice;
    private final CarFetchService carFetchService;


    @GetMapping("/cars")
    public CarWrapper findAllCars() {
        List<CarDto> allCars = carFetchService.findAllCars();
        CarWrapper carWrapper = new CarWrapper();
        carWrapper.setCars(allCars);
        return carWrapper;
    }

    @GetMapping("/cars/{id}")
    public CarDto findCarById(@PathVariable Long id) {
        return carFetchService.findCarById(id);
    }

    @PostMapping("/cars")
    @ResponseStatus(HttpStatus.CREATED)
    public CarDto addCar(@Valid @RequestBody CarDto carDTO) {
        return carCreateService.createCar(carDTO);
    }

    @PatchMapping("/cars/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CarDto modifyCar(@PathVariable Long id, @RequestBody CarDto carDto) {
        return carModifyservice.modifyCar(id, carDto);
    }

    @DeleteMapping("/cars/{id}")
    public void deleteCarById(@PathVariable Long id) {
        carModifyservice.deleteCarById(id);
    }


}
