package org.hubson404.carrentalapp.employee;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.Employee;
import org.hubson404.carrentalapp.domain.enums.EmployeePosition;
import org.hubson404.carrentalapp.exceptions.EmployeeNotFoundException;
import org.hubson404.carrentalapp.model.EmployeeDto;
import org.hubson404.carrentalapp.model.mappers.EmployeeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeFetchService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

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


}
