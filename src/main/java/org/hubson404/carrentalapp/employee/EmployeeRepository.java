package org.hubson404.carrentalapp.employee;

import org.hubson404.carrentalapp.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findEmployeeByDepartment_Id(Long departmentId);
}
