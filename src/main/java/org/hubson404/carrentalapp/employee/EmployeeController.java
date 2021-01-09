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

    private final EmployeeCreateService employeeCreateService;
    private final EmployeeModifyService employeeModifyService;
    private final EmployeeFetchService employeeFetchService;
    private final EmployeeMapper employeeMapper;

    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDTO createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee createdEmployee = employeeCreateService.createEmployee(employeeDTO);
        return employeeMapper.toEmployeeDTO(createdEmployee);
    }

    @GetMapping("/employees")
    public List<EmployeeDTO> findAll() {
        List<Employee> employees = employeeFetchService.findAll();
        return toEmployeeDtoList(employees);
    }

    @GetMapping("/employees/{id}")
    public EmployeeDTO findEmployeeById(@PathVariable Long id) {
        return employeeMapper.toEmployeeDTO(employeeFetchService.findEmployeeById(id));
    }

    @GetMapping("/employees/managers/{departmentId}")
    public List<EmployeeDTO> findManagersInDepartmentById(@PathVariable Long departmentId) {
        List<Employee> managers = employeeFetchService.findManagersInDepartmentById(departmentId);
        return toEmployeeDtoList(managers);
    }

    @GetMapping("/employees/department/{id}")
    public List<EmployeeDTO> findEmployeesByDepartmentId(@PathVariable Long id) {
        List<Employee> employees = employeeFetchService.findEmployeeByDepartmentId(id);
        return employees.stream().map(employeeMapper::toEmployeeDTO).collect(Collectors.toList());
    }

    @PatchMapping("/employees/{id}/promote")
    public EmployeeDTO promoteEmployeeById(@PathVariable Long id) {
        Employee promotedEmployee = employeeModifyService.promoteEmployee(id);
        return employeeMapper.toEmployeeDTO(promotedEmployee);
    }

    @PatchMapping("/employees/{id}/demote")
    public EmployeeDTO demoteEmployeeById(@PathVariable Long id) {
        Employee promotedEmployee = employeeModifyService.demoteEmployee(id);
        return employeeMapper.toEmployeeDTO(promotedEmployee);
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployeeById(@PathVariable Long id) {
        employeeModifyService.deleteEmployeeById(id);
    }

    private List<EmployeeDTO> toEmployeeDtoList(List<Employee> employees) {
        return employees.stream().map(employeeMapper::toEmployeeDTO).collect(Collectors.toList());
    }

}
