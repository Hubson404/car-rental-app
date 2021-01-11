package org.hubson404.carrentalapp.customer;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.Customer;
import org.hubson404.carrentalapp.exceptions.CustomerNotFoundException;
import org.hubson404.carrentalapp.model.CustomerDto;
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

    public List<CustomerDto> findAll() {
        List<Customer> allCustomers = customerRepository.findAll();
        return allCustomers.stream().map(customerMapper::toCustomerDto).collect(Collectors.toList());
    }

    public CustomerDto findCustomerById(Long id) {
        Optional<Customer> byId = customerRepository.findById(id);
        Customer foundCustomer = byId.orElseThrow(
                () -> new CustomerNotFoundException(id));
        return customerMapper.toCustomerDto(foundCustomer);
    }
}
