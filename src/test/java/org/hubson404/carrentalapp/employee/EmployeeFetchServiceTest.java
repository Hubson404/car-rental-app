package org.hubson404.carrentalapp.employee;

import org.hubson404.carrentalapp.domain.Department;
import org.hubson404.carrentalapp.domain.Employee;
import org.hubson404.carrentalapp.domain.enums.EmployeePosition;
import org.hubson404.carrentalapp.exceptions.EmployeeNotFoundException;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeFetchServiceTest {

    @Mock
    EmployeeRepository employeeRepository;
    @InjectMocks
    EmployeeFetchService employeeFetchService;

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
    void findEmployeeById_callsEmployeeRepository() {
        // given
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(new Employee()));
        // when
        Employee result = employeeFetchService.findEmployeeById(anyLong());
        // then
        assertThat(result).isInstanceOf(Employee.class);
        verify(employeeRepository, times(1)).findById(anyLong());
    }

    @Test
    void findEmployeeById_EmployeeByGivenIdDoesNotExist_callsEmployeeRepositoryAndThrowsException() {
        // given
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
        // when
        Throwable result = catchThrowable(() -> employeeFetchService.findEmployeeById(anyLong()));
        // then
        assertThat(result).isExactlyInstanceOf(EmployeeNotFoundException.class);
        verify(employeeRepository, times(1)).findById(anyLong());
    }

    @Test
    void findEmployeeByDepartmentId_CallsEmployeeRepositoryAndReturnsListOfEmployees() {
        // given
        when(employeeRepository.findEmployeeByDepartment_Id(anyLong())).thenReturn(List.of(new Employee()));
        // when
        List<Employee> result = employeeFetchService.findEmployeeByDepartmentId(anyLong());
        // then
        assertThat(result.get(0)).isInstanceOf(Employee.class);
        verify(employeeRepository, times(1)).findEmployeeByDepartment_Id(anyLong());
    }

    @Test
    void findManagersInDepartmentById_CallsEmployeeRepositoryAndReturnsListOfManagers() {
        // given
        when(employeeRepository.findEmployeesByDepartmentIdAndPosition(anyLong(), any()))
                .thenReturn(List.of(new Employee(
                        1L, "name", "name", EmployeePosition.MANAGER, new Department())));
        // when
        List<Employee> result = employeeFetchService.findManagersInDepartmentById(1L);
        // then
        assertThat(result.get(0).getPosition()).isEqualTo(EmployeePosition.MANAGER);
        verify(employeeRepository, times(1))
                .findEmployeesByDepartmentIdAndPosition(anyLong(), any());
    }
}
