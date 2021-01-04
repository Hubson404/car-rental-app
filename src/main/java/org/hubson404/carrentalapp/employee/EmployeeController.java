package org.hubson404.carrentalapp.employee;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.Employee;
import org.hubson404.carrentalapp.model.EmployeeDTO;
import org.hubson404.carrentalapp.model.mappers.EmployeeMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    @GetMapping("/employees")
    public List<EmployeeDTO> findAll() {
        List<Employee> employees = employeeService.findAll();
        return employees.stream().map(employeeMapper::toEmployeeDTO).collect(Collectors.toList());
    }

    @GetMapping("/employees/{id}")
    public EmployeeDTO findEmployeeById(@PathVariable Long id) {
        return employeeMapper.toEmployeeDTO(employeeService.findEmployeeById(id));
    }

    @GetMapping("/employees/department/{id}")
    public List<EmployeeDTO> findEmployeesByDepartmentId(@PathVariable Long id) {
        List<Employee> employees = employeeService.findEmployeeByDepartmentId(id);
        return employees.stream().map(employeeMapper::toEmployeeDTO).collect(Collectors.toList());
    }

    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDTO createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeService.createEmployee(employeeMapper.toEmployee(employeeDTO));
        return employeeMapper.toEmployeeDTO(employee);
    }

    @DeleteMapping("/employees/{id}")
    public EmployeeDTO deleteEmployeeById(@PathVariable Long id) {
        return employeeMapper.toEmployeeDTO(employeeService.deleteEmployeeById(id));
    }


}
