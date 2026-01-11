package com.flab.coongyaboard.common.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {

    private final int status;
    private final String statusDescription;
    private final String field;
    private final String code;
    private final String message;
}
