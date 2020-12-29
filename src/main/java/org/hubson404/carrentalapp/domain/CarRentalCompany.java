package org.hubson404.carrentalapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarRentalCompany {

    private String companyName;
    private String domainAddress;
    private String contactAddress;
    private String ownerName;
    private String logo;

    private Set<Department> departments;


}
