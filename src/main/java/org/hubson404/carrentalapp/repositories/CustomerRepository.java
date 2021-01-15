package org.hubson404.carrentalapp.repositories;

import org.hubson404.carrentalapp.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
