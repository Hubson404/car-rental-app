package org.hubson404.carrentalapp.repositories;

import org.hubson404.carrentalapp.domain.CarRentalCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRentalCompanyRepository extends JpaRepository<CarRentalCompany, Long> {
}
