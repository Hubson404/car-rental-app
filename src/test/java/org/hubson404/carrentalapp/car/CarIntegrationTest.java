package org.hubson404.carrentalapp.car;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hubson404.carrentalapp.domain.Car;
import org.hubson404.carrentalapp.domain.Department;
import org.hubson404.carrentalapp.domain.enums.CarBodyColor;
import org.hubson404.carrentalapp.domain.enums.CarBodyType;
import org.hubson404.carrentalapp.domain.enums.CarStatus;
import org.hubson404.carrentalapp.model.CarDto;
import org.hubson404.carrentalapp.model.mappers.CarMapper;
import org.hubson404.carrentalapp.model.mappers.DepartmentMapper;
import org.hubson404.carrentalapp.repositories.CarRepository;
import org.hubson404.carrentalapp.repositories.DepartmentRepository;
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
class CarIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    CarMapper carMapper;
    @Autowired
    CarRepository carRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    @BeforeEach
    void setUp() {
        carRepository.deleteAll();
        departmentRepository.deleteAll();
    }

    @Test
    void findAllCars_returnsListOfCarsAndStatusCode200() throws Exception {
        // given
        int expectedNumberOfEntriesInRepository = 3;
        for (int i = 0; i < expectedNumberOfEntriesInRepository; i++) {
            carRepository.save(new Car());
        }
        MockHttpServletRequestBuilder get = get("/cars");
        // when
        MvcResult result = mockMvc.perform(get).andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        List<Car> cars = carRepository.findAll();
        assertThat(cars.size()).isEqualTo(expectedNumberOfEntriesInRepository);

    }

    @Test
    void findCarById_returnsCarByIdAndStatusCode200() throws Exception {
        // given
        carRepository.save(new Car());
        Car savedCar = carRepository.save(new Car());
        Long savedCarId = savedCar.getId();
        // when
        MockHttpServletRequestBuilder get = get("/cars/" + savedCarId);
        MvcResult result = mockMvc.perform(get).andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Optional<Car> carOptional = carRepository.findById(savedCarId);
        assertThat(carOptional.isPresent()).isTrue();
    }

    @Test
    void addCar_createsNewCarAndReturnsStatusCode201() throws Exception {
        // given
        int expectedNumberOfEntriesInRepository = 1;

        Department department = departmentRepository.save(new Department(
                null, "Warsaw", null, null));
        CarDto carDTO = org.hubson404.carrentalapp.model.CarDto.builder()
                .model("testModel")
                .brand("testBrand")
                .productionYear(1999)
                .carBodyType("VAN")
                .color("BLACK")
                .carStatus("AVAILABLE")
                .costPerDay(1000d)
                .mileage(0L)
                .department(departmentMapper.toDepartmentDto(department))
                .build();

        String requestBody = objectMapper.writeValueAsString(carDTO);
        MockHttpServletRequestBuilder post = post("/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);
        // when
        MvcResult result = mockMvc.perform(post).andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        List<Car> cars = carRepository.findAll();
        assertThat(cars.size()).isEqualTo(expectedNumberOfEntriesInRepository);
    }

    @Test
    void modifyCar_modifiesCarAndReturnsStatusCode202() throws Exception {
        // given
        Department warsaw = departmentRepository.save(new Department(
                null, "Warsaw", null, null));
        Car savedCar = carRepository.save(new Car(
                null, "Mmm", "Bbb", CarBodyType.COUPE, 1999,
                CarBodyColor.WHITE, 0L, CarStatus.AVAILABLE, 100d, warsaw));
        Long savedCarId = savedCar.getId();

        CarDto carDTO = org.hubson404.carrentalapp.model.CarDto.builder().model("modifiedModel").build();
        // when
        String requestBody = objectMapper.writeValueAsString(carDTO);

        MockHttpServletRequestBuilder patch = patch("/cars/" + savedCarId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);
        MvcResult result = mockMvc.perform(patch).andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        Optional<Car> modifiedCar = carRepository.findById(savedCarId);

        assertThat(modifiedCar.isPresent()).isTrue();
        assertThat(modifiedCar.get().getModel()).isEqualTo("modifiedModel");
        assertThat(departmentRepository.findById(warsaw.getId()).orElseThrow().getCars().size()).isEqualTo(1);
    }

    @Test
    void deleteCarById_deletesEmployeeAndReturnStatusCode200() throws Exception {
        // given
        int expectedNumberOfEntriesInRepository = 3;
        for (int i = 0; i < expectedNumberOfEntriesInRepository; i++) {
            carRepository.save(new Car());
        }
        Car savedCar = carRepository.save(new Car());
        Long savedCarId = savedCar.getId();
        // when
        MockHttpServletRequestBuilder delete = delete("/cars/" + savedCarId);
        MvcResult result = mockMvc.perform(delete).andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        List<Car> cars = carRepository.findAll();
        assertThat(cars.size()).isEqualTo(expectedNumberOfEntriesInRepository);
    }
}

