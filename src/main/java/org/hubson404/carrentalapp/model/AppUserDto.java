package org.hubson404.carrentalapp.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDto {

    protected Long id;
    protected String firstName;
    protected String lastName;
    protected String email;
}
