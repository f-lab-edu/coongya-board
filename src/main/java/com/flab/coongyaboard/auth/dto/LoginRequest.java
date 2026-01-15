package com.flab.coongyaboard.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequest {

    @NotBlank(message = "이메일을 입력하지 않았습니다.")
    private String email;
    @NotBlank(message = "비밀번호를 입력하지 않았습니다.")
    private String password;
}
