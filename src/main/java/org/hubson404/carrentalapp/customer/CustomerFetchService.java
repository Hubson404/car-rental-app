package org.hubson404.carrentalapp.customer;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.Customer;
import org.hubson404.carrentalapp.exceptions.CustomerNotFoundException;
import org.hubson404.carrentalapp.model.CustomerDTO;
import org.hubson404.carrentalapp.model.mappers.CustomerMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerFetchService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public List<CustomerDTO> findAll() {
        List<Customer> allCustomers = customerRepository.findAll();
        return allCustomers.stream().map(customerMapper::toCustomerDTO).collect(Collectors.toList());
    }

    public CustomerDTO findCustomerById(Long id) {
        Optional<Customer> byId = customerRepository.findById(id);
        Customer foundCustomer = byId.orElseThrow(
                () -> new CustomerNotFoundException("Could not find department with id: " + id));
        return customerMapper.toCustomerDTO(foundCustomer);
    }
}
