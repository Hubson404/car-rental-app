package org.hubson404.carrentalapp.domain;

import lombok.*;
import org.hubson404.carrentalapp.domain.utils.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "customers")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Customer extends AppUser {

    public Customer(String firstName, String lastName, String email, String password,
                    String address) {
        super(null, firstName, lastName, email, password);
        this.address = address;
    }

    private String address;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
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
