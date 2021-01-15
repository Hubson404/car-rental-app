package org.hubson404.carrentalapp.model.mappers;


import org.hubson404.carrentalapp.domain.CarRentalCompany;
import org.hubson404.carrentalapp.model.CarRentalCompanyDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {DepartmentMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CarRentalCompanyMapper {

    @Mapping(target = "departments", ignore = true)
    CarRentalCompany toCarRentalCompany(CarRentalCompanyDto carRentalCompanyDto);

    CarRentalCompanyDto toCarRentalCompanyDto(CarRentalCompany carRentalCompany);

}
