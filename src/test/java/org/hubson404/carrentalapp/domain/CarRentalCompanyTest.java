package org.hubson404.carrentalapp.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CarRentalCompanyTest {

    @Autowired
    CarRentalCompany carRentalCompany;

    @Test
    void getCompanyName() {
        assertThat(carRentalCompany.getCompanyName()).isEqualTo("company");
    }

    @Test
    void getDomainAddress() {
        assertThat(carRentalCompany.getDomainAddress()).isEqualTo("domain");
    }

    @Test
    void getContactAddress() {
        assertThat(carRentalCompany.getContactAddress()).isEqualTo("address");
    }

    @Test
    void getOwnersName() {

        assertThat(carRentalCompany.getOwnersName()).isEqualTo("owner");
    }

    @Test
    void getCompanyLogo() {
        assertThat(carRentalCompany.getCompanyLogo()).isEqualTo("logo");
    }
}
