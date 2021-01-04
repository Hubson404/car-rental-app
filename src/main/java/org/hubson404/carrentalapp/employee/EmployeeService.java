package org.hubson404.carrentalapp.employee;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hubson404.carrentalapp.department.DepartmentRepository;
import org.hubson404.carrentalapp.domain.Department;
import org.hubson404.carrentalapp.domain.Employee;
import org.hubson404.carrentalapp.domain.enums.EmployeePosition;
import org.hubson404.carrentalapp.exceptions.DepartmentNotFoundException;
import org.hubson404.carrentalapp.exceptions.EmployeeNotFoundException;
import org.hubson404.carrentalapp.exceptions.InsufficientDataException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findEmployeeById(Long id) {
        Optional<Employee> byId = employeeRepository.findById(id);
        return byId.orElseThrow(
                () -> new EmployeeNotFoundException("Could not find department with id: " + id));
    }

    public Employee createEmployee(Employee employee) {

        if (employee.getFirstName() == null || employee.getFirstName().isBlank()) {
            throw new InsufficientDataException("Employees first name must be specified.");
        }
        if (employee.getLastName() == null || employee.getLastName().isBlank()) {
            throw new InsufficientDataException("Employees last name must be specified.");
        }
        if (employee.getDepartment() == null) {
            throw new InsufficientDataException("Employees department must be specified.");
        }
        if (employee.getPosition() == null) {
            log.info("Position was not specified, setting position to 'BASIC'");
            employee.setPosition(EmployeePosition.BASIC);
        }

        Optional<Department> byId = departmentRepository.findById(employee.getDepartment().getId());
        Department department = byId.orElseThrow(() -> new DepartmentNotFoundException(
                "Could not find department with id: " + employee.getDepartment().getId()));
        employee.setDepartment(department);

        return employeeRepository.save(employee);

    }

    public Employee deleteEmployeeById(Long id) {
        Employee employeeById = findEmployeeById(id);
        employeeRepository.deleteById(id);
        return employeeById;
    }

    public List<Employee> findEmployeeByDepartmentId(Long departmentId) {
        return employeeRepository.findEmployeeByDepartment_Id(departmentId);
    }

    public Employee promoteEmployee(Long id) {

        Optional<Employee> optionalEmployee = employeeRepository.findEmployeeByIdAndPosition(id, EmployeePosition.BASIC);

        Employee employee = optionalEmployee.orElseThrow(() -> new EmployeeNotFoundException(
                "Could not find employee by given id or employee is already promoted."));

        employee.setPosition(EmployeePosition.MANAGER);

        return employee;
    }

    public Employee demoteEmployee(Long id) {

        Optional<Employee> optionalEmployee = employeeRepository.findEmployeeByIdAndPosition(id, EmployeePosition.MANAGER);

        Employee employee = optionalEmployee.orElseThrow(() -> new EmployeeNotFoundException(
                "Could not find employee by given id or employee is already 'BASIC' employee."));

        employee.setPosition(EmployeePosition.BASIC);

        return employee;
    }
}
