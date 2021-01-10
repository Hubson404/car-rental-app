package org.hubson404.carrentalapp.CarReservation;


import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.model.CarReservationDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CarReservationController {

    private final CarReservationCreateService carReservationCreateService;
    private final CarReservationFetchService carReservationFetchService;
    private final CarReservationModifyService carReservationModifyService;

    @PostMapping("/reservations")
    public CarReservationDTO createReservation(@RequestBody CarReservationDTO carReservationDTO) {
        return carReservationCreateService.createReservation(carReservationDTO);
    }

    @GetMapping("/reservations")
    public CarReservationCollection getAllReservations() {
        List<CarReservationDTO> reservations = carReservationFetchService.findAll();
        CarReservationCollection collection = new CarReservationCollection();
        collection.setCarReservations(reservations);
        return collection;
    }

    @GetMapping("/reservations/{id}")
    public CarReservationDTO getReservationById(@PathVariable Long id) {
        return carReservationFetchService.findReservationById(id);
    }

    @GetMapping("/reservations/car/{carId}")
    public CarReservationCollection getReservationsByCarId(@PathVariable Long carId) {
        List<CarReservationDTO> reservations = carReservationFetchService.findReservationsByCarId(carId);
        CarReservationCollection collection = new CarReservationCollection();
        collection.setCarReservations(reservations);
        return collection;
    }

    @PatchMapping("/reservations/{id}")
    public CarReservationDTO cancelReservationById(@PathVariable Long id) {
        return carReservationModifyService.cancelReservation(id);
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservationById(@PathVariable Long id) {
        carReservationModifyService.deleteReservationById(id);
    }

}
