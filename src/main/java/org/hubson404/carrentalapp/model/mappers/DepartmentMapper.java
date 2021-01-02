package org.hubson404.carrentalapp.model.mappers;

import org.hubson404.carrentalapp.domain.Department;
import org.hubson404.carrentalapp.model.DepartmentDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(uses = {CarMapper.class, EmployeeMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DepartmentMapper {

    Department toDepartment(DepartmentDTO departmentDTO);

    DepartmentDTO toDepartmentDTO(Department department);
}
