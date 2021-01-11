package org.hubson404.carrentalapp.employee;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.model.EmployeeDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeCreateService employeeCreateService;
    private final EmployeeModifyService employeeModifyService;
    private final EmployeeFetchService employeeFetchService;

    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDto createEmployee(@Valid @RequestBody EmployeeDto employeeDTO) {
        return employeeCreateService.createEmployee(employeeDTO);
    }

    @GetMapping("/employees")
    public EmployeeWrapper findAll() {
        List<EmployeeDto> all = employeeFetchService.findAll();
        EmployeeWrapper employeeWrapper = new EmployeeWrapper();
        employeeWrapper.setEmployees(all);
        return employeeWrapper;
    }

    @GetMapping("/employees/{id}")
    public EmployeeDto findEmployeeById(@PathVariable Long id) {
        return employeeFetchService.findEmployeeById(id);
    }

    @GetMapping("/employees/managers/{departmentId}")
    public List<EmployeeDto> findManagersInDepartmentById(@PathVariable Long departmentId) {
        return employeeFetchService.findManagersInDepartmentById(departmentId);
    }

    @GetMapping("/employees/department/{id}")
    public List<EmployeeDto> findEmployeesByDepartmentId(@PathVariable Long id) {
        return employeeFetchService.findEmployeeByDepartmentId(id);
    }

    @PatchMapping("/employees/{id}/promote")
    public EmployeeDto promoteEmployeeById(@PathVariable Long id) {
        return employeeModifyService.promoteEmployee(id);
    }

    @PatchMapping("/employees/{id}/demote")
    public EmployeeDto demoteEmployeeById(@PathVariable Long id) {
        return employeeModifyService.demoteEmployee(id);
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployeeById(@PathVariable Long id) {
        employeeModifyService.deleteEmployeeById(id);
    }
}
