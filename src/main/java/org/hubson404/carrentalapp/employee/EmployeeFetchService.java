package org.hubson404.carrentalapp.employee;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.Employee;
import org.hubson404.carrentalapp.domain.enums.EmployeePosition;
import org.hubson404.carrentalapp.exceptions.EmployeeNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeFetchService {

    private final EmployeeRepository employeeRepository;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findEmployeeById(Long id) {
        Optional<Employee> byId = employeeRepository.findById(id);
        return byId.orElseThrow(
                () -> new EmployeeNotFoundException("Could not find department with id: " + id));
    }

    public List<Employee> findEmployeeByDepartmentId(Long departmentId) {
        return employeeRepository.findEmployeeByDepartment_Id(departmentId);
    }

    public List<Employee> findManagersInDepartmentById(Long departmentId) {
        return employeeRepository.findEmployeesByDepartmentIdAndPosition(departmentId, EmployeePosition.MANAGER);
    }


}
