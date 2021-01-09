package org.hubson404.carrentalapp.customer;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.model.CustomerDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerCreateService customerCreateService;
    private final CustomerFetchService customerFetchService;
    private final CustomerModifyService customerModifyService;

    @GetMapping("/customers")
    public CustomerCollection findAll() {
        List<CustomerDTO> all = customerFetchService.findAll();
        CustomerCollection customerCollection = new CustomerCollection();
        customerCollection.setCustomers(all);
        return customerCollection;
    }

    @GetMapping("/customers/{id}")
    public CustomerDTO findCustomerById(@PathVariable Long id) {
        return customerFetchService.findCustomerById(id);
    }

    @PostMapping("/customers")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerCreateService.createCustomer(customerDTO);
    }

    @PatchMapping("/customers/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CustomerDTO modifyCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return customerModifyService.modifyCustomer(id, customerDTO);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomerById(@PathVariable Long id) {
        customerModifyService.deleteCustomerById(id);
    }


}
