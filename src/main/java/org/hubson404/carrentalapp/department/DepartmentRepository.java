package org.hubson404.carrentalapp.department;

import org.hubson404.carrentalapp.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
