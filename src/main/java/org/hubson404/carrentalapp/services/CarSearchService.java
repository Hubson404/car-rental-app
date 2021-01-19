package org.hubson404.carrentalapp.services;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.Car;
import org.hubson404.carrentalapp.domain.enums.CarStatus;
import org.hubson404.carrentalapp.model.CarDto;
import org.hubson404.carrentalapp.model.mappers.CarMapper;
import org.hubson404.carrentalapp.repositories.CarRepository;
import org.hubson404.carrentalapp.wrappers.CarWrapper;
import org.hubson404.carrentalapp.wrappers.SearchParametersWrapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarSearchService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public CarWrapper findFreeCarsInTimePeriod(SearchParametersWrapper parameters) {
        List<Car> cars = carRepository.findAll();
        List<Car> carsFreeInGivenPeriod = findFreeCarsInTimePeriod(parameters, cars);
        return toCarWrapper(carsFreeInGivenPeriod);
    }

    private List<Car> findFreeCarsInTimePeriod(SearchParametersWrapper parameters, List<Car> cars) {
        return cars.stream()
                .filter(car -> !car.getCarStatus().equals(CarStatus.UNAVAILABLE))
                .filter(car -> car.getReservations().stream()
                        .filter(carReservation -> carReservation.getReturnDate()
                                .isAfter(LocalDate.parse(parameters.getRentStartDate())))
                        .noneMatch(carReservation -> carReservation.getRentalStartingDate()
                                .isBefore(LocalDate.parse(parameters.getRentEndDate())))
                )
                .collect(Collectors.toList());
    }

    private CarWrapper toCarWrapper(List<Car> cars) {
        List<CarDto> collect = cars.stream().map(carMapper::toCarDto).collect(Collectors.toList());
        CarWrapper carWrapper = new CarWrapper();
        carWrapper.setCars(collect);
        return carWrapper;
    }
}
