package com.flab.coongyaboard.auth.service;

import com.flab.coongyaboard.auth.domain.User;
import com.flab.coongyaboard.auth.dto.SignupRequest;
import com.flab.coongyaboard.auth.exception.DuplicateEmailException;
import com.flab.coongyaboard.auth.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;

    @Transactional
    public void signup(SignupRequest request) throws DuplicateEmailException {
        // TODO 비밀번호 단방향 암호화

        userMapper.findByEmailForUpdate(request.getEmail());

        if (userMapper.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException();
        }

        User user = User.builder()
                .email(request.getEmail())
                .nickname(request.getNickname())
                .password(request.getPassword())
                .build();

        userMapper.insert(user);
    }
}
