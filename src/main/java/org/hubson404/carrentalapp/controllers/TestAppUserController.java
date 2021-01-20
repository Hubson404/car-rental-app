package org.hubson404.carrentalapp.controllers;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.domain.utils.AppUser;
import org.hubson404.carrentalapp.exceptions.AppUserNotFoundException;
import org.hubson404.carrentalapp.model.AppUserDto;
import org.hubson404.carrentalapp.model.mappers.AppUserMapper;
import org.hubson404.carrentalapp.repositories.AppUserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestAppUserController {

    private final AppUserRepository appUserRepository;
    private final AppUserMapper appUserMapper;

    @GetMapping("/testuser")
    public AppUserDto getAppUserByEmail(@RequestParam String email) {
        AppUser appUser = appUserRepository.findAppUserByEmail(email)
                .orElseThrow(() -> new AppUserNotFoundException(email));
        return appUserMapper.toDto(appUser);
    }

}
