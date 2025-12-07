package com.flab.coongyaboard.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ValidationErrorCode {

    FIELD_REQUIRED("NotBlank", "FIELD_REQUIRED"),
    INVALID_FIELD_LENGTH("Size",      "INVALID_FIELD_LENGTH"),
    INVALID_EMAIL_FORMAT("Email",    "INVALID_EMAIL_FORMAT"),
    INVALID_PASSWORD_COMPLEXITY("PasswordComplexity", "INVALID_PASSWORD_COMPLEXITY"),

    OTHER(null, "OTHER");

    private final String constraintCode;
    private final String errorCode;

    public static ValidationErrorCode from(String constraintCode) {
        for (ValidationErrorCode value : values()) {
            if (value.constraintCode == null) {
                continue;
            }
            if (value.constraintCode.equals(constraintCode)) {
                return value;
            }
        }
        return OTHER;
    }
}
