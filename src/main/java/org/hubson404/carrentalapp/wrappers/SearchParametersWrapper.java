package org.hubson404.carrentalapp.wrappers;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchParametersWrapper {

    private Long departmentId;
    private String rentStartDate;
    private String rentEndDate;


}
