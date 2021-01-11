package org.hubson404.carrentalapp.employee;

import org.hubson404.carrentalapp.department.DepartmentRepository;
import org.hubson404.carrentalapp.domain.Department;
import org.hubson404.carrentalapp.domain.Employee;
import org.hubson404.carrentalapp.exceptions.InsufficientDataException;
import org.hubson404.carrentalapp.model.DepartmentDto;
import org.hubson404.carrentalapp.model.EmployeeDto;
import org.hubson404.carrentalapp.model.mappers.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeCreateServiceTest {

    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    DepartmentRepository departmentRepository;
    @Mock
    EmployeeMapper employeeMapper;
    @InjectMocks
    EmployeeCreateService employeeCreateService;

    @Test
    void createEmployee_callsEmployeeRepository() {
        // given
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(new Department()));
        when(employeeRepository.save(any(Employee.class))).thenReturn(new Employee());
        when(employeeMapper.toEmployee(any())).thenReturn(new Employee());
        // when
        employeeCreateService.createEmployee(
                EmployeeDto.builder()
                        .firstName("testFirstName")
                        .lastName("testLastName")
                        .department(DepartmentDto.builder().id(1L).address("testAddress").build())
                        .build());
        // then
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void createEmployee_whenFirstNameFieldIsBlank_ThrowsExceptionAndDoesNotCallEmployeeRepository() {
        // given
        Department department = new Department();
        department.setId(1L);
        department.setAddress("Warsaw");
        // when
        Throwable result = catchThrowable(() -> employeeCreateService.createEmployee(
                EmployeeDto.builder()
                        .firstName("  ")
                        .lastName("testLastName")
                        .department(DepartmentDto.builder().id(1L).address("testAddress").build())
                        .build()));
        // then
        assertThat(result).isExactlyInstanceOf(InsufficientDataException.class);
        verify(employeeRepository, times(0)).save(any(Employee.class));
    }

    @Test
    void createEmployee_whenLastNameFieldIsBlank_ThrowsExceptionAndDoesNotCallEmployeeRepository() {
        // given
        Department department = new Department();
        department.setId(1L);
        department.setAddress("Warsaw");
        // when
        Throwable result = catchThrowable(() -> employeeCreateService.createEmployee(
                EmployeeDto.builder()
                        .firstName("testFirstName")
                        .lastName("  ")
                        .department(DepartmentDto.builder().id(1L).address("testAddress").build())
                        .build()));
        // then
        assertThat(result).isExactlyInstanceOf(InsufficientDataException.class);
        verify(employeeRepository, times(0)).save(any(Employee.class));
    }

    @Test
    void createEmployee_whenDepartmentFieldIsNull_ThrowsExceptionAndDoesNotCallEmployeeRepository() {
        // given

        // when
        Throwable result = catchThrowable(() -> employeeCreateService.createEmployee(
                EmployeeDto.builder()
                        .firstName("testFirstName")
                        .lastName("testLastName")
                        .build()));
        // then
        assertThat(result).isExactlyInstanceOf(InsufficientDataException.class);
        verify(employeeRepository, times(0)).save(any(Employee.class));
    }

}
