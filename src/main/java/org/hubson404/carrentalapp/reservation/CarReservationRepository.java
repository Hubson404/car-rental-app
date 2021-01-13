package org.hubson404.carrentalapp.reservation;

import org.hubson404.carrentalapp.domain.CarReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarReservationRepository extends JpaRepository<CarReservation, Long> {

    List<CarReservation> findCarReservationByCarId(Long id);
}
