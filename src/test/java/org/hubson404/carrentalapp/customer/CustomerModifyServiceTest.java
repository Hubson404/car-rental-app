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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerModifyServiceTest {

    @Mock
    CustomerRepository customerRepository;
    @Mock
    CustomerMapper customerMapper;
    @InjectMocks
    CustomerModifyService customerModifyService;

    @Test
    void modifyCustomer_CustomerByGivenIdDoesNotExist_callsRepositoryAndThrowsException() {
        // given
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());
        // when
        Throwable result = catchThrowable(() -> customerModifyService.modifyCustomer(1L, new CustomerDto()));
        // then
        assertThat(result).isExactlyInstanceOf(CustomerNotFoundException.class);
        verify(customerRepository, times(1)).findById(anyLong());
        verify(customerRepository, times(0)).save(any());
    }

    @Test
    void modifyCustomer_changeCustomerFirstName_CallsRepository() {
        // given
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(new Customer()));
        when(customerMapper.toCustomerDto(any(Customer.class))).thenReturn(new CustomerDto());
        when(customerRepository.save(any(Customer.class))).thenReturn(new Customer());
        CustomerDto customerDTO = CustomerDto.builder().firstName("testName").build();
        // when
        customerModifyService.modifyCustomer(1L, customerDTO);
        // then
        verify(customerRepository, times(1)).findById(anyLong());
        verify(customerRepository, times(1)).save(any());
    }

    @Test
    void modifyCustomer_changeCustomerFirstNameToBlank_DoesNotCallRepository() {
        // given
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(new Customer()));
        CustomerDto customerDTO = CustomerDto.builder().firstName("  ").build();
        // when
        Throwable result = catchThrowable(() -> customerModifyService.modifyCustomer(1L, customerDTO));
        // then
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
        verify(customerRepository, times(1)).findById(anyLong());
        verify(customerRepository, times(0)).save(any());
    }

    @Test
    void modifyCustomer_changeCustomerLastName_CallsRepository() {
        // given
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(new Customer()));
        when(customerMapper.toCustomerDto(any(Customer.class))).thenReturn(new CustomerDto());
        when(customerRepository.save(any(Customer.class))).thenReturn(new Customer());
        CustomerDto customerDTO = CustomerDto.builder().lastName("testLastName").build();
        // when
        customerModifyService.modifyCustomer(1L, customerDTO);
        // then
        verify(customerRepository, times(1)).findById(anyLong());
        verify(customerRepository, times(1)).save(any());
    }

    @Test
    void modifyCustomer_changeCustomerLastNameToBlank_DoesNotCallRepository() {
        // given
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(new Customer()));
        CustomerDto customerDTO = CustomerDto.builder().lastName("  ").build();
        // when
        Throwable result = catchThrowable(() -> customerModifyService.modifyCustomer(1L, customerDTO));
        // then
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
        verify(customerRepository, times(1)).findById(anyLong());
        verify(customerRepository, times(0)).save(any());
    }

    @Test
    void modifyCustomer_changeCustomerEmail_CallsRepository() {
        // given
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(new Customer()));
        when(customerMapper.toCustomerDto(any(Customer.class))).thenReturn(new CustomerDto());
        when(customerRepository.save(any(Customer.class))).thenReturn(new Customer());
        CustomerDto customerDTO = CustomerDto.builder().email("testEmail").build();
        // when
        customerModifyService.modifyCustomer(1L, customerDTO);
        // then
        verify(customerRepository, times(1)).findById(anyLong());
        verify(customerRepository, times(1)).save(any());
    }


    @Test
    void modifyCustomer_changeCustomerEmailToBlank_DoesNotCallRepository() {
        // given
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(new Customer()));
        CustomerDto customerDTO = CustomerDto.builder().email("  ").build();
        // when
        Throwable result = catchThrowable(() -> customerModifyService.modifyCustomer(1L, customerDTO));
        // then
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
        verify(customerRepository, times(1)).findById(anyLong());
        verify(customerRepository, times(0)).save(any());
    }

    @Test
    void modifyCustomer_changeCustomerAddress_CallsRepository() {
        // given
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(new Customer()));
        when(customerMapper.toCustomerDto(any(Customer.class))).thenReturn(new CustomerDto());
        when(customerRepository.save(any(Customer.class))).thenReturn(new Customer());
        CustomerDto customerDTO = CustomerDto.builder().address("testAddress").build();
        // when
        customerModifyService.modifyCustomer(1L, customerDTO);
        // then
        verify(customerRepository, times(1)).findById(anyLong());
        verify(customerRepository, times(1)).save(any());
    }


    @Test
    void modifyCustomer_changeCustomerAddressToBlank_DoesNotCallRepository() {
        // given
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(new Customer()));
        CustomerDto customerDTO = CustomerDto.builder().address("  ").build();
        // when
        Throwable result = catchThrowable(() -> customerModifyService.modifyCustomer(1L, customerDTO));
        // then
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
        verify(customerRepository, times(1)).findById(anyLong());
        verify(customerRepository, times(0)).save(any());
    }

    @Test
    void deleteCustomerById_callsCarRepository() {
        // given
        Customer customer = new Customer();
        customer.setId(1L);
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        // when
        customerModifyService.deleteCustomerById(1L);
        // then
        verify(customerRepository, times(1)).findById(anyLong());
        verify(customerRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteCustomerById_CustomerByIdNotFound_ThrowsException() {
        // given
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());
        // when
        Throwable result = catchThrowable(() -> customerModifyService.deleteCustomerById(anyLong()));
        // then
        assertThat(result).isExactlyInstanceOf(CustomerNotFoundException.class);
        verify(customerRepository, times(1)).findById(anyLong());
        verify(customerRepository, times(0)).deleteById(anyLong());
    }
}
