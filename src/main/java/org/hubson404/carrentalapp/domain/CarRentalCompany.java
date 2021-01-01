package org.hubson404.carrentalapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ConfigurationProperties(prefix ="org.hubson404.carrentalapp")
public class CarRentalCompany {

    private String companyName;
    private String domainAddress;
    private String contactAddress;
    private String ownersName;
    private String companyLogo;

    private Set<Department> departments;


}
