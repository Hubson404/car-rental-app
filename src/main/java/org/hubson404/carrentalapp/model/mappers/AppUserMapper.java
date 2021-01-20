package org.hubson404.carrentalapp.model.mappers;

import org.hubson404.carrentalapp.domain.utils.AppUser;
import org.hubson404.carrentalapp.model.AppUserDto;
import org.mapstruct.Mapper;

@Mapper
public interface AppUserMapper {

    AppUserDto toDto(AppUser appUser);
}
