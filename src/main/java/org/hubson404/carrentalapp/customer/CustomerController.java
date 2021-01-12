package org.hubson404.carrentalapp.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hubson404.carrentalapp.model.CustomerDto;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerCreateService customerCreateService;
    private final CustomerFetchService customerFetchService;
    private final CustomerModifyService customerModifyService;

    @GetMapping("/customers")
    public CustomerWrapper findAll() {
        List<CustomerDto> all = customerFetchService.findAll();
        CustomerWrapper customerWrapper = new CustomerWrapper();
        customerWrapper.setCustomers(all);
        return customerWrapper;
    }

    @GetMapping("/customers/{id}")
    public CustomerDto findCustomerById(@PathVariable Long id) {
        return customerFetchService.findCustomerById(id);
    }

    @PostMapping("/customers")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto createCustomer(@Valid @RequestBody CustomerDto customerDTO) {
        return customerCreateService.createCustomer(customerDTO);
    }

    @PatchMapping("/customers/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CustomerDto modifyCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDTO) {
        return customerModifyService.modifyCustomer(id, customerDTO);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomerById(@PathVariable Long id) {
        customerModifyService.deleteCustomerById(id);
    }


    @EventListener(ApplicationReadyEvent.class)
    public void initializeWithCustomer() {
        CustomerDto customer = createCustomer(new CustomerDto(null, "Jan",
                "Kowalski", "Address", "email@email"));
        log.info(customer.toString());
    }

}
