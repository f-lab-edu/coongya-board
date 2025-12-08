package com.flab.coongyaboard.auth.service;

import com.flab.coongyaboard.auth.domain.User;
import com.flab.coongyaboard.auth.dto.SignupRequest;
import com.flab.coongyaboard.auth.exception.DuplicateEmailException;
import com.flab.coongyaboard.auth.repository.UserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserMapper userMapper;

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
        SignupRequest request = createSignupRequest(email, "Password123!", "닉네임");

        when(userMapper.existsByEmail(email)).thenReturn(false);

        // when
        authService.signup(request);

        // then
        verify(userMapper).findByEmailForUpdate(email);
        verify(userMapper).existsByEmail(email);
        verify(userMapper).insert(any(User.class));
    }

    @Test
    @DisplayName("이메일이 중복되면 DuplicateEmailException을 던지고 회원가입 실패")
    void signup_fail_when_email_duplicate() {
        // given
        String email = "user@example.com";
        SignupRequest request = createSignupRequest(email, "Password123!", "닉네임");

        when(userMapper.existsByEmail(email)).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> authService.signup(request))
                .isInstanceOf(DuplicateEmailException.class);

        verify(userMapper).findByEmailForUpdate(email);
        verify(userMapper).existsByEmail(email);
        verify(userMapper, never()).insert(any(User.class));
    }
}
