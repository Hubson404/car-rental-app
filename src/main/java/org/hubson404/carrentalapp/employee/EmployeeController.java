package org.hubson404.carrentalapp.employee;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.model.EmployeeDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeCreateService employeeCreateService;
    private final EmployeeModifyService employeeModifyService;
    private final EmployeeFetchService employeeFetchService;

    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDTO createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeCreateService.createEmployee(employeeDTO);
    }

    @GetMapping("/employees")
    public List<EmployeeDTO> findAll() {
        return employeeFetchService.findAll();
    }

    @GetMapping("/employees/{id}")
    public EmployeeDTO findEmployeeById(@PathVariable Long id) {
        return employeeFetchService.findEmployeeById(id);
    }

    @GetMapping("/employees/managers/{departmentId}")
    public List<EmployeeDTO> findManagersInDepartmentById(@PathVariable Long departmentId) {
        return employeeFetchService.findManagersInDepartmentById(departmentId);
    }

    @GetMapping("/employees/department/{id}")
    public List<EmployeeDTO> findEmployeesByDepartmentId(@PathVariable Long id) {
        return employeeFetchService.findEmployeeByDepartmentId(id);
    }

    @PatchMapping("/employees/{id}/promote")
    public EmployeeDTO promoteEmployeeById(@PathVariable Long id) {
        return employeeModifyService.promoteEmployee(id);
    }

    @PatchMapping("/employees/{id}/demote")
    public EmployeeDTO demoteEmployeeById(@PathVariable Long id) {
        return employeeModifyService.demoteEmployee(id);
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployeeById(@PathVariable Long id) {
        employeeModifyService.deleteEmployeeById(id);
    }
}
