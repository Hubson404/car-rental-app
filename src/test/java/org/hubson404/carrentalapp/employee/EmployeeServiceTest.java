package org.hubson404.carrentalapp.employee;

import org.hubson404.carrentalapp.domain.Department;
import org.hubson404.carrentalapp.domain.Employee;
import org.hubson404.carrentalapp.domain.enums.EmployeePosition;
import org.hubson404.carrentalapp.exceptions.EmployeeNotFoundException;
import org.hubson404.carrentalapp.exceptions.IllegalEmployeeIdException;
import org.hubson404.carrentalapp.exceptions.InsufficientDataException;
import org.hubson404.carrentalapp.model.DepartmentDto;
import org.hubson404.carrentalapp.model.EmployeeDto;
import org.hubson404.carrentalapp.model.mappers.EmployeeMapper;
import org.hubson404.carrentalapp.repositories.DepartmentRepository;
import org.hubson404.carrentalapp.repositories.EmployeeRepository;
import org.hubson404.carrentalapp.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    @Mock
    EmployeeMapper employeeMapper;
    @InjectMocks
    EmployeeService employeeService;

    @Test
    void createEmployee_callsEmployeeRepository() {
        // given
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(new Department()));
        when(employeeRepository.save(any(Employee.class))).thenReturn(new Employee());
        when(employeeMapper.toEmployee(any())).thenReturn(new Employee());
        // when
        employeeService.createEmployee(
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
        Throwable result = catchThrowable(() -> employeeService.createEmployee(
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
        Throwable result = catchThrowable(() -> employeeService.createEmployee(
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
        Throwable result = catchThrowable(() -> employeeService.createEmployee(
                EmployeeDto.builder()
                        .firstName("testFirstName")
                        .lastName("testLastName")
                        .build()));
        // then
        assertThat(result).isExactlyInstanceOf(InsufficientDataException.class);
        verify(employeeRepository, times(0)).save(any(Employee.class));
    }

    @Test
    void findAll_callsEmployeeRepository() {
        // given
        when(employeeRepository.findAll()).thenReturn(List.of(new Employee(), new Employee()));
        when(employeeMapper.toEmployeeDto(any(Employee.class))).thenReturn(new EmployeeDto());
        // when
        employeeService.findAll();
        // then
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void findEmployeeById_callsEmployeeRepository() {
        // given
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(new Employee()));
        when(employeeMapper.toEmployeeDto(any(Employee.class))).thenReturn(new EmployeeDto());
        // when
        employeeService.findEmployeeById(anyLong());
        // then
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
    void findEmployeeByDepartmentId_CallsEmployeeRepositoryAndReturnsListOfEmployees() {
        // given
        when(employeeRepository.findEmployeeByDepartment_Id(anyLong())).thenReturn(List.of(new Employee()));
        when(employeeMapper.toEmployeeDto(any(Employee.class))).thenReturn(new EmployeeDto());
        // when
        employeeService.findEmployeeByDepartmentId(anyLong());
        // then
        verify(employeeRepository, times(1)).findEmployeeByDepartment_Id(anyLong());
    }

    @Test
    void findManagersInDepartmentById_CallsEmployeeRepositoryAndReturnsListOfManagers() {
        // given
        when(employeeRepository.findEmployeesByDepartmentIdAndPosition(anyLong(), any()))
                .thenReturn(List.of(new Employee(
                        1L, "name", "name", EmployeePosition.MANAGER, new Department())));
        when(employeeMapper.toEmployeeDto(any(Employee.class))).thenReturn(new EmployeeDto());
        // when
        employeeService.findManagersInDepartmentById(1L);
        // then
        verify(employeeRepository, times(1))
                .findEmployeesByDepartmentIdAndPosition(anyLong(), any());
    }

    @Test
    void deleteEmployeeById_callsEmployeeRepository() {
        // given
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(new Employee(
                1L, "name", "name", null, null)));
        // when
        employeeService.deleteEmployeeById(1L);
        // then
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).deleteById(1L);
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

    @Test
    void promoteEmployeeById_callsEmployeeRepositoryAndUpdatesEmployeePosition() {
        //given
        when(employeeRepository.findEmployeeByIdAndPosition(anyLong(), any())).thenReturn(Optional.of(new Employee()));
        when(employeeMapper.toEmployeeDto(any(Employee.class))).thenReturn(new EmployeeDto(
                1L, "name", "lastname", "MANAGER", new DepartmentDto()));        //when
        EmployeeDto employee = employeeService.promoteEmployee(1L);
        //then
        assertThat(employee.getPosition()).isEqualTo(EmployeePosition.MANAGER.toString());
        verify(employeeRepository, times(1)).findEmployeeByIdAndPosition(anyLong(), any());
    }

    @Test
    void promoteEmployeeById_EmployeeByGivenIdDoesNotExistOrAlreadyPromoted_callsEmployeeRepositoryAndThrowsException() {
        //given
        when(employeeRepository.findEmployeeByIdAndPosition(anyLong(), any())).thenReturn(Optional.empty());
        //when
        Throwable result = catchThrowable(() -> employeeService.promoteEmployee(1L));
        //then
        assertThat(result).isExactlyInstanceOf(IllegalEmployeeIdException.class);
        verify(employeeRepository, times(1)).findEmployeeByIdAndPosition(anyLong(), any());
    }

    @Test
    void demoteEmployeeById_callsEmployeeRepositoryAndUpdatesEmployeePosition() {
        //given
        when(employeeRepository.findEmployeeByIdAndPosition(anyLong(), any())).thenReturn(Optional.of(new Employee()));
        when(employeeMapper.toEmployeeDto(any(Employee.class))).thenReturn(new EmployeeDto(
                1L, "name", "lastname", "BASIC", new DepartmentDto()));
        //when
        EmployeeDto employeeDTO = employeeService.demoteEmployee(1L);
        //then
        assertThat(employeeDTO.getPosition()).isEqualTo(EmployeePosition.BASIC.toString());
        verify(employeeRepository, times(1)).findEmployeeByIdAndPosition(anyLong(), any());
    }

    @Test
    void demoteEmployeeById_EmployeeByGivenIdDoesNotExistOrAlreadyDemoted_callsEmployeeRepositoryAndThrowsException() {
        //given
        when(employeeRepository.findEmployeeByIdAndPosition(anyLong(), any())).thenReturn(Optional.empty());
        //when
        Throwable result = catchThrowable(() -> employeeService.demoteEmployee(1L));
        //then
        assertThat(result).isExactlyInstanceOf(IllegalEmployeeIdException.class);
        verify(employeeRepository, times(1)).findEmployeeByIdAndPosition(anyLong(), any());
    }

}
