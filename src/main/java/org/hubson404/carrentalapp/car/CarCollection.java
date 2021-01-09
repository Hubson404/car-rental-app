package org.hubson404.carrentalapp.car;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hubson404.carrentalapp.model.CarDTO;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarCollection {

    private List<CarDTO> cars;


}
