package org.hubson404.carrentalapp.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarRentalCompanyDto {

    private Long id;
    private String companyName;
    private String domainAddress;
    private String contactAddress;
    private String ownersName;
    private String companyLogo;

}
