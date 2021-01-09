package org.hubson404.carrentalapp.customer;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.Customer;
import org.hubson404.carrentalapp.exceptions.CustomerNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerFetchService {

    private final CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findCustomerById(Long id) {
        Optional<Customer> byId = customerRepository.findById(id);
        return byId.orElseThrow(
                () -> new CustomerNotFoundException("Could not find department with id: " + id));
    }
}
