package org.hubson404.carrentalapp;

import java.util.List;

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

@Transactional
@Component
@RequiredArgsConstructor
@Profile("dev")
public class DbInitializer {

    private final CarRentalCompanyRepository carRentalCompanyRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;

    private Boolean isEmpty = false;

    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {

        final List<CarRentalCompany> all = carRentalCompanyRepository.findAll();
        if (all.isEmpty()) {
            initializeDbData();
            CarRentalCompany savedCompany = getCarRentalCompany();

            Department department = getDepartment(savedCompany, "Gdansk");

            getEmployee(department, "John", "Carson", EmployeePosition.MANAGER);
            getEmployee(department, "Tim", "Worker", EmployeePosition.BASIC);

            getAudi(department, CarBodyColor.WHITE);
            getAudi(department, CarBodyColor.BLUE);
            getAudi(department, CarBodyColor.RED);

            getCustomer("Tom", "Driver", "Warsaw");
            getCustomer("Matt", "Walker", "Gdansk");
        }
    }

    private Customer getCustomer(String name, String lastname, String address) {
        Customer build = Customer.builder().firstName(name).lastName(lastname).address(address)
                .email(name + "." + lastname + "@email.com").build();
        return customerRepository.save(build);
    }

    private Car getAudi(Department department, CarBodyColor color) {
        Car build = Car.builder().brand("Audi").model("A4").carBodyType(CarBodyType.STATION_WAGON)
                .carStatus(CarStatus.AVAILABLE)
                .color(color)
                .costPerDay(50d)
                .productionYear(2010)
                .mileage(125L)
                .department(department)
                .build();
        return carRepository.save(build);
    }

    private Employee getEmployee(Department department, String name, String lastName, EmployeePosition position) {
        Employee employee = new Employee();
        employee.setFirstName(name);
        employee.setLastName(lastName);
        employee.setPosition(position);
        employee.setDepartment(department);
        return employeeRepository.save(employee);
    }

    private Department getDepartment(CarRentalCompany savedCompany, String address) {
        Department department = new Department();
        department.setAddress(address);
        department.setCarRentalCompany(savedCompany);
        return departmentRepository.save(department);
    }

    private CarRentalCompany getCarRentalCompany() {
        CarRentalCompany company = new CarRentalCompany();
        company.setCompanyName("comp-details.name");
        company.setDomainAddress("comp-details.domain");
        company.setContactAddress("comp-details.address");
        company.setOwnersName("comp-details.owner");
        company.setCompanyLogo("comp-details.logo");
        return carRentalCompanyRepository.save(company);
    }

    public void setEmpty(Boolean empty) {
        isEmpty = empty;
    }
}
