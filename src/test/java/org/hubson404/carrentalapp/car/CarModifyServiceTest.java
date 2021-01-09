package org.hubson404.carrentalapp.car;

import org.hubson404.carrentalapp.department.DepartmentRepository;
import org.hubson404.carrentalapp.domain.Car;
import org.hubson404.carrentalapp.domain.Department;
import org.hubson404.carrentalapp.exceptions.CarNotFoundException;
import org.hubson404.carrentalapp.exceptions.DepartmentNotFoundException;
import org.hubson404.carrentalapp.model.CarDTO;
import org.hubson404.carrentalapp.model.DepartmentDTO;
import org.hubson404.carrentalapp.model.mappers.CarMapper;
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
    @Mock
    CarMapper carMapper;
    @Mock
    DepartmentRepository departmentRepository;
    @InjectMocks
    CarModifyService carModifyService;

    @Test
    void modifyCar_CarByGivenIdDoesNotExist_callsCarRepositoryAndThrowsException() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.empty());
        // when
        Throwable result = catchThrowable(() -> carModifyService.modifyCar(1L, new CarDTO()));
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
        when(carMapper.toCarDTO(any(Car.class))).thenReturn(new CarDTO());
        CarDTO carDTO = CarDTO.builder().brand("Audi").build();
        // when
        carModifyService.modifyCar(1L, carDTO);
        // then
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(1)).save(any());
    }

    @Test
    void modifyCar_carBrandIsBlank_CallsCarRepository() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(new Car()));
        CarDTO carDTO = CarDTO.builder().brand("  ").build();
        // when
        Throwable result = catchThrowable(() -> carModifyService.modifyCar(1L, carDTO));
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
        when(carMapper.toCarDTO(any(Car.class))).thenReturn(new CarDTO());
        CarDTO carDTO = CarDTO.builder().model("S5").build();
        // when
        carModifyService.modifyCar(1L, carDTO);
        // then
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(1)).save(any());
    }

    @Test
    void modifyCar_carModelIsBlank_CallsCarRepository() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(new Car()));
        CarDTO carDTO = CarDTO.builder().model("  ").build();
        // when
        Throwable result = catchThrowable(() -> carModifyService.modifyCar(1L, carDTO));
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
        when(carMapper.toCarDTO(any(Car.class))).thenReturn(new CarDTO());
        CarDTO carDTO = CarDTO.builder().productionYear(1999).build();
        // when
        carModifyService.modifyCar(1L, carDTO);
        // then
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(1)).save(any());
    }

    @Test
    void modifyCar_CarProductionYearIsSetToLessThan1950_CallsCarRepository() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(new Car()));
        CarDTO carDTO = CarDTO.builder().productionYear(1400).build();
        // when
        Throwable result = catchThrowable(() -> carModifyService.modifyCar(1L, carDTO));
        // then
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException.class);
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(0)).save(any());
    }

    @Test
    void modifyCar_CarProductionYearIsGreaterThanCurrentYear_CallsCarRepository() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(new Car()));
        CarDTO carDTO = CarDTO.builder().productionYear(1400).build();
        // when
        Throwable result = catchThrowable(() -> carModifyService.modifyCar(1L, carDTO));
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
        when(carMapper.toCarDTO(any(Car.class))).thenReturn(new CarDTO());
        CarDTO carDTO = CarDTO.builder().mileage(100L).build();
        // when
        carModifyService.modifyCar(1L, carDTO);
        // then
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(1)).save(any());
    }

    @Test
    void modifyCar_carMileageToNegativeValue_CallsCarRepository() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(new Car()));
        CarDTO carDTO = CarDTO.builder().mileage(-100L).build();
        // when
        Throwable result = catchThrowable(() -> carModifyService.modifyCar(1L, carDTO));
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
        when(carMapper.toCarDTO(any(Car.class))).thenReturn(new CarDTO());
        CarDTO carDTO = CarDTO.builder().costPerDay(100d).build();
        // when
        carModifyService.modifyCar(1L, carDTO);
        // then
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(1)).save(any());
    }

    @Test
    void modifyCar_carCostPerDaySetToLessThanZero_CallsCarRepository() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(new Car()));
        CarDTO carDTO = CarDTO.builder().costPerDay(-100d).build();
        // when
        Throwable result = catchThrowable(() -> carModifyService.modifyCar(1L, carDTO));
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
        when(carMapper.toCarDTO(any(Car.class))).thenReturn(new CarDTO());
        CarDTO carDTO = CarDTO.builder().carBodyType("COUPE").build();
        // when
        carModifyService.modifyCar(1L, carDTO);
        // then
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(1)).save(any());
    }

    @Test
    void modifyCar_CarBodyTypeIsInvalid_CallsCarRepository() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(new Car()));
        CarDTO carDTO = CarDTO.builder().carBodyType("NONE").build();
        // when
        Throwable result = catchThrowable(() -> carModifyService.modifyCar(1L, carDTO));
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
        when(carMapper.toCarDTO(any(Car.class))).thenReturn(new CarDTO());
        CarDTO carDTO = CarDTO.builder().color("BLACK").build();
        // when
        carModifyService.modifyCar(1L, carDTO);
        // then
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(1)).save(any());
    }

    @Test
    void modifyCar_CarBodyColorIsInvalid_CallsCarRepository() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(new Car()));
        CarDTO carDTO = CarDTO.builder().color("NEON_GREEN").build();
        // when
        Throwable result = catchThrowable(() -> carModifyService.modifyCar(1L, carDTO));
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
        when(carMapper.toCarDTO(any(Car.class))).thenReturn(new CarDTO());
        CarDTO carDTO = CarDTO.builder().carStatus("AVAILABLE").build();
        // when
        carModifyService.modifyCar(1L, carDTO);
        // then
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(1)).save(any());
    }

    @Test
    void modifyCar_changeCarStatusIsInvalid_CallsCarRepository() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(new Car()));
        CarDTO carDTO = CarDTO.builder().carStatus("INVALID_STATUS").build();
        // when
        Throwable result = catchThrowable(() -> carModifyService.modifyCar(1L, carDTO));
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
        when(carMapper.toCarDTO(any(Car.class))).thenReturn(new CarDTO());
        CarDTO carDTO = CarDTO.builder().department(new DepartmentDTO(1L, null)).build();
        // when
        carModifyService.modifyCar(1L, carDTO);
        // then
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(1)).save(any());
    }

    @Test
    void modifyCar_CarDepartmentNotFound_CallsCarRepository() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(new Car()));
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.empty());
        CarDTO carDTO = CarDTO.builder().department(new DepartmentDTO(1L, null)).build();
        // when
        Throwable result = catchThrowable(() -> carModifyService.modifyCar(1L, carDTO));
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
        carModifyService.deleteCarById(1L);
        // then
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteCarById_CarByIdNotFound_ThrowsException() {
        // given
        when(carRepository.findById(anyLong())).thenReturn(Optional.empty());
        // when
        Throwable result = catchThrowable(() -> carModifyService.deleteCarById(anyLong()));
        // then
        assertThat(result).isExactlyInstanceOf(CarNotFoundException.class);
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(0)).deleteById(anyLong());
    }
}
