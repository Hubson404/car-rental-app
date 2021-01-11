package org.hubson404.carrentalapp.department;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.Department;
import org.hubson404.carrentalapp.exceptions.DepartmentNotFoundException;
import org.hubson404.carrentalapp.model.DepartmentDto;
import org.hubson404.carrentalapp.model.mappers.DepartmentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public List<DepartmentDto> findAll() {
        List<Department> allDepartments = departmentRepository.findAll();
        return allDepartments.stream()
                .map(departmentMapper::toDepartmentDto)
                .collect(Collectors.toList());
    }

    public DepartmentDto createDepartment(DepartmentDto departmentDTO) {
        Department createdDepartment = departmentRepository.save(departmentMapper.toDepartment(departmentDTO));
        return departmentMapper.toDepartmentDto(createdDepartment);
    }

    public DepartmentDto findDepartmentById(Long id) {
        Department foundDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));
        return departmentMapper.toDepartmentDto(foundDepartment);
    }

    public DepartmentDto deleteDepartment(Long id) {
        DepartmentDto departmentById = findDepartmentById(id);
        departmentRepository.deleteById(id);
        return departmentById;
    }

}
