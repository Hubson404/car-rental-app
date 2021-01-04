package org.hubson404.carrentalapp.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hubson404.carrentalapp.department.DepartmentRepository;
import org.hubson404.carrentalapp.domain.Department;
import org.hubson404.carrentalapp.domain.Employee;
import org.hubson404.carrentalapp.model.EmployeeDTO;
import org.hubson404.carrentalapp.model.mappers.DepartmentMapper;
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
class EmployeeIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();
    }

    @Test
    void findAllEmployees_returnsListOfEmployeesAndStatusCode200() throws Exception {
        // given
        int expectedNumberOfEntriesInRepository = 3;
        for (int i = 0; i < expectedNumberOfEntriesInRepository; i++) {
            employeeRepository.save(new Employee());
        }

        MockHttpServletRequestBuilder get = get("/employees");

        // when
        MvcResult result = mockMvc.perform(get).andReturn();

        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees.size()).isEqualTo(expectedNumberOfEntriesInRepository);
    }

    @Test
    void createNewEmployee_createsNewEmployeeAndReturnsStatusCode201() throws Exception {
        // given
        int expectedNumberOfEntriesInRepository = 1;

        Department department = departmentRepository.save(new Department(null, "Warsaw", null, null));
        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .firstName("some firstname")
                .lastName("some lastname")
                .department(departmentMapper.toDepartmentDTO(department))
                .build();

        String requestBody = objectMapper.writeValueAsString(employeeDTO);
        MockHttpServletRequestBuilder post = post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        // when
        MvcResult result = mockMvc.perform(post).andReturn();

        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees.size()).isEqualTo(expectedNumberOfEntriesInRepository);
    }


    @Test
    void createNewEmployee_WhenFirstNameFieldIsBlank_StatusCode400() throws Exception {
        // given
        int expectedNumberOfEntriesInRepository = 0;

        Department department = departmentRepository.save(new Department(null, "Warsaw", null, null));
        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .firstName("  ")
                .lastName("some lastname")
                .department(departmentMapper.toDepartmentDTO(department))
                .build();

        String requestBody = objectMapper.writeValueAsString(employeeDTO);
        MockHttpServletRequestBuilder post = post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        // when
        MvcResult result = mockMvc.perform(post).andReturn();

        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees.size()).isEqualTo(expectedNumberOfEntriesInRepository);
    }

    @Test
    void createNewEmployee_WhenLastNameFieldIsBlank_StatusCode400() throws Exception {
        // given
        int expectedNumberOfEntriesInRepository = 0;

        Department department = departmentRepository.save(new Department(null, "Warsaw", null, null));
        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .firstName("some firstName")
                .lastName("  ")
                .department(departmentMapper.toDepartmentDTO(department))
                .build();

        String requestBody = objectMapper.writeValueAsString(employeeDTO);
        MockHttpServletRequestBuilder post = post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        // when
        MvcResult result = mockMvc.perform(post).andReturn();

        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees.size()).isEqualTo(expectedNumberOfEntriesInRepository);
    }

    @Test
    void createNewEmployee_WhenDepartmentFieldIsNull_StatusCode400() throws Exception {
        // given
        int expectedNumberOfEntriesInRepository = 0;

        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .firstName("some firstName")
                .lastName("some lastName")
                .build();

        String requestBody = objectMapper.writeValueAsString(employeeDTO);
        MockHttpServletRequestBuilder post = post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        // when
        MvcResult result = mockMvc.perform(post).andReturn();

        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees.size()).isEqualTo(expectedNumberOfEntriesInRepository);
    }

    @Test
    void findEmployeeById_ReturnsEmployeeByIdAndReturnStatusCode200() throws Exception {
        // given
        employeeRepository.save(new Employee());
        Employee savedEmployee = employeeRepository.save(new Employee());
        Long savedEmployeeId = savedEmployee.getId();

        // when
        MockHttpServletRequestBuilder get = get("/employees/" + savedEmployeeId);
        MvcResult result = mockMvc.perform(get).andReturn();

        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Optional<Employee> departmentOptional = employeeRepository.findById(savedEmployeeId);
        assertThat(departmentOptional.isPresent()).isTrue();

    }

    @Test
    void findEmployeeById_WhenEmployeeByGivenIdDoesNotExist_ReturnsStatusCode404() throws Exception {
        // given
        Long idOfNonexistentEmployee = 1L;

        // when
        MockHttpServletRequestBuilder get = get("/employees/" + idOfNonexistentEmployee);
        MvcResult result = mockMvc.perform(get).andReturn();

        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        Optional<Employee> departmentOptional = employeeRepository.findById(idOfNonexistentEmployee);
        assertThat(departmentOptional.isEmpty()).isTrue();

    }

    @Test
    void deleteEmployee_deletesEmployeeAndReturnStatusCode200() throws Exception {
        // given
        int expectedNumberOfEntriesInRepository = 3;
        for (int i = 0; i < expectedNumberOfEntriesInRepository; i++) {
            employeeRepository.save(new Employee());
        }
        Employee savedEmployee = employeeRepository.save(new Employee());
        Long savedEmployeeId = savedEmployee.getId();

        // when
        MockHttpServletRequestBuilder delete = delete("/employees/" + savedEmployeeId);
        MvcResult result = mockMvc.perform(delete).andReturn();

        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees.size()).isEqualTo(expectedNumberOfEntriesInRepository);

    }

    @Test
    void deleteEmployeeById_WhenEmployeeByGivenIdDoesNotExist_ReturnsStatusCode404() throws Exception {
        // given
        int expectedNumberOfEntriesInRepository = 3;
        for (int i = 0; i < expectedNumberOfEntriesInRepository; i++) {
            employeeRepository.save(new Employee());
        }
        Long idOfNonexistentEmployee = 777L;

        // when
        MockHttpServletRequestBuilder delete = delete("/employees/" + idOfNonexistentEmployee);
        MvcResult result = mockMvc.perform(delete).andReturn();

        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees.size()).isEqualTo(expectedNumberOfEntriesInRepository);
    }
}
