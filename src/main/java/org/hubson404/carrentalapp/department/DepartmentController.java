package org.hubson404.carrentalapp.department;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hubson404.carrentalapp.model.DepartmentDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/departments")
    public DepartmentCollection findAll() {
        List<DepartmentDTO> all = departmentService.findAll();
        DepartmentCollection departmentCollection = new DepartmentCollection();
        departmentCollection.setDepartments(all);
        return departmentCollection;
    }

    @GetMapping("/departments/{id}")
    public DepartmentDTO findDepartmentById(@PathVariable Long id) {
        return departmentService.findDepartmentById(id);
    }

    @PostMapping("/departments")
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentDTO createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        return departmentService.createDepartment(departmentDTO);
    }

    @DeleteMapping("/departments/{id}")
    public DepartmentDTO deleteDepartment(@PathVariable Long id) {
        return departmentService.deleteDepartment(id);
    }
}
