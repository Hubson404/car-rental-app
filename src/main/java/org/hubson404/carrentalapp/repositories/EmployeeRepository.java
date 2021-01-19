package org.hubson404.carrentalapp.repositories;

import org.hubson404.carrentalapp.domain.Employee;
import org.hubson404.carrentalapp.domain.enums.EmployeePosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findEmployeeByDepartment_Id(Long departmentId);

    Optional<Employee> findEmployeeByIdAndPosition(Long id, EmployeePosition position);

    List<Employee> findEmployeesByDepartmentIdAndPosition(Long departmentId, EmployeePosition position);

    Optional<Employee> findEmployeesByEmail(String email);
}
