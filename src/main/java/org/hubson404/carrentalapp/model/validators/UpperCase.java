package org.hubson404.carrentalapp.model.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = UpperCaseValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface UpperCase {
    String message() default "Validated value '${validatedValue}' doesn't meet the required conditions." +
            " The string needs to contain only upper case letters.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

