package org.hubson404.carrentalapp.controllers;


import lombok.RequiredArgsConstructor;

import org.hubson404.carrentalapp.domain.CarReservation;
import org.hubson404.carrentalapp.model.CarReservationDto;
import org.hubson404.carrentalapp.services.CarReservationService;
import org.hubson404.carrentalapp.wrappers.CarReservationWrapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CarReservationController {

    private final CarReservationService carReservationService;

    @PostMapping("/reservations/search")
    public CarReservationDto search(@Valid CarReservationSearchRequest request) {

    }

    @GetMapping
    public CarReservationDto asd(@RequestParam, @RequestParam)

    @PostMapping("/reservations")
    public CarReservationDto createReservation(@Valid @RequestBody CarReservationDto carReservationDTO) {
        return carReservationService.createReservation(carReservationDTO);
    }

    @GetMapping("/reservations")
    public CarReservationWrapper getAllReservations() {
        List<CarReservationDto> reservations = carReservationService.findAll();
        CarReservationWrapper collection = new CarReservationWrapper();
        collection.setCarReservations(reservations);
        return collection;
    }

    @GetMapping("/reservations/{id}")
    public CarReservationDto getReservationById(@PathVariable Long id) {
        return carReservationService.findReservationById(id);
    }

    @GetMapping("/reservations/car/{carId}")
    public CarReservationWrapper getReservationsByCarId(@PathVariable Long carId) {
        List<CarReservationDto> reservations = carReservationService.findReservationsByCarId(carId);
        CarReservationWrapper collection = new CarReservationWrapper();
        collection.setCarReservations(reservations);
        return collection;
    }

    @PatchMapping("/reservations/{id}")
    public CarReservationDto cancelReservationById(@PathVariable Long id) {
        return carReservationService.cancelReservation(id);
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservationById(@PathVariable Long id) {
        carReservationService.deleteReservationById(id);
    }

}
