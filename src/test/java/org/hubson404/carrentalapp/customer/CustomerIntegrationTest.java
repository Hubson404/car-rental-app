package org.hubson404.carrentalapp.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hubson404.carrentalapp.domain.Customer;
import org.hubson404.carrentalapp.model.CustomerDto;
import org.hubson404.carrentalapp.model.mappers.CustomerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    void findAll_returnsListOfCustomersAndStatusCode200() throws Exception {
        // given
        int expectedNumberOfEntriesInRepository = 3;
        for (int i = 0; i < expectedNumberOfEntriesInRepository; i++) {
            customerRepository.save(new Customer());
        }
        MockHttpServletRequestBuilder get = get("/customers");
        // when
        MvcResult result = mockMvc.perform(get).andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers.size()).isEqualTo(expectedNumberOfEntriesInRepository);
    }

    @Test
    void findCustomerById_returnsCustomerByIdAndStatusCode200() throws Exception {
        // given
        customerRepository.save(new Customer());
        Customer savedCustomer = customerRepository.save(new Customer());
        Long savedCustomerId = savedCustomer.getId();
        // when
        MockHttpServletRequestBuilder get = get("/customers/" + savedCustomerId);
        MvcResult result = mockMvc.perform(get).andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Optional<Customer> customerOptional = customerRepository.findById(savedCustomerId);
        assertThat(customerOptional.isPresent()).isTrue();
    }

    @Test
    void createCustomer_createsNewCustomerAndReturnsStatusCode201() throws Exception {
        // given
        int expectedNumberOfEntriesInRepository = 1;

        CustomerDto customerDTO = CustomerDto.builder()
                .firstName("testName")
                .lastName("testLastName")
                .email("testEmail")
                .address("testAddress")
                .build();

        String requestBody = objectMapper.writeValueAsString(customerDTO);
        MockHttpServletRequestBuilder post = post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);
        // when
        MvcResult result = mockMvc.perform(post).andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers.size()).isEqualTo(expectedNumberOfEntriesInRepository);
    }

    @Test
    void modifyCustomer_modifiesCustomerAndReturnsStatusCode202() throws Exception {
        // given
        Customer savedCustomer = customerRepository.save(new Customer(
                null, "testName", "testLastName",
                "testEmail", "testAddress"));
        Long savedCustomerId = savedCustomer.getId();

        CustomerDto customerDTO = CustomerDto.builder().firstName("modifiedName").build();
        // when
        String requestBody = objectMapper.writeValueAsString(customerDTO);

        MockHttpServletRequestBuilder patch = patch("/customers/" + savedCustomerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);
        MvcResult result = mockMvc.perform(patch).andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        Optional<Customer> customerOptional = customerRepository.findById(savedCustomerId);

        assertThat(customerOptional.isPresent()).isTrue();
        assertThat(customerOptional.get().getFirstName()).isEqualTo("modifiedName");
    }

    @Test
    void deleteCustomerById_deletesEmployeeAndReturnStatusCode200() throws Exception {
        // given
        int expectedNumberOfEntriesInRepository = 3;
        for (int i = 0; i < expectedNumberOfEntriesInRepository; i++) {
            customerRepository.save(new Customer());
        }
        Customer savedCustomer = customerRepository.save(new Customer());
        Long savedCustomerId = savedCustomer.getId();
        // when
        MockHttpServletRequestBuilder delete = delete("/customers/" + savedCustomerId);
        MvcResult result = mockMvc.perform(delete).andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers.size()).isEqualTo(expectedNumberOfEntriesInRepository);
    }
}
