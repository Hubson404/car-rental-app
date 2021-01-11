package org.hubson404.carrentalapp.employee;


import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.Employee;
import org.hubson404.carrentalapp.domain.enums.EmployeePosition;
import org.hubson404.carrentalapp.exceptions.EmployeeNotFoundException;
import org.hubson404.carrentalapp.exceptions.IllegalEmployeeIdException;
import org.hubson404.carrentalapp.model.EmployeeDto;
import org.hubson404.carrentalapp.model.mappers.EmployeeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeModifyService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeDto promoteEmployee(Long id) {

        Optional<Employee> optionalEmployee = employeeRepository.findEmployeeByIdAndPosition(id, EmployeePosition.BASIC);
        Employee employee = optionalEmployee.orElseThrow(() -> new IllegalEmployeeIdException(id, EmployeePosition.MANAGER));
        employee.setPosition(EmployeePosition.MANAGER);
        return employeeMapper.toEmployeeDto(employee);
    }

    public EmployeeDto demoteEmployee(Long id) {

        Optional<Employee> optionalEmployee = employeeRepository.findEmployeeByIdAndPosition(id, EmployeePosition.MANAGER);
        Employee employee = optionalEmployee.orElseThrow(() -> new IllegalEmployeeIdException(id, EmployeePosition.BASIC));
        employee.setPosition(EmployeePosition.BASIC);
        return employeeMapper.toEmployeeDto(employee);
    }

    public void deleteEmployeeById(Long id) {
        employeeRepository.findById(id).ifPresentOrElse(
                employee -> employeeRepository.deleteById(employee.getId()),
                () -> {
                    throw new EmployeeNotFoundException(id);
                });
    }
}
