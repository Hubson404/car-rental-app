package org.hubson404.carrentalapp.department;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.Department;
import org.hubson404.carrentalapp.exceptions.DepartmentNotFoundException;
import org.hubson404.carrentalapp.exceptions.InsufficientDataException;
import org.hubson404.carrentalapp.model.DepartmentDTO;
import org.hubson404.carrentalapp.model.mappers.DepartmentMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public List<DepartmentDTO> findAll() {
        List<Department> allDepartments = departmentRepository.findAll();
        return allDepartments.stream()
                .map(departmentMapper::toDepartmentDTO)
                .collect(Collectors.toList());
    }

    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        if (departmentDTO.getAddress() == null || departmentDTO.getAddress().isBlank()) {
            throw new InsufficientDataException("Department address must be specified.");
        }
        Department createdDepartment = departmentRepository.save(departmentMapper.toDepartment(departmentDTO));
        return departmentMapper.toDepartmentDTO(createdDepartment);
    }

    public DepartmentDTO findDepartmentById(Long id) {
        Department foundDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException("Could not find department with id: " + id));
        return departmentMapper.toDepartmentDTO(foundDepartment);
    }

    public DepartmentDTO deleteDepartment(Long id) {
        DepartmentDTO departmentById = findDepartmentById(id);
        departmentRepository.deleteById(id);
        return departmentById;
    }

}
