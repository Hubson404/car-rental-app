package org.hubson404.carrentalapp.customer;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.Customer;
import org.hubson404.carrentalapp.exceptions.InsufficientDataException;
import org.hubson404.carrentalapp.model.CustomerDto;
import org.hubson404.carrentalapp.model.mappers.CustomerMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerCreateService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerDto createCustomer(CustomerDto customerDTO) {

        if (customerDTO.getFirstName() == null || customerDTO.getFirstName().isBlank()) {
            throw new InsufficientDataException("Customer first name must be specified.");
        }
        if (customerDTO.getLastName() == null || customerDTO.getLastName().isBlank()) {
            throw new InsufficientDataException("Customer last name must be specified.");
        }
        if (customerDTO.getEmail() == null || customerDTO.getEmail().isBlank()) {
            throw new InsufficientDataException("Customer email must be specified.");
        }
        if (customerDTO.getAddress() == null || customerDTO.getAddress().isBlank()) {
            throw new InsufficientDataException("Customer address must be specified.");
        }

        Customer createdCustomer = customerMapper.toCustomer(customerDTO);
        Customer savedCustomer = customerRepository.save(createdCustomer);

        return customerMapper.toCustomerDto(savedCustomer);
    }
}
