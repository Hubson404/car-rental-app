package org.hubson404.carrentalapp.employee;

import org.hubson404.carrentalapp.department.DepartmentRepository;
import org.hubson404.carrentalapp.domain.Department;
import org.hubson404.carrentalapp.domain.Employee;
import org.hubson404.carrentalapp.exceptions.EmployeeNotFoundException;
import org.hubson404.carrentalapp.exceptions.InsufficientDataException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    DepartmentRepository departmentRepository;
    @InjectMocks
    EmployeeService employeeService;

    @Test
    void findAll_callsEmployeeRepository() {
        // given
        when(employeeRepository.findAll()).thenReturn(new ArrayList<>());
        // when
        List<Employee> result = employeeRepository.findAll();
        // then
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void createEmployee_callsEmployeeRepository() {
        // given
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(new Department()));
        when(employeeRepository.save(any(Employee.class))).thenReturn(new Employee());

        Department department = new Department();
        department.setId(1L);
        department.setAddress("Warsaw");
        // when
        Employee result = employeeService.createEmployee(
                new Employee(null, "first name", "last name",
                        null, department));
        // then
        assertThat(result).isInstanceOf(Employee.class);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void createDepartment_whenFirstNameFieldIsBlank_ThrowsExceptionAndDoesNotCallDepartmentRepository() {
        // given
        Department department = new Department();
        department.setId(1L);
        department.setAddress("Warsaw");
        // when
        Throwable result = catchThrowable(() -> employeeService.createEmployee(
                new Employee(null, "  ", "last name",
                        null, department)));
        // then
        assertThat(result).isExactlyInstanceOf(InsufficientDataException.class);
        verify(departmentRepository, times(0)).save(any(Department.class));
    }

    @Test
    void createDepartment_whenLastNameFieldIsBlank_ThrowsExceptionAndDoesNotCallDepartmentRepository() {
        // given
        Department department = new Department();
        department.setId(1L);
        department.setAddress("Warsaw");
        // when
        Throwable result = catchThrowable(() -> employeeService.createEmployee(
                new Employee(null, "first name", "  ",
                        null, department)));
        // then
        assertThat(result).isExactlyInstanceOf(InsufficientDataException.class);
        verify(departmentRepository, times(0)).save(any(Department.class));
    }

    @Test
    void createDepartment_whenDepartmentFieldIsBlank_ThrowsExceptionAndDoesNotCallDepartmentRepository() {
        // given

        // when
        Throwable result = catchThrowable(() -> employeeService.createEmployee(
                new Employee(null, "first name", "last name",
                        null, null)));
        // then
        assertThat(result).isExactlyInstanceOf(InsufficientDataException.class);
        verify(departmentRepository, times(0)).save(any(Department.class));
    }

    @Test
    void findEmployeeById_callsEmployeeRepository() {
        // given
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(new Employee()));
        // when
        Employee result = employeeService.findEmployeeById(anyLong());
        // then
        assertThat(result).isInstanceOf(Employee.class);
        verify(employeeRepository, times(1)).findById(anyLong());
    }

    @Test
    void findEmployeeById_EmployeeByGivenIdDoesNotExist_callsEmployeeRepositoryAndThrowsException() {
        // given
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
        // when
        Throwable result = catchThrowable(() -> employeeService.findEmployeeById(anyLong()));
        // then
        assertThat(result).isExactlyInstanceOf(EmployeeNotFoundException.class);
        verify(employeeRepository, times(1)).findById(anyLong());
    }

    @Test
    void deleteEmployeeById_callsEmployeeRepository() {
        // given
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(new Employee()));
        // when
        Employee result = employeeService.deleteEmployeeById(anyLong());
        // then
        assertThat(result).isInstanceOf(Employee.class);
        verify(employeeRepository, times(1)).findById(anyLong());
        verify(employeeRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteEmployeeById_EmployeeByGivenIdDoesNotExist_callsEmployeeRepositoryAndThrowsException() {
        // given
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
        // when
        Throwable result = catchThrowable(() -> employeeService.deleteEmployeeById(anyLong()));
        // then
        assertThat(result).isExactlyInstanceOf(EmployeeNotFoundException.class);
        verify(employeeRepository, times(1)).findById(anyLong());
        verify(employeeRepository, times(0)).deleteById(anyLong());
    }
}
