package org.hubson404.carrentalapp.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hubson404.carrentalapp.domain.Department;
import org.hubson404.carrentalapp.domain.Employee;
import org.hubson404.carrentalapp.domain.enums.EmployeePosition;
import org.hubson404.carrentalapp.exceptions.DepartmentNotFoundException;
import org.hubson404.carrentalapp.exceptions.EmployeeNotFoundException;
import org.hubson404.carrentalapp.exceptions.IllegalEmployeeIdException;
import org.hubson404.carrentalapp.model.EmployeeDto;
import org.hubson404.carrentalapp.model.mappers.EmployeeMapper;
import org.hubson404.carrentalapp.repositories.DepartmentRepository;
import org.hubson404.carrentalapp.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeService {

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

    public List<EmployeeDto> findAll() {
        List<Employee> all = employeeRepository.findAll();
        return all.stream().map(employeeMapper::toEmployeeDto).collect(Collectors.toList());
    }

    public EmployeeDto findEmployeeById(Long id) {
        Optional<Employee> byId = employeeRepository.findById(id);
        Employee employee = byId.orElseThrow(
                () -> new EmployeeNotFoundException(id));
        return employeeMapper.toEmployeeDto(employee);
    }

    public List<EmployeeDto> findEmployeeByDepartmentId(Long departmentId) {
        List<Employee> employeeByDepartment_id = employeeRepository.findEmployeeByDepartment_Id(departmentId);
        return employeeByDepartment_id.stream().map(employeeMapper::toEmployeeDto).collect(Collectors.toList());
    }

    public List<EmployeeDto> findManagersInDepartmentById(Long departmentId) {
        List<Employee> employeesByDepartmentIdAndPosition = employeeRepository.findEmployeesByDepartmentIdAndPosition(departmentId, EmployeePosition.MANAGER);
        return employeesByDepartmentIdAndPosition.stream().map(employeeMapper::toEmployeeDto).collect(Collectors.toList());
    }

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
