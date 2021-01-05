package org.hubson404.carrentalapp.employee;

import org.hubson404.carrentalapp.domain.Department;
import org.hubson404.carrentalapp.domain.Employee;
import org.hubson404.carrentalapp.domain.enums.EmployeePosition;
import org.hubson404.carrentalapp.exceptions.EmployeeNotFoundException;
import org.hubson404.carrentalapp.exceptions.IllegalEmployeeIdException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeModifyServiceTest {

    @Mock
    EmployeeRepository employeeRepository;
    @InjectMocks
    EmployeeModifyService employeeModifyService;

    @Test
    void deleteEmployeeById_callsEmployeeRepository() {
        // given
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(new Employee(
                1L, "name", "name", null, null)));
        // when
        employeeModifyService.deleteEmployeeById(1L);
        // then
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteEmployeeById_EmployeeByGivenIdDoesNotExist_callsEmployeeRepositoryAndThrowsException() {
        // given
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
        // when
        Throwable result = catchThrowable(() -> employeeModifyService.deleteEmployeeById(anyLong()));
        // then
        assertThat(result).isExactlyInstanceOf(EmployeeNotFoundException.class);
        verify(employeeRepository, times(1)).findById(anyLong());
        verify(employeeRepository, times(0)).deleteById(anyLong());
    }

    @Test
    void promoteEmployeeById_callsEmployeeRepositoryAndUpdatesEmployeePosition() {
        //given
        when(employeeRepository.findEmployeeByIdAndPosition(anyLong(), any())).thenReturn(Optional.of(
                new Employee(1L, "name", "lastname", EmployeePosition.BASIC, new Department())));
        //when
        Employee employee = employeeModifyService.promoteEmployee(1L);
        //then
        assertThat(employee.getPosition()).isEqualTo(EmployeePosition.MANAGER);
        verify(employeeRepository, times(1)).findEmployeeByIdAndPosition(anyLong(), any());
    }

    @Test
    void promoteEmployeeById_EmployeeByGivenIdDoesNotExistOrAlreadyPromoted_callsEmployeeRepositoryAndThrowsException() {
        //given
        when(employeeRepository.findEmployeeByIdAndPosition(anyLong(), any())).thenReturn(Optional.empty());
        //when
        Throwable result = catchThrowable(() -> employeeModifyService.promoteEmployee(1L));
        //then
        assertThat(result).isExactlyInstanceOf(IllegalEmployeeIdException.class);
        verify(employeeRepository, times(1)).findEmployeeByIdAndPosition(anyLong(), any());
    }

    @Test
    void demoteEmployeeById_callsEmployeeRepositoryAndUpdatesEmployeePosition() {
        //given
        when(employeeRepository.findEmployeeByIdAndPosition(anyLong(), any())).thenReturn(Optional.of(
                new Employee(1L, "name", "lastname", EmployeePosition.MANAGER, new Department())));
        //when
        Employee employee = employeeModifyService.demoteEmployee(1L);
        //then
        assertThat(employee.getPosition()).isEqualTo(EmployeePosition.BASIC);
        verify(employeeRepository, times(1)).findEmployeeByIdAndPosition(anyLong(), any());
    }

    @Test
    void demoteEmployeeById_EmployeeByGivenIdDoesNotExistOrAlreadyDemoted_callsEmployeeRepositoryAndThrowsException() {
        //given
        when(employeeRepository.findEmployeeByIdAndPosition(anyLong(), any())).thenReturn(Optional.empty());
        //when
        Throwable result = catchThrowable(() -> employeeModifyService.demoteEmployee(1L));
        //then
        assertThat(result).isExactlyInstanceOf(IllegalEmployeeIdException.class);
        verify(employeeRepository, times(1)).findEmployeeByIdAndPosition(anyLong(), any());
    }

}
