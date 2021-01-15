package org.hubson404.carrentalapp.controllers;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.model.EmployeeDto;
import org.hubson404.carrentalapp.services.EmployeeService;
import org.hubson404.carrentalapp.wrappers.EmployeeWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDto createEmployee(@Valid @RequestBody EmployeeDto employeeDTO) {
        return employeeService.createEmployee(employeeDTO);
    }

    @GetMapping("/employees")
    public EmployeeWrapper findAll() {
        List<EmployeeDto> all = employeeService.findAll();
        EmployeeWrapper employeeWrapper = new EmployeeWrapper();
        employeeWrapper.setEmployees(all);
        return employeeWrapper;
    }

    @GetMapping("/employees/{id}")
    public EmployeeDto findEmployeeById(@PathVariable Long id) {
        return employeeService.findEmployeeById(id);
    }

    @GetMapping("/employees/managers/{departmentId}")
    public List<EmployeeDto> findManagersInDepartmentById(@PathVariable Long departmentId) {
        return employeeService.findManagersInDepartmentById(departmentId);
    }

    @GetMapping("/employees/department/{id}")
    public List<EmployeeDto> findEmployeesByDepartmentId(@PathVariable Long id) {
        return employeeService.findEmployeeByDepartmentId(id);
    }

    @PatchMapping("/employees/{id}/promote")
    public EmployeeDto promoteEmployeeById(@PathVariable Long id) {
        return employeeService.promoteEmployee(id);
    }

    @PatchMapping("/employees/{id}/demote")
    public EmployeeDto demoteEmployeeById(@PathVariable Long id) {
        return employeeService.demoteEmployee(id);
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployeeById(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
    }
}
