package org.hubson404.carrentalapp.repositories;

import org.hubson404.carrentalapp.domain.Car;
import org.hubson404.carrentalapp.domain.CarReservation;
import org.hubson404.carrentalapp.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;

public interface CarReservationRepository extends JpaRepository<CarReservation, Long> {

    List<CarReservation> findCarReservationByCarId(Long id);

    // "WHERE cr.car IS NULL
    List<CarReservation> findAllByCustomerAndCarAndReturnDate(Customer customer, @Nullable Car car, @Nullable LocalDateTime returnDate);
}
