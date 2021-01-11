package org.hubson404.carrentalapp.model.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.function.Function;

public abstract class GenericStringValidator<A extends Annotation> implements ConstraintValidator<A, String> {

    protected A annotation;

    @Override
    public void initialize(A constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return condition().apply(value);
    }

    public abstract Function<String, Boolean> condition();
}
