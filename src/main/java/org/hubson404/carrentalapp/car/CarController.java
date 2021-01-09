package org.hubson404.carrentalapp.car;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.model.CarDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CarController {

    private final CarCreateService carCreateService;
    private final CarModifyService carModifyservice;
    private final CarFetchService carFetchService;


    @GetMapping("/cars")
    public List<CarDTO> findAllCars() {
        return carFetchService.findAllCars();
    }

    @GetMapping("/cars/{id}")
    public CarDTO findCarById(@PathVariable Long id) {
        return carFetchService.findCarById(id);
    }

    @PostMapping("/cars")
    @ResponseStatus(HttpStatus.CREATED)
    public CarDTO addCar(@RequestBody CarDTO carDTO) {
        return carCreateService.createCar(carDTO);
    }

    @PatchMapping("/cars/{id}/modify")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CarDTO modifyCar(@PathVariable Long id, @RequestBody CarDTO carDTO) {
        return carModifyservice.modifyCar(id, carDTO);
    }

    @DeleteMapping("/cars/{id}")
    public void deleteCarById(@PathVariable Long id) {
        carModifyservice.deleteCarById(id);
    }


}
