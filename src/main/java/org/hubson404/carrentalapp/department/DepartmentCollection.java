package org.hubson404.carrentalapp.department;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hubson404.carrentalapp.model.DepartmentDTO;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentCollection {

    private List<DepartmentDTO> departments;
}
