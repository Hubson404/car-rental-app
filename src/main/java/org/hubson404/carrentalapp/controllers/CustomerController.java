package org.hubson404.carrentalapp.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hubson404.carrentalapp.model.CustomerDto;
import org.hubson404.carrentalapp.services.CustomerService;
import org.hubson404.carrentalapp.wrappers.CustomerWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/customers")
    public CustomerWrapper findAll() {
        List<CustomerDto> all = customerService.findAll();
        CustomerWrapper customerWrapper = new CustomerWrapper();
        customerWrapper.setCustomers(all);
        return customerWrapper;
    }

    @GetMapping("/customers/{id}")
    public CustomerDto findCustomerById(@PathVariable Long id) {
        return customerService.findCustomerById(id);
    }

    @PostMapping("/customers")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto createCustomer(@Valid @RequestBody CustomerDto customerDTO) {
        return customerService.createCustomer(customerDTO);
    }

    @PatchMapping("/customers/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CustomerDto modifyCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDTO) {
        return customerService.modifyCustomer(id, customerDTO);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomerById(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
    }

}
