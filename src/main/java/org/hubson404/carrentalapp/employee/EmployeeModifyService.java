package org.hubson404.carrentalapp.employee;


import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.Employee;
import org.hubson404.carrentalapp.domain.enums.EmployeePosition;
import org.hubson404.carrentalapp.exceptions.EmployeeNotFoundException;
import org.hubson404.carrentalapp.exceptions.IllegalEmployeeIdException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeModifyService {

    private final EmployeeRepository employeeRepository;

    public Employee promoteEmployee(Long id) {

        Optional<Employee> optionalEmployee = employeeRepository.findEmployeeByIdAndPosition(id, EmployeePosition.BASIC);
        Employee employee = optionalEmployee.orElseThrow(() -> new IllegalEmployeeIdException(
                "Could not find employee by given id or employee position is already set to 'MANAGER'."));
        employee.setPosition(EmployeePosition.MANAGER);
        return employee;
    }

    public Employee demoteEmployee(Long id) {

        Optional<Employee> optionalEmployee = employeeRepository.findEmployeeByIdAndPosition(id, EmployeePosition.MANAGER);
        Employee employee = optionalEmployee.orElseThrow(() -> new IllegalEmployeeIdException(
                "Could not find employee by given id or employee position is already set to 'BASIC'."));
        employee.setPosition(EmployeePosition.BASIC);
        return employee;
    }

    public void deleteEmployeeById(Long id) {
        employeeRepository.findById(id).ifPresentOrElse(
                employee -> employeeRepository.deleteById(employee.getId()),
                () -> {
                    throw new EmployeeNotFoundException("Could not find Employee with id: " + id);
                });
    }
}
