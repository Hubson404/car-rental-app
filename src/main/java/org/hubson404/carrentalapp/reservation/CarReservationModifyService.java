package org.hubson404.carrentalapp.reservation;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.CarReservation;
import org.hubson404.carrentalapp.exceptions.CarReservationNotFoundException;
import org.hubson404.carrentalapp.model.CarReservationDto;
import org.hubson404.carrentalapp.model.mappers.CarReservationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    public CarReservationDto cancelReservation(Long id) {

        long penalisedDaysBeforeRentalStart = 2L;

        CarReservation carReservation = carReservationRepository.findById(id)
                .orElseThrow(() -> new CarReservationNotFoundException(id));

        LocalDateTime rentalStartingDate = carReservation.getRentalStartingDate();
        LocalDateTime penalisedCancelDateDeadline = rentalStartingDate.minus(penalisedDaysBeforeRentalStart, ChronoUnit.DAYS);

        if (penalisedCancelDateDeadline.isAfter(LocalDateTime.now())) {
            carReservation.setTotalCost(0d);
        } else {
            Double totalCost = carReservation.getTotalCost();
            carReservation.setTotalCost(0.2 * totalCost);
        }

        carReservation.setCanceled(true);

        return carReservationMapper.toCarReservationDto(carReservation);
    }
}
