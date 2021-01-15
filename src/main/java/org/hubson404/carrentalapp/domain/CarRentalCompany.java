package org.hubson404.carrentalapp.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarRentalCompany {

    @Id
    @GeneratedValue
    private Long id;

    private String companyName;
    private String domainAddress;
    private String contactAddress;
    private String ownersName;
    private String companyLogo;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "carRentalCompany")
    private Set<Department> departments;


}
