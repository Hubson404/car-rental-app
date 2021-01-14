package org.hubson404.carrentalapp.controllers;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.model.CarRentalDto;
import org.hubson404.carrentalapp.services.CarRentalService;
import org.hubson404.carrentalapp.wrappers.CarRentalCreateWrapper;
import org.hubson404.carrentalapp.wrappers.CarRentalWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CarRentalController {

    private final CarRentalService carRentalService;

    @PostMapping("/rentals")
    @ResponseStatus(HttpStatus.CREATED)
    public CarRentalDto createCarRental(@Valid @RequestBody CarRentalCreateWrapper wrapper) {
        return carRentalService.createCarRental(wrapper);
    }

    @GetMapping("/rentals/{id}")
    public CarRentalDto findById(@PathVariable Long id) {
        return carRentalService.findById(id);
    }

    @GetMapping("/rentals")
    public CarRentalWrapper findAll() {
        return carRentalService.findAll();
    }
}
