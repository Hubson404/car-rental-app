package org.hubson404.carrentalapp.car;

import org.hubson404.carrentalapp.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
