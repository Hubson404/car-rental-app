package org.hubson404.carrentalapp.CarReservation;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.CarReservation;
import org.hubson404.carrentalapp.exceptions.CarReservationNotFoundException;
import org.hubson404.carrentalapp.model.CarReservationDTO;
import org.hubson404.carrentalapp.model.mappers.CarReservationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@Transactional
@RequiredArgsConstructor
public class CarReservationModifyService {

    private final CarReservationRepository carReservationRepository;
    private final CarReservationMapper carReservationMapper;

    public void deleteReservationById(Long id) {
        carReservationRepository.findById(id)
                .ifPresentOrElse(r -> carReservationRepository.deleteById(r.getId()),
                        () -> {
                            throw new CarReservationNotFoundException(id);
                        });
    }

    public CarReservationDTO cancelReservation(Long id) {

        long penalisedDaysBeforeRentalStart = 2L;

        CarReservation carReservation = carReservationRepository.findById(id)
                .orElseThrow(() -> new CarReservationNotFoundException(id));

        Instant rentalStartingDate = carReservation.getRentalStartingDate();
        Instant penalisedCancelDateDeadline = rentalStartingDate.minus(penalisedDaysBeforeRentalStart, ChronoUnit.DAYS);

        if (penalisedCancelDateDeadline.isAfter(Instant.now())) {
            carReservation.setTotalCost(0d);
        } else {
            Double totalCost = carReservation.getTotalCost();
            carReservation.setTotalCost(0.2 * totalCost);
        }

        carReservation.setCanceled(true);

        return carReservationMapper.toCarReservationDTO(carReservation);
    }
}
