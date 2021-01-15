package org.hubson404.carrentalapp.services;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.Customer;
import org.hubson404.carrentalapp.exceptions.CustomerNotFoundException;
import org.hubson404.carrentalapp.exceptions.InsufficientDataException;
import org.hubson404.carrentalapp.model.CustomerDto;
import org.hubson404.carrentalapp.model.mappers.CustomerMapper;
import org.hubson404.carrentalapp.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

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

    public void deleteCustomerById(Long id) {
        customerRepository.findById(id)
                .ifPresentOrElse(customer -> customerRepository.deleteById(id),
                        () -> {
                            throw new CustomerNotFoundException(id);
                        });
    }

    public CustomerDto modifyCustomer(Long id, CustomerDto customerDTO) {

        Customer foundCustomer = customerRepository.findById(id).orElseThrow(
                () -> new CustomerNotFoundException(id));

        if (customerDTO.getFirstName() != null) {
            if (customerDTO.getFirstName().isBlank()) {
                throw new IllegalArgumentException("First name field cannot be set to empty.");
            }
            foundCustomer.setFirstName(customerDTO.getFirstName());
        }
        if (customerDTO.getLastName() != null) {
            if (customerDTO.getLastName().isBlank()) {
                throw new IllegalArgumentException("Last name field cannot be set to empty.");
            }
            foundCustomer.setLastName(customerDTO.getLastName());
        }
        if (customerDTO.getEmail() != null) {
            if (customerDTO.getEmail().isBlank()) {
                throw new IllegalArgumentException("Email field cannot be set to empty.");
            }
            foundCustomer.setEmail(customerDTO.getEmail());
        }
        if (customerDTO.getAddress() != null) {
            if (customerDTO.getAddress().isBlank()) {
                throw new IllegalArgumentException("Address field cannot be set to empty.");
            }
            foundCustomer.setAddress(customerDTO.getAddress());
        }

        Customer savedCustomer = customerRepository.save(foundCustomer);

        return customerMapper.toCustomerDto(savedCustomer);
    }
}
