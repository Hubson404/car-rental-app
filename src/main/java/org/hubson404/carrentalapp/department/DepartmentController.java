package org.hubson404.carrentalapp.department;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hubson404.carrentalapp.model.DepartmentDTO;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentDTO createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        return departmentService.createDepartment(departmentDTO);
    }

    @DeleteMapping("/departments/{id}")
    public DepartmentDTO deleteDepartment(@PathVariable Long id) {
        return departmentService.deleteDepartment(id);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeWithDepartment() {
        DepartmentDTO warsaw = createDepartment(new DepartmentDTO(null, "Warsaw"));
        log.info(warsaw.toString());
    }
}
