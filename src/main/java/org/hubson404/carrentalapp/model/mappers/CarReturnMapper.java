package org.hubson404.carrentalapp.model.mappers;

import org.hubson404.carrentalapp.domain.CarReturn;
import org.hubson404.carrentalapp.model.CarReturnDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(uses = {CarReservationMapper.class, EmployeeMapper.class, DepartmentMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CarReturnMapper {

    CarReturn toCarReturn(CarReturnDto carReturnDto);

    CarReturnDto toCarReturnDto(CarReturn carReturn);
}
