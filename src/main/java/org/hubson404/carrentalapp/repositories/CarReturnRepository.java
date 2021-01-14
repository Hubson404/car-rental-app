package org.hubson404.carrentalapp.repositories;

import org.hubson404.carrentalapp.domain.CarReturn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarReturnRepository extends JpaRepository<CarReturn, Long> {
}
