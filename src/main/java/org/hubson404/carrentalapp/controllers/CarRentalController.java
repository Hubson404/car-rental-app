package org.hubson404.carrentalapp.controllers;


import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.model.CarRentalDto;
import org.hubson404.carrentalapp.services.CarRentalService;
import org.hubson404.carrentalapp.wrappers.CarRentalCreateWrapper;
import org.hubson404.carrentalapp.wrappers.CarRentalWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CarRentalController {

    private final CarRentalService carRentalService;

    @PostMapping("/rentals")
    public CarRentalDto createCarRental(@RequestBody CarRentalCreateWrapper wrapper) {
        return carRentalService.createCarRental(wrapper);
    }

    @GetMapping("/rentals")
    public CarRentalWrapper findAll() {
        return carRentalService.findAll();

    }
}
