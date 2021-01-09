package org.hubson404.carrentalapp.customer;

import org.hubson404.carrentalapp.domain.Customer;
import org.hubson404.carrentalapp.exceptions.InsufficientDataException;
import org.hubson404.carrentalapp.model.CustomerDTO;
import org.hubson404.carrentalapp.model.mappers.CustomerMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class CustomerCreateServiceTest {

    @Mock
    CustomerRepository customerRepository;
    @Mock
    CustomerMapper customerMapper;
    @InjectMocks
    CustomerCreateService customerCreateService;

    @Test
    void createCustomer_callsCustomerRepository() {
        // given
        when(customerMapper.toCustomer(any(CustomerDTO.class))).thenReturn(new Customer());
        CustomerDTO customerDTO = CustomerDTO.builder()
                .firstName("testFirstName")
                .lastName("testLastName")
                .email("testEmail")
                .address("testAddress")
                .build();
        // when
        customerCreateService.createCustomer(customerDTO);
        // then
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void createCustomer_whenFirstNameFieldIsBlank_ThrowsExceptionAndDoesNotCallRepository() {
        // given
        CustomerDTO customerDTO = CustomerDTO.builder()
                .firstName("  ")
                .lastName("testLastName")
                .email("testEmail")
                .address("testAddress")
                .build();

        // when
        Throwable result = catchThrowable(() -> customerCreateService.createCustomer(customerDTO));
        // then
        assertThat(result).isExactlyInstanceOf(InsufficientDataException.class);
        verify(customerRepository, times(0)).save(any(Customer.class));
    }

    @Test
    void createCustomer_whenLastNameFieldIsBlank_ThrowsExceptionAndDoesNotCallRepository() {
        // given
        CustomerDTO customerDTO = CustomerDTO.builder()
                .firstName("testFirstName")
                .lastName("  ")
                .email("testEmail")
                .address("testAddress")
                .build();

        // when
        Throwable result = catchThrowable(() -> customerCreateService.createCustomer(customerDTO));
        // then
        assertThat(result).isExactlyInstanceOf(InsufficientDataException.class);
        verify(customerRepository, times(0)).save(any(Customer.class));
    }

    @Test
    void createCustomer_whenEmailFieldIsBlank_ThrowsExceptionAndDoesNotCallRepository() {
        // given
        CustomerDTO customerDTO = CustomerDTO.builder()
                .firstName("testFirstName")
                .lastName("testLastName")
                .email("  ")
                .address("testAddress")
                .build();

        // when
        Throwable result = catchThrowable(() -> customerCreateService.createCustomer(customerDTO));
        // then
        assertThat(result).isExactlyInstanceOf(InsufficientDataException.class);
        verify(customerRepository, times(0)).save(any(Customer.class));
    }

    @Test
    void createCustomer_whenAddressFieldIsBlank_ThrowsExceptionAndDoesNotCallRepository() {
        // given
        CustomerDTO customerDTO = CustomerDTO.builder()
                .firstName("testFirstName")
                .lastName("testLastName")
                .email("testEmail")
                .address("   ")
                .build();

        // when
        Throwable result = catchThrowable(() -> customerCreateService.createCustomer(customerDTO));
        // then
        assertThat(result).isExactlyInstanceOf(InsufficientDataException.class);
        verify(customerRepository, times(0)).save(any(Customer.class));
    }
}

