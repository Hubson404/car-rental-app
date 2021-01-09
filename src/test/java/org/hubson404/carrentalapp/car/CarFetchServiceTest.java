package org.hubson404.carrentalapp.car;

import org.hubson404.carrentalapp.domain.Car;
import org.hubson404.carrentalapp.exceptions.CarNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarFetchServiceTest {

    @Mock
    CarRepository carRepository;
    @InjectMocks
    CarFetchService carFetchService;

    @Test
    void findAllCars_callsCarRepository() {
        // given
        when(carRepository.findAll()).thenReturn(new ArrayList<>());
        // when
        carFetchService.findAllCars();
        // then
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void findCarById_callsCarRepository() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(new Car()));
        // when
        carFetchService.findCarById(anyLong());
        // then
        verify(carRepository, times(1)).findById(anyLong());
    }

    @Test
    void findCarById_CarByGivenIdDoesNotExist_callsCarRepositoryAndThrowsException() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.empty());
        // when
        Throwable result = catchThrowable(() -> carFetchService.findCarById(anyLong()));
        // then
        assertThat(result).isExactlyInstanceOf(CarNotFoundException.class);
        verify(carRepository, times(1)).findById(anyLong());
    }
}