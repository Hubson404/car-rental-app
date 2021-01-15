package org.hubson404.carrentalapp.customer;

import org.hubson404.carrentalapp.domain.Customer;
import org.hubson404.carrentalapp.exceptions.CustomerNotFoundException;
import org.hubson404.carrentalapp.exceptions.InsufficientDataException;
import org.hubson404.carrentalapp.model.CustomerDto;
import org.hubson404.carrentalapp.model.mappers.CustomerMapper;
import org.hubson404.carrentalapp.repositories.CustomerRepository;
import org.hubson404.carrentalapp.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;
    @Mock
    CustomerMapper customerMapper;
    @InjectMocks
    CustomerService customerService;

    @Test
    void createCustomer_callsCustomerRepository() {
        // given
        when(customerMapper.toCustomer(any(CustomerDto.class))).thenReturn(new Customer());
        CustomerDto customerDTO = CustomerDto.builder()
                .firstName("testFirstName")
                .lastName("testLastName")
                .email("testEmail")
                .address("testAddress")
                .build();
        // when
        customerService.createCustomer(customerDTO);
        // then
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void createCustomer_whenFirstNameFieldIsBlank_ThrowsExceptionAndDoesNotCallRepository() {
        // given
        CustomerDto customerDTO = CustomerDto.builder()
                .firstName("  ")
                .lastName("testLastName")
                .email("testEmail")
                .address("testAddress")
                .build();

        // when
        Throwable result = catchThrowable(() -> customerService.createCustomer(customerDTO));
        // then
        assertThat(result).isExactlyInstanceOf(InsufficientDataException.class);
        verify(customerRepository, times(0)).save(any(Customer.class));
    }

    @Test
    void createCustomer_whenLastNameFieldIsBlank_ThrowsExceptionAndDoesNotCallRepository() {
        // given
        CustomerDto customerDTO = CustomerDto.builder()
                .firstName("testFirstName")
                .lastName("  ")
                .email("testEmail")
                .address("testAddress")
                .build();

        // when
        Throwable result = catchThrowable(() -> customerService.createCustomer(customerDTO));
        // then
        assertThat(result).isExactlyInstanceOf(InsufficientDataException.class);
        verify(customerRepository, times(0)).save(any(Customer.class));
    }

    @Test
    void createCustomer_whenEmailFieldIsBlank_ThrowsExceptionAndDoesNotCallRepository() {
        // given
        CustomerDto customerDTO = CustomerDto.builder()
                .firstName("testFirstName")
                .lastName("testLastName")
                .email("  ")
                .address("testAddress")
                .build();

        // when
        Throwable result = catchThrowable(() -> customerService.createCustomer(customerDTO));
        // then
        assertThat(result).isExactlyInstanceOf(InsufficientDataException.class);
        verify(customerRepository, times(0)).save(any(Customer.class));
    }

    @Test
    void createCustomer_whenAddressFieldIsBlank_ThrowsExceptionAndDoesNotCallRepository() {
        // given
        CustomerDto customerDTO = CustomerDto.builder()
                .firstName("testFirstName")
                .lastName("testLastName")
                .email("testEmail")
                .address("   ")
                .build();

        // when
        Throwable result = catchThrowable(() -> customerService.createCustomer(customerDTO));
        // then
        assertThat(result).isExactlyInstanceOf(InsufficientDataException.class);
        verify(customerRepository, times(0)).save(any(Customer.class));
    }

    @Test
    void findAll_callsRepository() {
        // given
        when(customerRepository.findAll()).thenReturn(List.of(new Customer()));
        when(customerMapper.toCustomerDto(any(Customer.class))).thenReturn(new CustomerDto());
        // when
        customerService.findAll();
        // then
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void findCustomerById_callsCarRepository() {
        // given
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(new Customer()));
        when(customerMapper.toCustomerDto(any(Customer.class))).thenReturn(new CustomerDto());
        // when
        customerService.findCustomerById(anyLong());
        // then
        verify(customerRepository, times(1)).findById(anyLong());
    }

    @Test
    void findCustomerById_CustomerByGivenIdDoesNotExist_callsCarRepositoryAndThrowsException() {
        // given
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());
        // when
        Throwable result = catchThrowable(() -> customerService.findCustomerById(anyLong()));
        // then
        assertThat(result).isExactlyInstanceOf(CustomerNotFoundException.class);
        verify(customerRepository, times(1)).findById(anyLong());
    }

    @Test
    void modifyCustomer_CustomerByGivenIdDoesNotExist_callsRepositoryAndThrowsException() {
        // given
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());
        // when
        Throwable result = catchThrowable(() -> customerService.modifyCustomer(1L, new CustomerDto()));
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
        customerService.modifyCustomer(1L, customerDTO);
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
        Throwable result = catchThrowable(() -> customerService.modifyCustomer(1L, customerDTO));
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
        customerService.modifyCustomer(1L, customerDTO);
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
        Throwable result = catchThrowable(() -> customerService.modifyCustomer(1L, customerDTO));
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
        customerService.modifyCustomer(1L, customerDTO);
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
        Throwable result = catchThrowable(() -> customerService.modifyCustomer(1L, customerDTO));
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
        customerService.modifyCustomer(1L, customerDTO);
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
        Throwable result = catchThrowable(() -> customerService.modifyCustomer(1L, customerDTO));
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
        customerService.deleteCustomerById(1L);
        // then
        verify(customerRepository, times(1)).findById(anyLong());
        verify(customerRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteCustomerById_CustomerByIdNotFound_ThrowsException() {
        // given
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());
        // when
        Throwable result = catchThrowable(() -> customerService.deleteCustomerById(anyLong()));
        // then
        assertThat(result).isExactlyInstanceOf(CustomerNotFoundException.class);
        verify(customerRepository, times(1)).findById(anyLong());
        verify(customerRepository, times(0)).deleteById(anyLong());
    }
}

