package org.hubson404.carrentalapp.model.mappers;

import org.hubson404.carrentalapp.domain.Customer;
import org.hubson404.carrentalapp.model.CustomerDto;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer toCustomer(CustomerDto customerDto);

    CustomerDto toCustomerDto(Customer customer);

}
