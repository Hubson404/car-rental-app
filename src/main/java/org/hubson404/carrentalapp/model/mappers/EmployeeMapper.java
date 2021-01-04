package org.hubson404.carrentalapp.model.mappers;

import org.hubson404.carrentalapp.domain.Employee;
import org.hubson404.carrentalapp.model.EmployeeDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(uses = {DepartmentMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface EmployeeMapper {

    Employee toEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO toEmployeeDTO(Employee employee);
}