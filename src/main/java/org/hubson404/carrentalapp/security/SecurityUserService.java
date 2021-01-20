package org.hubson404.carrentalapp.security;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.exceptions.AppUserNotFoundException;
import org.hubson404.carrentalapp.repositories.AppUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findAppUserByEmail(email)
                .orElseThrow(() -> new AppUserNotFoundException(email));
    }

}
