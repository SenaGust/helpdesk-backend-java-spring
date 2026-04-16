package com.senagust.helpdesk.validation.validator;

import com.senagust.helpdesk.validation.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return password != null && password.length() >= 8 && password.length() <= 24 && password.matches(".*\\d.*");
    }
}
