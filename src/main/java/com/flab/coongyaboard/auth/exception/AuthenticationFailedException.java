package com.flab.coongyaboard.auth.exception;

import com.flab.coongyaboard.common.exception.BusinessException;

public class AuthenticationFailedException extends BusinessException {

    private static final String AUTHENTICATION_FAILED = "AUTHENTICATION_FAILED";

    public AuthenticationFailedException() {
        super("email/password", AUTHENTICATION_FAILED, "이메일 또는 비밀번호가 올바르지 않습니다.");
    }
}
