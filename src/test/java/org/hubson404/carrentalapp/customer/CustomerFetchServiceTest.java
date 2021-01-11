package org.hubson404.carrentalapp.customer;

import org.hubson404.carrentalapp.domain.Customer;
import org.hubson404.carrentalapp.exceptions.CustomerNotFoundException;
import org.hubson404.carrentalapp.model.CustomerDto;
import org.hubson404.carrentalapp.model.mappers.CustomerMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerFetchServiceTest {

    @Mock
    CustomerRepository customerRepository;
    @Mock
    CustomerMapper customerMapper;
    @InjectMocks
    CustomerFetchService customerFetchService;

    @Test
    void findAll_callsRepository() {
        // given
        when(customerRepository.findAll()).thenReturn(List.of(new Customer()));
        when(customerMapper.toCustomerDto(any(Customer.class))).thenReturn(new CustomerDto());
        // when
        customerFetchService.findAll();
        // then
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void findCustomerById_callsCarRepository() {
        // given
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(new Customer()));
        when(customerMapper.toCustomerDto(any(Customer.class))).thenReturn(new CustomerDto());
        // when
        customerFetchService.findCustomerById(anyLong());
        // then
        verify(customerRepository, times(1)).findById(anyLong());
    }

    @Test
    void findCustomerById_CustomerByGivenIdDoesNotExist_callsCarRepositoryAndThrowsException() {
        // given
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());
        // when
        Throwable result = catchThrowable(() -> customerFetchService.findCustomerById(anyLong()));
        // then
        assertThat(result).isExactlyInstanceOf(CustomerNotFoundException.class);
        verify(customerRepository, times(1)).findById(anyLong());
    }
}
