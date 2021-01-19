package org.hubson404.carrentalapp.security;

import lombok.RequiredArgsConstructor;
import org.hubson404.carrentalapp.exceptions.EmployeeNotFoundException;
import org.hubson404.carrentalapp.repositories.EmployeeRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityUserService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return employeeRepository.findEmployeesByEmail(email)
                .orElseThrow(() -> new EmployeeNotFoundException(email));
    }

}
