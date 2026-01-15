package com.flab.coongyaboard.auth.exception;

import com.flab.coongyaboard.common.exception.BusinessException;

public class DuplicateEmailException extends BusinessException {

    private static final String DUPLICATE_EMAIL = "DUPLICATE_EMAIL";

    public DuplicateEmailException() {
        super("email", DUPLICATE_EMAIL, "이미 사용 중인 이메일입니다.");
    }
}
