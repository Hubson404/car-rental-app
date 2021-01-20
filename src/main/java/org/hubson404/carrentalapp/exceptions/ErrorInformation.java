package org.hubson404.carrentalapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorInformation {

    private String errorMessage;
    private Map<String, List<String>> errors;

    public ErrorInformation(Map<String, List<String>> errors) {
        this.errors = errors;
    }

    public ErrorInformation(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
