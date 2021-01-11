package org.hubson404.carrentalapp.department;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hubson404.carrentalapp.model.DepartmentDto;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/departments")
    public DepartmentWrapper findAll() {
        List<DepartmentDto> all = departmentService.findAll();
        DepartmentWrapper departmentWrapper = new DepartmentWrapper();
        departmentWrapper.setDepartments(all);
        return departmentWrapper;
    }

    @GetMapping("/departments/{id}")
    public DepartmentDto findDepartmentById(@PathVariable Long id) {
        return departmentService.findDepartmentById(id);
    }

    @PostMapping("/departments")
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentDto createDepartment(@Valid @RequestBody DepartmentDto departmentDTO) {
        return departmentService.createDepartment(departmentDTO);
    }

    @DeleteMapping("/departments/{id}")
    public DepartmentDto deleteDepartment(@PathVariable Long id) {
        return departmentService.deleteDepartment(id);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeWithDepartment() {
        DepartmentDto warsaw = createDepartment(new DepartmentDto(null, "Warsaw"));
        log.info(warsaw.toString());
    }
}
