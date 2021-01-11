package org.hubson404.carrentalapp.employee;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hubson404.carrentalapp.department.DepartmentRepository;
import org.hubson404.carrentalapp.domain.Department;
import org.hubson404.carrentalapp.domain.Employee;
import org.hubson404.carrentalapp.exceptions.DepartmentNotFoundException;
import org.hubson404.carrentalapp.model.EmployeeDto;
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

    public EmployeeDto createEmployee(EmployeeDto employeeDTO) {

        checkPosition(employeeDTO);
        Employee createdEmployee = getDepartmentFromRepository(employeeDTO);

        Employee savedEmployee = employeeRepository.save(createdEmployee);
        return employeeMapper.toEmployeeDto(savedEmployee);
    }

    private void checkPosition(EmployeeDto employeeDTO) {
        if (employeeDTO.getPosition() == null) {
            log.info("Position was not specified, setting position to 'BASIC'");
            employeeDTO.setPosition("BASIC");
        }
    }

    private Employee getDepartmentFromRepository(EmployeeDto employeeDTO) {

        Employee createdEmployee = employeeMapper.toEmployee(employeeDTO);
        Optional<Department> byId = departmentRepository.findById(employeeDTO.getDepartment().getId());

        byId.ifPresentOrElse(createdEmployee::setDepartment,
                () -> {
                    throw new DepartmentNotFoundException(employeeDTO.getDepartment().getId());
                });
        return createdEmployee;
    }
}
