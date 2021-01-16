package org.hubson404.carrentalapp.services;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.CarReservation;
import org.hubson404.carrentalapp.domain.CarReturn;
import org.hubson404.carrentalapp.domain.Employee;
import org.hubson404.carrentalapp.domain.enums.CarStatus;
import org.hubson404.carrentalapp.exceptions.CarReservationNotFoundException;
import org.hubson404.carrentalapp.exceptions.CarReturnNotFoundException;
import org.hubson404.carrentalapp.exceptions.EmployeeNotFoundException;
import org.hubson404.carrentalapp.model.CarReturnDto;
import org.hubson404.carrentalapp.model.mappers.CarReturnMapper;
import org.hubson404.carrentalapp.repositories.CarReservationRepository;
import org.hubson404.carrentalapp.repositories.CarReturnRepository;
import org.hubson404.carrentalapp.repositories.EmployeeRepository;
import org.hubson404.carrentalapp.wrappers.CarReturnCreateWrapper;
import org.hubson404.carrentalapp.wrappers.CarReturnWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CarReturnService {

    private final CarReservationRepository carReservationRepository;
    private final CarReturnRepository carReturnRepository;
    private final EmployeeRepository employeeRepository;
    private final CarReturnMapper carReturnMapper;

    public CarReturnDto createCarReturn(CarReturnCreateWrapper wrapper) {
        Employee employee = getEmployeeFromRepository(wrapper);
        CarReservation carReservation = getCarReservationFromRepository(wrapper);
        CarReturn savedCarReturn = saveCarReturn(wrapper, employee, carReservation);
        return carReturnMapper.toCarReturnDto(savedCarReturn);
    }

    public CarReturnDto findById(Long id) {
        CarReturn carReturn = carReturnRepository.findById(id).orElseThrow(() -> new CarReturnNotFoundException(id));
        return carReturnMapper.toCarReturnDto(carReturn);
    }

    public CarReturnWrapper findAll() {
        List<CarReturn> cars = carReturnRepository.findAll();
        return toCarReturnWrapper(cars);
    }

    private CarReturnWrapper toCarReturnWrapper(List<CarReturn> cars) {
        List<CarReturnDto> collect = cars.stream().map(carReturnMapper::toCarReturnDto).collect(Collectors.toList());
        CarReturnWrapper carReturnWrapper = new CarReturnWrapper();
        carReturnWrapper.setCarReturns(collect);
        return carReturnWrapper;
    }

    private CarReturn saveCarReturn(CarReturnCreateWrapper wrapper, Employee employee, CarReservation carReservation) {
        CarReturn carReturn = new CarReturn();
        carReturn.setEmployee(employee);
        carReturn.setReservation(carReservation);
        carReturn.setReturnDate(carReservation.getReturnDate());
        carReturn.setComments(wrapper.getComments());
        if (wrapper.getExtraCharge() == null) carReturn.setExtraCharge(wrapper.getExtraCharge());
        return carReturnRepository.save(carReturn);
    }

    private CarReservation getCarReservationFromRepository(CarReturnCreateWrapper wrapper) {
        CarReservation carReservation = carReservationRepository.findById(wrapper.getCarReservationId())
                .orElseThrow(() -> new CarReservationNotFoundException(wrapper.getCarReservationId()));
        carReservation.getCar().setCarStatus(CarStatus.AVAILABLE);
        return carReservation;
    }

    private Employee getEmployeeFromRepository(CarReturnCreateWrapper wrapper) {
        return employeeRepository.findById(wrapper.getEmployeeId())
                .orElseThrow(() -> new EmployeeNotFoundException(wrapper.getEmployeeId()));
    }
}
