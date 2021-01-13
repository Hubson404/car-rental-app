package org.hubson404.carrentalapp.wrappers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarRentalCreateWrapper {

    @NotNull
    private Long carReservationId;

    @NotNull
    private Long employeeId;

    private String comments;

    public String getComments() {
        if (comments == null) {
            return "Comment section is empty.";
        }
        return comments;
    }
}
