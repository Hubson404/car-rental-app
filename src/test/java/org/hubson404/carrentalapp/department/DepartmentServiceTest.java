package org.hubson404.carrentalapp.department;

import org.hubson404.carrentalapp.domain.Department;
import org.hubson404.carrentalapp.exceptions.DepartmentNotFoundException;
import org.hubson404.carrentalapp.exceptions.InsufficientDataException;
import org.hubson404.carrentalapp.model.DepartmentDto;
import org.hubson404.carrentalapp.model.mappers.DepartmentMapper;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    DepartmentRepository departmentRepository;
    @Mock
    DepartmentMapper departmentMapper;
    @InjectMocks
    DepartmentService departmentService;

    @Test
    void findAll_callsDepartmentRepository() {
        // given
        when(departmentRepository.findAll()).thenReturn(new ArrayList<>());
        // when
        List<DepartmentDto> result = departmentService.findAll();
        // then
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    void createDepartment_callsDepartmentRepository() {
        // given
        when(departmentRepository.save(any(Department.class))).thenReturn(new Department());
        when(departmentMapper.toDepartment(any(DepartmentDto.class))).thenReturn(new Department());
        when(departmentMapper.toDepartmentDto(any(Department.class))).thenReturn(new DepartmentDto());
        // when
        DepartmentDto result = departmentService.createDepartment(
                new DepartmentDto(null, "some address"));
        // then
        assertThat(result).isInstanceOf(DepartmentDto.class);
        verify(departmentRepository, times(1)).save(any(Department.class));
    }

    @Test
    void createDepartment_whenAddressFieldIsBlank_ThrowsExceptionAndDoesNotCallDepartmentRepository() {
        // given
        // when
        Throwable result = catchThrowable(() -> departmentService.createDepartment(
                new DepartmentDto(null, " ")));
        // then
        assertThat(result).isExactlyInstanceOf(InsufficientDataException.class);
        verify(departmentRepository, times(0)).save(any(Department.class));
    }

    @Test
    void createDepartment_whenAddressFieldIsNull_ThrowsExceptionAndDoesNotCallDepartmentRepository() {
        // given
        // when
        Throwable result = catchThrowable(() -> departmentService.createDepartment(
                new DepartmentDto(null, null)));
        // then
        assertThat(result).isExactlyInstanceOf(InsufficientDataException.class);
        verify(departmentRepository, times(0)).save(any(Department.class));
    }

    @Test
    void findDepartmentById_callsDepartmentRepository() {
        // given
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(new Department()));
        when(departmentMapper.toDepartmentDto(any(Department.class))).thenReturn(new DepartmentDto());
        // when
        DepartmentDto result = departmentService.findDepartmentById(anyLong());
        // then
        assertThat(result).isInstanceOf(DepartmentDto.class);
        verify(departmentRepository, times(1)).findById(anyLong());
    }

    @Test
    void findDepartmentById_DepartmentByGivenIdDoesNotExist_callsDepartmentRepositoryAndThrowsException() {
        // given
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.empty());
        // when
        Throwable result = catchThrowable(() -> departmentService.findDepartmentById(anyLong()));
        // then
        assertThat(result).isExactlyInstanceOf(DepartmentNotFoundException.class);
        verify(departmentRepository, times(1)).findById(anyLong());
    }

    @Test
    void deleteDepartmentById_callsDepartmentRepository() {
        // given
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(new Department()));
        when(departmentMapper.toDepartmentDto(any(Department.class))).thenReturn(new DepartmentDto());
        // when
        DepartmentDto result = departmentService.deleteDepartment(anyLong());
        // then
        assertThat(result).isInstanceOf(DepartmentDto.class);
        verify(departmentRepository, times(1)).findById(anyLong());
        verify(departmentRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteDepartmentById_DepartmentByGivenIdDoesNotExist_callsDepartmentRepositoryAndThrowsException() {
        // given
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.empty());
        // when
        Throwable result = catchThrowable(() -> departmentService.deleteDepartment(anyLong()));
        // then
        assertThat(result).isExactlyInstanceOf(DepartmentNotFoundException.class);
        verify(departmentRepository, times(1)).findById(anyLong());
        verify(departmentRepository, times(0)).deleteById(anyLong());
    }

}
