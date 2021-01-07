package org.hubson404.carrentalapp.car;

import org.hubson404.carrentalapp.domain.Car;
import org.hubson404.carrentalapp.exceptions.InsufficientDataException;
import org.hubson404.carrentalapp.model.CarDTO;
import org.hubson404.carrentalapp.model.DepartmentDTO;
import org.hubson404.carrentalapp.model.mappers.CarMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarCreateServiceTest {

    @Mock
    CarRepository carRepository;
    @Mock
    CarMapper carMapper;
    @InjectMocks
    CarCreateService carCreateService;

    @Test
    void createCar_callsCarRepository() {

        // given
        when(carRepository.save(any(Car.class))).thenReturn(new Car());
        when(carMapper.toCar(any(CarDTO.class))).thenReturn(new Car());

        CarDTO carDTO = new CarDTO(
                null, "S5", "Audi", "COUPE", 1999,
                "WHITE", 0L, "AVAILABLE", 100d, new DepartmentDTO());

        // when
        Car result = carCreateService.createCar(carDTO);
        // then
        assertThat(result).isInstanceOf(Car.class);
        verify(carRepository, times(1)).save(any(Car.class));

    }

    @Test
    void createCar_whenModelFieldIsBlank_ThrowsExceptionAndDoesNotCallCarRepository() {
        // given
        CarDTO carDTO = new CarDTO(
                null, "  ", "Aaa", "COUPE", 1999,
                "WHITE", 0L, "AVAILABLE", 100d, new DepartmentDTO());
        // when
        Throwable result = catchThrowable(() -> carCreateService.createCar(carDTO));
        // then
        assertThat(result).isExactlyInstanceOf(InsufficientDataException.class);
        verify(carRepository, times(0)).save(any(Car.class));
    }

    @Test
    void createCar_whenBrandFieldIsBlank_ThrowsExceptionAndDoesNotCallCarRepository() {
        // given
        CarDTO carDTO = new CarDTO(
                null, "S5", "  ", "COUPE", 1999,
                "WHITE", 0L, "AVAILABLE", 100d, new DepartmentDTO());
        // when
        Throwable result = catchThrowable(() -> carCreateService.createCar(carDTO));
        // then
        assertThat(result).isExactlyInstanceOf(InsufficientDataException.class);
        verify(carRepository, times(0)).save(any(Car.class));
    }

    @Test
    void createCar_whenCarBodyTypeFieldIsBlank_ThrowsExceptionAndDoesNotCallCarRepository() {
        // given
        CarDTO carDTO = new CarDTO(
                null, "Mmm", "Bbb", "  ", 1999,
                "WHITE", 0L, "AVAILABLE", 100d, new DepartmentDTO());
        // when
        Throwable result = catchThrowable(() -> carCreateService.createCar(carDTO));
        // then
        assertThat(result).isExactlyInstanceOf(InsufficientDataException.class);
        verify(carRepository, times(0)).save(any(Car.class));
    }

    @Test
    void createCar_whenProductionYearFieldIsNull_ThrowsExceptionAndDoesNotCallCarRepository() {
        // given
        CarDTO carDTO = new CarDTO(
                null, "Mmm", "Bbb", "Bdt", null,
                "WHITE", 0L, "AVAILABLE", 100d, new DepartmentDTO());
        // when
        Throwable result = catchThrowable(() -> carCreateService.createCar(carDTO));
        // then
        assertThat(result).isExactlyInstanceOf(InsufficientDataException.class);
        verify(carRepository, times(0)).save(any(Car.class));
    }

    @Test
    void createCar_whenCarBodyColorFieldIsBlank_ThrowsExceptionAndDoesNotCallCarRepository() {
        // given
        CarDTO carDTO = new CarDTO(
                null, "Mmm", "Bbb", "  ", 1999,
                "BLACK", 0L, "AVAILABLE", 100d, new DepartmentDTO());
        // when
        Throwable result = catchThrowable(() -> carCreateService.createCar(carDTO));
        // then
        assertThat(result).isExactlyInstanceOf(InsufficientDataException.class);
        verify(carRepository, times(0)).save(any(Car.class));
    }

    @Test
    void createCar_whenMileageFieldIsNull_ThrowsExceptionAndDoesNotCallCarRepository() {
        // given
        when(carRepository.save(any(Car.class))).thenReturn(new Car());
        when(carMapper.toCar(any(CarDTO.class))).thenReturn(new Car());

        CarDTO carDTO = new CarDTO(
                null, "Mmm", "Bbb", "COUPE", 1999,
                "BLK", null, "AVAILABLE", 100d, new DepartmentDTO());
        // when
        Car result = carCreateService.createCar(carDTO);
        // then
        assertThat(result).isInstanceOf(Car.class);
        verify(carRepository, times(1)).save(any(Car.class));
    }

    @Test
    void createCar_whenCarStatusFieldIsBlank_ThrowsExceptionAndDoesNotCallCarRepository() {
        // given
        CarDTO carDTO = new CarDTO(
                null, "Mmm", "Bbb", "COUPE", 1999,
                "BLACK", 0L, "   ", 100d, new DepartmentDTO());
        // when
        Throwable result = catchThrowable(() -> carCreateService.createCar(carDTO));
        // then
        assertThat(result).isExactlyInstanceOf(InsufficientDataException.class);
        verify(carRepository, times(0)).save(any(Car.class));
    }

    @Test
    void createCar_whenCostPerDayFieldIsNull_ThrowsExceptionAndDoesNotCallCarRepository() {
        // given
        CarDTO carDTO = new CarDTO(
                null, "Mmm", "Bbb", "COUPE", 1999,
                "BLK", 0L, "AVAILABLE", null, new DepartmentDTO());
        // when
        Throwable result = catchThrowable(() -> carCreateService.createCar(carDTO));
        // then
        assertThat(result).isExactlyInstanceOf(InsufficientDataException.class);
        verify(carRepository, times(0)).save(any(Car.class));
    }

    @Test
    void createCar_whenDepartmentFieldIsNull_ThrowsExceptionAndDoesNotCallCarRepository() {
        // given
        CarDTO carDTO = new CarDTO(
                null, "Mmm", "Bbb", "COUPE", 1999,
                "BLK", 0L, "AVAILABLE", 100d, null);
        // when
        Throwable result = catchThrowable(() -> carCreateService.createCar(carDTO));
        // then
        assertThat(result).isExactlyInstanceOf(InsufficientDataException.class);
        verify(carRepository, times(0)).save(any(Car.class));
    }
}
