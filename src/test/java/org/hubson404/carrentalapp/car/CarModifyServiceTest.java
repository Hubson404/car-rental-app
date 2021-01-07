package org.hubson404.carrentalapp.car;

import org.hubson404.carrentalapp.exceptions.EmployeeNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarModifyServiceTest {

    @Mock
    CarRepository carRepository;
    @InjectMocks
    CarModifyService carModifyService;

    @Test
    void modifyCar() {
    }

    @Test
    void deleteCarById() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.empty());
        // when
        Throwable result = catchThrowable(() -> carModifyService.deleteCarById(anyLong()));
        // then
        assertThat(result).isExactlyInstanceOf(EmployeeNotFoundException.class);
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(0)).deleteById(anyLong());
    }
}
