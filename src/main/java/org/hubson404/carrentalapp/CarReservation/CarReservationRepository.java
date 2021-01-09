package org.hubson404.carrentalapp.CarReservation;

import org.hubson404.carrentalapp.domain.CarReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarReservationRepository extends JpaRepository<CarReservation, Long> {
}
