package com.senagust.helpdesk.validation;

import com.senagust.helpdesk.validation.validator.ValidPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidPasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "Password must be at least 8 characters and contain at least one number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
