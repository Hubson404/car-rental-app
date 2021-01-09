package org.hubson404.carrentalapp.customer;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.Customer;
import org.hubson404.carrentalapp.exceptions.CustomerNotFoundException;
import org.hubson404.carrentalapp.model.CustomerDTO;
import org.hubson404.carrentalapp.model.mappers.CustomerMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerModifyService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public void deleteCustomerById(Long id) {
        customerRepository.findById(id)
                .ifPresentOrElse(customer -> customerRepository.deleteById(id),
                        () -> {
                            throw new CustomerNotFoundException("Could not find customer with id: " + id);
                        });
    }

    public CustomerDTO modifyCustomer(Long id, CustomerDTO customerDTO) {

        Customer foundCustomer = customerRepository.findById(id).orElseThrow(
                () -> new CustomerNotFoundException("Could not find Customer with id: " + id));

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

        return customerMapper.toCustomerDTO(savedCustomer);
    }
}
