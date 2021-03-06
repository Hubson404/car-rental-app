package org.hubson404.carrentalapp.model.mappers;

import org.hubson404.carrentalapp.domain.Department;
import org.hubson404.carrentalapp.model.DepartmentDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {CarMapper.class, EmployeeMapper.class, CarRentalCompanyMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DepartmentMapper {

    @Mapping(target = "employees", ignore = true)
    @Mapping(target = "cars", ignore = true)
    Department toDepartment(DepartmentDto departmentDto);

    DepartmentDto toDepartmentDto(Department department);

}
