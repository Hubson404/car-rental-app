package org.hubson404.carrentalapp.model.validators;


import java.util.Objects;
import java.util.function.Function;

public class UpperCaseValidator extends GenericStringValidator<UpperCase> {

    @Override
    public Function<String, Boolean> condition() {
        return str -> Objects.equals(str, str.toUpperCase());
    }
}
