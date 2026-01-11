package com.flab.coongyaboard.auth.dto;

import com.flab.coongyaboard.auth.validation.PasswordComplexity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequest {

    @NotBlank(message = "이메일을 입력하지 않았습니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @Size(min=5, max=191, message = "이메일은 최소 5자, 최대 191자까지 입력할 수 있습니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력하지 않았습니다.")
    @Size(min=8, max=32, message = "비밀번호는 최소 8자, 최대 32자까지 입력할 수 있습니다.")
    @PasswordComplexity(message = "비밀번호는 영문 대소문자, 숫자, 특수문자를 각각 1자 이상 포함해야 합니다.")
    private String password;

    @NotBlank(message = "닉네임을 입력하지 않았습니다.")
    @Size(min=2, max=12, message = "닉네임은 최소 2자, 최대 12자까지 입력할 수 있습니다.")
    private String nickname;
}
