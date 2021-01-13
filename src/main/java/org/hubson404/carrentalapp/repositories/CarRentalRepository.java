package org.hubson404.carrentalapp.repositories;

import org.hubson404.carrentalapp.domain.CarRental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRentalRepository extends JpaRepository<CarRental, Long> {
}
