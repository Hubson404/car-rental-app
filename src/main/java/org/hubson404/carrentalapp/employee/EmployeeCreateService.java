package org.hubson404.carrentalapp.employee;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hubson404.carrentalapp.department.DepartmentRepository;
import org.hubson404.carrentalapp.domain.Department;
import org.hubson404.carrentalapp.domain.Employee;
import org.hubson404.carrentalapp.exceptions.DepartmentNotFoundException;
import org.hubson404.carrentalapp.exceptions.InsufficientDataException;
import org.hubson404.carrentalapp.model.EmployeeDTO;
import org.hubson404.carrentalapp.model.mappers.EmployeeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeCreateService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {

        if (employeeDTO.getFirstName() == null || employeeDTO.getFirstName().isBlank()) {
            throw new InsufficientDataException("Employees first name must be specified.");
        }
        if (employeeDTO.getLastName() == null || employeeDTO.getLastName().isBlank()) {
            throw new InsufficientDataException("Employees last name must be specified.");
        }
        if (employeeDTO.getDepartment() == null) {
            throw new InsufficientDataException("Employees department must be specified.");
        }
        if (employeeDTO.getPosition() == null) {
            log.info("Position was not specified, setting position to 'BASIC'");
            employeeDTO.setPosition("BASIC");
        }

        Employee createdEmployee = employeeMapper.toEmployee(employeeDTO);

        Optional<Department> byId = departmentRepository.findById(employeeDTO.getDepartment().getId());
        Department department = byId.orElseThrow(() -> new DepartmentNotFoundException(
                "Could not find department with id: " + employeeDTO.getDepartment().getId()));
        createdEmployee.setDepartment(department);

        Employee savedEmployee = employeeRepository.save(createdEmployee);
        return employeeMapper.toEmployeeDTO(savedEmployee);

    }
}
