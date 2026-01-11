package com.flab.coongyaboard.auth.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordComplexityValidator implements ConstraintValidator<PasswordComplexity, String> {

    private static final String SPECIAL_CHARS = "!@#$%^&*()_+-=[]{}|;':\\\",.<>/?`~";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }

        boolean hasLetter = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char ch : value.toCharArray()) {
            if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) {
                hasLetter = true;
            }
            else if (Character.isDigit(ch)) {
                hasDigit = true;
            }
            else if (SPECIAL_CHARS.indexOf(ch) >= 0) {
                hasSpecial = true;
            }

            if (hasLetter && hasDigit && hasSpecial) {
                return true;
            }
        }

        return hasLetter && hasDigit && hasSpecial;
    }
}
