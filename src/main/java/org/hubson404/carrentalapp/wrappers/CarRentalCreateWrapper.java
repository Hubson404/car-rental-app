package org.hubson404.carrentalapp.wrappers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

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

    @Length(min = 3)
    private String comments;

    public String getComments() {
        if (comments == null) {
            return "Comment section is empty.";
        }
        return comments;
    }
}
