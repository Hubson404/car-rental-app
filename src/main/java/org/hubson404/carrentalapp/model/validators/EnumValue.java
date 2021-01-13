package org.hubson404.carrentalapp.model.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EnumValueImpl.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EnumValue {

    Class<? extends Enum<?>> enumClass();

    String message() default "Enum value '${validatedValue}' is invalid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

