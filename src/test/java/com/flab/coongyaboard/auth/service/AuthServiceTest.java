package com.flab.coongyaboard.auth.service;

import com.flab.coongyaboard.auth.domain.User;
import com.flab.coongyaboard.auth.dto.SignupRequest;
import com.flab.coongyaboard.auth.exception.DuplicateEmailException;
import com.flab.coongyaboard.auth.repository.UserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserMapper userMapper;

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

    @Test
    @DisplayName("이메일이 중복되지 않으면 회원가입 성공")
    void signup_success_when_email_not_duplicate() throws Exception {
        // given
        String email = "user@example.com";
        String password = "Password123!";
        String encodedPassword = "Encoded123!";
        String nickname = "닉네임";
        SignupRequest request = createSignupRequest(email, password, nickname);

        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(userMapper.existsByEmail(email)).thenReturn(false);

        // when
        authService.signup(request);

        // then
        verify(passwordEncoder).encode(password);

        verify(userMapper).findByEmailForUpdate(email);
        verify(userMapper).existsByEmail(email);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userMapper).insert(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertThat(savedUser.getEmail()).isEqualTo(email);
        assertThat(savedUser.getNickname()).isEqualTo(nickname);
        assertThat(savedUser.getPassword()).isEqualTo(encodedPassword);
    }

    @Test
    @DisplayName("이메일이 중복되면 DuplicateEmailException을 던지고 회원가입 실패")
    void signup_fail_when_email_duplicate() {
        // given
        String email = "user@example.com";
        String password = "Password123!";
        String encodedPassword = "Encoded123!";
        String nickname = "닉네임";
        SignupRequest request = createSignupRequest(email, password, nickname);

        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(userMapper.existsByEmail(email)).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> authService.signup(request))
                .isInstanceOf(DuplicateEmailException.class);

        verify(passwordEncoder).encode(password);
        verify(userMapper).findByEmailForUpdate(email);
        verify(userMapper).existsByEmail(email);
        verify(userMapper, never()).insert(any(User.class));
    }
}
