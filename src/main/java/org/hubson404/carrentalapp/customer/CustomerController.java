package org.hubson404.carrentalapp.customer;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.Customer;
import org.hubson404.carrentalapp.model.CustomerDTO;
import org.hubson404.carrentalapp.model.mappers.CustomerMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerCreateService customerCreateService;
    private final CustomerFetchService customerFetchService;
    private final CustomerModifyService customerModifyService;
    private final CustomerMapper customerMapper;

    @GetMapping("/customers")
    public List<CustomerDTO> findAll() {
        List<Customer> customers = customerFetchService.findAll();
        return toCustomerDtoList(customers);
    }

    @GetMapping("/customers/{id}")
    public CustomerDTO findCustomerById(@PathVariable Long id) {
        return customerMapper.toCustomerDTO(customerFetchService.findCustomerById(id));
    }

    @PostMapping("/customers")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer createdCustomer = customerCreateService.createCustomer(customerDTO);
        return customerMapper.toCustomerDTO(createdCustomer);
    }

    @PatchMapping("/customers/{id}/modify")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CustomerDTO modifyCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        Customer modifiedCustomer = customerModifyService.modifyCustomer(id, customerDTO);
        return customerMapper.toCustomerDTO(modifiedCustomer);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomerById(@PathVariable Long id) {
        customerModifyService.deleteCustomerById(id);
    }

    private List<CustomerDTO> toCustomerDtoList(List<Customer> customers) {
        return customers.stream().map(customerMapper::toCustomerDTO).collect(Collectors.toList());
    }
}
