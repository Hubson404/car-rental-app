package org.hubson404.carrentalapp.car;

import org.hubson404.carrentalapp.domain.Car;
import org.hubson404.carrentalapp.domain.Department;
import org.hubson404.carrentalapp.exceptions.CarNotFoundException;
import org.hubson404.carrentalapp.exceptions.DepartmentNotFoundException;
import org.hubson404.carrentalapp.model.CarDto;
import org.hubson404.carrentalapp.model.DepartmentDto;
import org.hubson404.carrentalapp.model.mappers.CarMapper;
import org.hubson404.carrentalapp.repositories.CarRepository;
import org.hubson404.carrentalapp.repositories.DepartmentRepository;
import org.hubson404.carrentalapp.services.CarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    DepartmentRepository departmentRepository;
    @Mock
    CarRepository carRepository;
    @Mock
    CarMapper carMapper;
    @InjectMocks
    CarService carService;

    @Test
    void createCar_callsCarRepository() {

        // given
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(new Department()));
        when(carMapper.toCar(any(CarDto.class))).thenReturn(new Car());
        CarDto carDTO = new CarDto(
                null, "S5", "Audi", "COUPE", 1999,
                "WHITE", 0L, "AVAILABLE", 100d, DepartmentDto.builder().id(1L).build());
        // when
        carService.createCar(carDTO);
        // then
        verify(carRepository, times(1)).save(any(Car.class));

    }

    @Test
    void createCar_whenMileageFieldIsNull_CallsCarRepository() {
        // given
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(new Department()));
        when(carRepository.save(any(Car.class))).thenReturn(new Car());
        when(carMapper.toCar(any(CarDto.class))).thenReturn(new Car());

        CarDto carDTO = new CarDto(
                null, "Mmm", "Bbb", "COUPE", 1999,
                "BLK", null, "AVAILABLE", 100d, DepartmentDto.builder().id(1L).build());
        // when
        carService.createCar(carDTO);
        // then
        verify(carRepository, times(1)).save(any(Car.class));
    }

    @Test
    void modifyCar_CarByGivenIdDoesNotExist_callsCarRepositoryAndThrowsException() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.empty());
        // when
        Throwable result = catchThrowable(() -> carService.modifyCar(1L, new CarDto()));
        // then
        assertThat(result).isExactlyInstanceOf(CarNotFoundException.class);
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(0)).save(any());
    }

    @Test
    void modifyCar_changeCarBrand_CallsCarRepository() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(new Car()));
        when(carRepository.save(any(Car.class))).thenReturn(new Car());
        when(carMapper.toCarDto(any(Car.class))).thenReturn(new CarDto());
        CarDto carDTO = org.hubson404.carrentalapp.model.CarDto.builder().brand("Audi").build();
        // when
        carService.modifyCar(1L, carDTO);
        // then
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(1)).save(any());
    }

    @Test
    void modifyCar_carBrandIsBlank_CallsCarRepository() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(new Car()));
        CarDto carDTO = org.hubson404.carrentalapp.model.CarDto.builder().brand("  ").build();
        // when
        Throwable result = catchThrowable(() -> carService.modifyCar(1L, carDTO));
        // then
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(0)).save(any());
    }

    @Test
    void modifyCar_changeCarModel_CallsCarRepository() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(new Car()));
        when(carRepository.save(any(Car.class))).thenReturn(new Car());
        when(carMapper.toCarDto(any(Car.class))).thenReturn(new CarDto());
        CarDto carDTO = org.hubson404.carrentalapp.model.CarDto.builder().model("S5").build();
        // when
        carService.modifyCar(1L, carDTO);
        // then
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(1)).save(any());
    }

    @Test
    void modifyCar_carModelIsBlank_CallsCarRepository() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(new Car()));
        CarDto carDTO = org.hubson404.carrentalapp.model.CarDto.builder().model("  ").build();
        // when
        Throwable result = catchThrowable(() -> carService.modifyCar(1L, carDTO));
        // then
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(0)).save(any());
    }

    @Test
    void modifyCar_changeCarProductionYear_CallsCarRepository() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(new Car()));
        when(carRepository.save(any(Car.class))).thenReturn(new Car());
        when(carMapper.toCarDto(any(Car.class))).thenReturn(new CarDto());
        CarDto carDTO = org.hubson404.carrentalapp.model.CarDto.builder().productionYear(1999).build();
        // when
        carService.modifyCar(1L, carDTO);
        // then
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(1)).save(any());
    }

    @Test
    void modifyCar_CarProductionYearIsSetToLessThan1950_CallsCarRepository() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(new Car()));
        CarDto carDTO = org.hubson404.carrentalapp.model.CarDto.builder().productionYear(1400).build();
        // when
        Throwable result = catchThrowable(() -> carService.modifyCar(1L, carDTO));
        // then
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(0)).save(any());
    }

    @Test
    void modifyCar_CarProductionYearIsGreaterThanCurrentYear_CallsCarRepository() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(new Car()));
        CarDto carDTO = org.hubson404.carrentalapp.model.CarDto.builder().productionYear(1400).build();
        // when
        Throwable result = catchThrowable(() -> carService.modifyCar(1L, carDTO));
        // then
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(0)).save(any());
    }

    @Test
    void modifyCar_changeCarMileage_CallsCarRepository() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(new Car()));
        when(carRepository.save(any(Car.class))).thenReturn(new Car());
        when(carMapper.toCarDto(any(Car.class))).thenReturn(new CarDto());
        CarDto carDTO = org.hubson404.carrentalapp.model.CarDto.builder().mileage(100L).build();
        // when
        carService.modifyCar(1L, carDTO);
        // then
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(1)).save(any());
    }

    @Test
    void modifyCar_carMileageToNegativeValue_CallsCarRepository() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(new Car()));
        CarDto carDTO = org.hubson404.carrentalapp.model.CarDto.builder().mileage(-100L).build();
        // when
        Throwable result = catchThrowable(() -> carService.modifyCar(1L, carDTO));
        // then
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(0)).save(any());
    }

    @Test
    void modifyCar_changeCarCostPerDay_CallsCarRepository() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(new Car()));
        when(carRepository.save(any(Car.class))).thenReturn(new Car());
        when(carMapper.toCarDto(any(Car.class))).thenReturn(new CarDto());
        CarDto carDTO = org.hubson404.carrentalapp.model.CarDto.builder().costPerDay(100d).build();
        // when
        carService.modifyCar(1L, carDTO);
        // then
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(1)).save(any());
    }

    @Test
    void modifyCar_carCostPerDaySetToLessThanZero_CallsCarRepository() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(new Car()));
        CarDto carDTO = org.hubson404.carrentalapp.model.CarDto.builder().costPerDay(-100d).build();
        // when
        Throwable result = catchThrowable(() -> carService.modifyCar(1L, carDTO));
        // then
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(0)).save(any());
    }

    @Test
    void modifyCar_changeCarBodyType_CallsCarRepository() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(new Car()));
        when(carRepository.save(any(Car.class))).thenReturn(new Car());
        when(carMapper.toCarDto(any(Car.class))).thenReturn(new CarDto());
        CarDto carDTO = org.hubson404.carrentalapp.model.CarDto.builder().carBodyType("COUPE").build();
        // when
        carService.modifyCar(1L, carDTO);
        // then
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(1)).save(any());
    }

    @Test
    void modifyCar_CarBodyTypeIsInvalid_CallsCarRepository() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(new Car()));
        CarDto carDTO = org.hubson404.carrentalapp.model.CarDto.builder().carBodyType("NONE").build();
        // when
        Throwable result = catchThrowable(() -> carService.modifyCar(1L, carDTO));
        // then
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(0)).save(any());
    }

    @Test
    void modifyCar_changeCarBodyColor_CallsCarRepository() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(new Car()));
        when(carRepository.save(any(Car.class))).thenReturn(new Car());
        when(carMapper.toCarDto(any(Car.class))).thenReturn(new CarDto());
        CarDto carDTO = org.hubson404.carrentalapp.model.CarDto.builder().color("BLACK").build();
        // when
        carService.modifyCar(1L, carDTO);
        // then
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(1)).save(any());
    }

    @Test
    void modifyCar_CarBodyColorIsInvalid_CallsCarRepository() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(new Car()));
        CarDto carDTO = org.hubson404.carrentalapp.model.CarDto.builder().color("NEON_GREEN").build();
        // when
        Throwable result = catchThrowable(() -> carService.modifyCar(1L, carDTO));
        // then
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(0)).save(any());
    }

    @Test
    void modifyCar_changeCarStatus_CallsCarRepository() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(new Car()));
        when(carRepository.save(any(Car.class))).thenReturn(new Car());
        when(carMapper.toCarDto(any(Car.class))).thenReturn(new CarDto());
        CarDto carDTO = org.hubson404.carrentalapp.model.CarDto.builder().carStatus("AVAILABLE").build();
        // when
        carService.modifyCar(1L, carDTO);
        // then
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(1)).save(any());
    }

    @Test
    void modifyCar_changeCarStatusIsInvalid_CallsCarRepository() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(new Car()));
        CarDto carDTO = org.hubson404.carrentalapp.model.CarDto.builder().carStatus("INVALID_STATUS").build();
        // when
        Throwable result = catchThrowable(() -> carService.modifyCar(1L, carDTO));
        // then
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(0)).save(any());
    }

    @Test
    void modifyCar_changeCarDepartment_CallsCarRepository() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(new Car()));
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(new Department()));
        when(carRepository.save(any(Car.class))).thenReturn(new Car());
        when(carMapper.toCarDto(any(Car.class))).thenReturn(new CarDto());
        CarDto carDTO = org.hubson404.carrentalapp.model.CarDto.builder().department(new DepartmentDto(1L, null)).build();
        // when
        carService.modifyCar(1L, carDTO);
        // then
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(1)).save(any());
    }

    @Test
    void modifyCar_CarDepartmentNotFound_CallsCarRepository() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(new Car()));
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.empty());
        CarDto carDTO = org.hubson404.carrentalapp.model.CarDto.builder().department(new DepartmentDto(1L, null)).build();
        // when
        Throwable result = catchThrowable(() -> carService.modifyCar(1L, carDTO));
        // then
        assertThat(result).isExactlyInstanceOf(DepartmentNotFoundException.class);
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(0)).save(any());
    }

    @Test
    void deleteCarById_callsCarRepository() {
        // given
        Car car = new Car();
        car.setId(1L);
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(car));
        // when
        carService.deleteCarById(1L);
        // then
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteCarById_CarByIdNotFound_ThrowsException() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.empty());
        // when
        Throwable result = catchThrowable(() -> carService.deleteCarById(anyLong()));
        // then
        assertThat(result).isExactlyInstanceOf(CarNotFoundException.class);
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(0)).deleteById(anyLong());
    }

    @Test
    void findAllCars_callsCarRepository() {
        // given
        when(carRepository.findAll()).thenReturn(List.of(new Car(), new Car()));
        when(carMapper.toCarDto(any(Car.class))).thenReturn(new CarDto());
        // when
        carService.findAllCars();
        // then
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void findCarById_callsCarRepository() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(new Car()));
        when(carMapper.toCarDto(any(Car.class))).thenReturn(new CarDto());
        // when
        carService.findCarById(anyLong());
        // then
        verify(carRepository, times(1)).findById(anyLong());
    }

    @Test
    void findCarById_CarByGivenIdDoesNotExist_callsCarRepositoryAndThrowsException() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.empty());
        // when
        Throwable result = catchThrowable(() -> carService.findCarById(anyLong()));
        // then
        assertThat(result).isExactlyInstanceOf(CarNotFoundException.class);
        verify(carRepository, times(1)).findById(anyLong());
    }
}
