package com.flab.coongyaboard.auth.service;

import com.flab.coongyaboard.auth.domain.User;
import com.flab.coongyaboard.auth.dto.LoginRequest;
import com.flab.coongyaboard.auth.dto.SignupRequest;
import com.flab.coongyaboard.auth.exception.DuplicateEmailException;
import com.flab.coongyaboard.auth.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    private SignupRequest createSignupRequest(String email, String password, String nickname) {
        SignupRequest request = new SignupRequest();
        ReflectionTestUtils.setField(request, "email", email);
        ReflectionTestUtils.setField(request, "password", password);
        ReflectionTestUtils.setField(request, "nickname", nickname);
        return request;
    }

    private LoginRequest createLoginRequest(String email, String password) {
        LoginRequest request = new LoginRequest();
        ReflectionTestUtils.setField(request, "email", email);
        ReflectionTestUtils.setField(request, "password", password);
        return request;
    }

    @Test
    @DisplayName("이메일이 중복되지 않으면 회원가입 성공")
    void signup_success_when_email_not_duplicate() throws Exception {
        // given
        String email = "user@example.com";
        String password = "password123!";
        String encodedPassword = "encoded123!";
        String nickname = "닉네임";
        SignupRequest request = createSignupRequest(email, password, nickname);

        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(userRepository.findByEmailForUpdate(email)).thenReturn(Optional.empty());

        // when
        authService.signup(request);

        // then
        verify(passwordEncoder).encode(password);
        verify(userRepository).findByEmailForUpdate(email);
        verify(userRepository).save(email, nickname, encodedPassword);
    }

    @Test
    @DisplayName("이메일이 중복되면 DuplicateEmailException을 던지고 회원가입 실패")
    void signup_fail_when_email_duplicate() {
        // given
        String email = "user@example.com";
        String password = "password123!";
        String encodedPassword = "encoded123!";
        String nickname = "닉네임";
        SignupRequest request = createSignupRequest(email, password, nickname);

        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(userRepository.findByEmailForUpdate(email)).thenReturn(Optional.of(User.create(email, nickname, encodedPassword)));

        // when & then
        assertThatThrownBy(() -> authService.signup(request))
                .isInstanceOf(DuplicateEmailException.class);

        verify(passwordEncoder).encode(password);
        verify(userRepository).findByEmailForUpdate(email);
        verify(userRepository, never()).save(anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("유효한 이메일, 비밀번호로 로그인 성공")
    void login_success_when_valid_email_and_password() throws Exception {
        // TODO
    }

    @Test
    @DisplayName("이메일이 존재하지 않으면 로그인 실패")
    void login_fail_when_invalid_email() {
        // TODO
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않으면 로그인 실패")
    void login_fail_when_invalid_password() {
        // TODO
    }
}
