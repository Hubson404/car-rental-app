package org.hubson404.carrentalapp.domain;

import lombok.*;
import org.hubson404.carrentalapp.domain.enums.EmployeePosition;
import org.hubson404.carrentalapp.domain.utils.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "employees")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Employee extends AppUser {

    public Employee(String firstName, String lastName, String password,
                    Department department, EmployeePosition position) {
        super(null, firstName, lastName, createCompanyEmailAddress(firstName, lastName), password);
        this.department = department;
        this.position = position;
    }

    private static String createCompanyEmailAddress(String firstName, String lastName) {
        return lastName + "." + firstName + "@car-rental.org";
    }

    @Enumerated(EnumType.STRING)
    private EmployeePosition position;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
