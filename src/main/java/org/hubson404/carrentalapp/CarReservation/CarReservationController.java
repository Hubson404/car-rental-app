package org.hubson404.carrentalapp.CarReservation;


import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.CarReservation;
import org.hubson404.carrentalapp.model.CarReservationDto;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CarReservationController {

    private final CarReservationCreateService carReservationCreateService;
    private final CarReservationFetchService carReservationFetchService;
    private final CarReservationModifyService carReservationModifyService;
    private final CarReservationRepository carReservationRepository;

    @PostMapping("/reservations")
    public CarReservationDto createReservation(@Valid @RequestBody CarReservationDto carReservationDTO) {
        return carReservationCreateService.createReservation(carReservationDTO);
    }

    @GetMapping("/reservations")
    public CarReservationWrapper getAllReservations() {
        List<CarReservationDto> reservations = carReservationFetchService.findAll();
        CarReservationWrapper collection = new CarReservationWrapper();
        collection.setCarReservations(reservations);
        return collection;
    }

    @GetMapping("/reservations/{id}")
    public CarReservationDto getReservationById(@PathVariable Long id) {
        return carReservationFetchService.findReservationById(id);
    }

    @GetMapping("/reservations/car/{carId}")
    public CarReservationWrapper getReservationsByCarId(@PathVariable Long carId) {
        List<CarReservationDto> reservations = carReservationFetchService.findReservationsByCarId(carId);
        CarReservationWrapper collection = new CarReservationWrapper();
        collection.setCarReservations(reservations);
        return collection;
    }

    @PatchMapping("/reservations/{id}")
    public CarReservationDto cancelReservationById(@PathVariable Long id) {
        return carReservationModifyService.cancelReservation(id);
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservationById(@PathVariable Long id) {
        carReservationModifyService.deleteReservationById(id);
    }


    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        carReservationRepository.save(new CarReservation());
    }
}
