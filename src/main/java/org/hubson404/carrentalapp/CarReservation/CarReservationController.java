package org.hubson404.carrentalapp.CarReservation;


import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.CarReservation;
import org.hubson404.carrentalapp.model.CarReservationDTO;
import org.hubson404.carrentalapp.model.mappers.CarReservationMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CarReservationController {

    private final CarReservationCreateService carReservationCreateService;
    private final CarReservationMapper carReservationMapper;

    @PostMapping("/reservations")
    public CarReservationDTO createReservation(@RequestBody CarReservationDTO carReservationDTO) {
        CarReservation createdReservation = carReservationCreateService.createReservation(carReservationDTO);
        return carReservationMapper.toCarReservationDTO(createdReservation);
    }

}
