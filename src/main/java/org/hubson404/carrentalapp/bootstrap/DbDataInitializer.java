package org.hubson404.carrentalapp.bootstrap;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.*;
import org.hubson404.carrentalapp.domain.enums.CarBodyColor;
import org.hubson404.carrentalapp.domain.enums.CarBodyType;
import org.hubson404.carrentalapp.domain.enums.CarStatus;
import org.hubson404.carrentalapp.domain.enums.EmployeePosition;
import org.hubson404.carrentalapp.repositories.*;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
@Profile("dev")
@RequiredArgsConstructor
public class DbDataInitializer {

    private final CarRentalCompanyRepository carRentalCompanyRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void checkDbStatus() {
        final List<CarRentalCompany> all = carRentalCompanyRepository.findAll();
        if (all.isEmpty()) {
            initializeDbData();
        }
    }

    private void initializeDbData() {

        CarRentalCompany savedCompany = createCarRentalCompany();

        Department department = createDepartment(savedCompany, "Gdansk");

        createEmployee(department, "John", "Carson", EmployeePosition.MANAGER);
        createEmployee(department, "Tim", "Worker", EmployeePosition.BASIC);

        createAudiCar(department, CarBodyColor.WHITE);
        createAudiCar(department, CarBodyColor.BLUE);
        createAudiCar(department, CarBodyColor.RED);

        createCustomer("Tom", "Driver", "Warsaw");
        createCustomer("Matt", "Walker", "Gdansk");
    }

    private void createCustomer(String name, String lastname, String address) {
        Customer build = Customer.builder().firstName(name).lastName(lastname).address(address)
                .email(name + "." + lastname + "@email.com").build();
        customerRepository.save(build);
    }

    private void createAudiCar(Department department, CarBodyColor color) {
        Car build = Car.builder().brand("Audi").model("A4").carBodyType(CarBodyType.STATION_WAGON)
                .carStatus(CarStatus.AVAILABLE)
                .color(color)
                .costPerDay(50d)
                .productionYear(2010)
                .mileage(125L)
                .department(department)
                .build();
        carRepository.save(build);
    }

    private void createEmployee(Department department, String name, String lastName, EmployeePosition position) {
        Employee employee = new Employee();
        employee.setFirstName(name);
        employee.setLastName(lastName);
        employee.setPosition(position);
        employee.setDepartment(department);
        employeeRepository.save(employee);
    }

    private Department createDepartment(CarRentalCompany savedCompany, String address) {
        Department department = new Department();
        department.setAddress(address);
        department.setCarRentalCompany(savedCompany);
        return departmentRepository.save(department);
    }

    private CarRentalCompany createCarRentalCompany() {
        CarRentalCompany company = new CarRentalCompany();
        company.setCompanyName("comp-details.name");
        company.setDomainAddress("comp-details.domain");
        company.setContactAddress("comp-details.address");
        company.setOwnersName("comp-details.owner");
        company.setCompanyLogo("comp-details.logo");
        return carRentalCompanyRepository.save(company);
    }
}
