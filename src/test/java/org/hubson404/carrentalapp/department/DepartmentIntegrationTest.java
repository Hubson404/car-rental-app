package org.hubson404.carrentalapp.department;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hubson404.carrentalapp.domain.Department;
import org.hubson404.carrentalapp.model.DepartmentDTO;
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
class DepartmentIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    DepartmentRepository departmentRepository;

    @BeforeEach
    void setUp() {
        departmentRepository.deleteAll();
    }

    @Test
    void findAllDepartments_returnsListOfDepartmentsAndStatusCode200() throws Exception {
        // given
        int expectedNumberOfEntriesInRepository = 3;
        for (int i = 0; i < expectedNumberOfEntriesInRepository; i++) {
            departmentRepository.save(new Department());
        }

        MockHttpServletRequestBuilder get = get("/departments");

        // when
        MvcResult result = mockMvc.perform(get).andReturn();

        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        List<Department> departments = departmentRepository.findAll();
        assertThat(departments.size()).isEqualTo(expectedNumberOfEntriesInRepository);
    }

    @Test
    void createNewDepartment_createsNewDepartmentAndReturnsStatusCode201() throws Exception {
        // given
        int expectedNumberOfEntriesInRepository = 1;
        DepartmentDTO departmentDTO = DepartmentDTO.builder()
                .address("some address")
                .build();

        String requestBody = objectMapper.writeValueAsString(departmentDTO);
        MockHttpServletRequestBuilder post = post("/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        // when
        MvcResult result = mockMvc.perform(post).andReturn();

        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        List<Department> departments = departmentRepository.findAll();
        assertThat(departments.size()).isEqualTo(expectedNumberOfEntriesInRepository);
    }

    @Test
    void createNewDepartment_WhenAddressFieldIsBlank_ReturnsStatusCode400() throws Exception {
        // given
        int expectedNumberOfEntriesInRepository = 0;
        DepartmentDTO departmentDTO = DepartmentDTO.builder()
                .address(" ")
                .build();

        String requestBody = objectMapper.writeValueAsString(departmentDTO);
        MockHttpServletRequestBuilder post = post("/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        // when
        MvcResult result = mockMvc.perform(post).andReturn();

        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        List<Department> departments = departmentRepository.findAll();
        assertThat(departments.size()).isEqualTo(expectedNumberOfEntriesInRepository);
    }

    @Test
    void findDepartmentById_ReturnsDepartmentByIdAndReturnStatusCode200() throws Exception {
        // given
        departmentRepository.save(new Department());
        Department savedDepartment = departmentRepository.save(new Department());
        Long savedDepartmentId = savedDepartment.getId();

        // when
        MockHttpServletRequestBuilder get = get("/departments/" + savedDepartmentId);
        MvcResult result = mockMvc.perform(get).andReturn();

        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Optional<Department> departmentOptional = departmentRepository.findById(savedDepartmentId);
        assertThat(departmentOptional.isPresent()).isTrue();

    }

    @Test
    void findDepartmentById_WhenDepartmentByGivenIdDoesNotExist_ReturnsStatusCode404() throws Exception {
        // given
        Long idOfNonexistentDepartment = 1L;

        // when
        MockHttpServletRequestBuilder get = get("/departments/" + idOfNonexistentDepartment);
        MvcResult result = mockMvc.perform(get).andReturn();

        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        Optional<Department> departmentOptional = departmentRepository.findById(idOfNonexistentDepartment);
        assertThat(departmentOptional.isEmpty()).isTrue();

    }

    @Test
    void deleteDepartment_deletesDepartmentAndReturnStatusCode200() throws Exception {
        // given
        int expectedNumberOfEntriesInRepository = 3;
        for (int i = 0; i < expectedNumberOfEntriesInRepository; i++) {
            departmentRepository.save(new Department());
        }
        Department savedDepartment = departmentRepository.save(new Department());
        Long savedDepartmentId = savedDepartment.getId();

        // when
        MockHttpServletRequestBuilder delete = delete("/departments/" + savedDepartmentId);
        MvcResult result = mockMvc.perform(delete).andReturn();

        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        List<Department> departments = departmentRepository.findAll();
        assertThat(departments.size()).isEqualTo(expectedNumberOfEntriesInRepository);

    }

    @Test
    void deleteDepartmentById_WhenDepartmentByGivenIdDoesNotExist_ReturnsStatusCode404() throws Exception {
        // given
        int expectedNumberOfEntriesInRepository = 3;
        for (int i = 0; i < expectedNumberOfEntriesInRepository; i++) {
            departmentRepository.save(new Department());
        }
        Long idOfNonexistentDepartment = 777L;

        // when
        MockHttpServletRequestBuilder delete = delete("/departments/" + idOfNonexistentDepartment);
        MvcResult result = mockMvc.perform(delete).andReturn();

        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        List<Department> departments = departmentRepository.findAll();
        assertThat(departments.size()).isEqualTo(expectedNumberOfEntriesInRepository);
    }
}
