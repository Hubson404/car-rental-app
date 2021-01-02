package org.hubson404.carrentalapp.department;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.model.DepartmentDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/departments")
    public List<DepartmentDTO> findAll() {
        return departmentService.findAll();
    }

    @GetMapping("/departments/{id}")
    public DepartmentDTO findDepartmentById(@PathVariable Long id) {
        return departmentService.findDepartmentById(id);
    }

    @PostMapping("/departments")
    public DepartmentDTO createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        return departmentService.createDepartment(departmentDTO);
    }

    @DeleteMapping("/departments/{id}")
    public DepartmentDTO deleteDepartment(@PathVariable Long id) {
        return departmentService.deleteDepartment(id);
    }
}
