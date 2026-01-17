package com.flab.coongyaboard.auth.service;

import com.flab.coongyaboard.auth.domain.User;
import com.flab.coongyaboard.auth.dto.LoginRequest;
import com.flab.coongyaboard.auth.dto.SignupRequest;
import com.flab.coongyaboard.auth.exception.AuthenticationFailedException;
import com.flab.coongyaboard.auth.exception.DuplicateEmailException;
import com.flab.coongyaboard.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignupRequest request) {

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        userRepository.findByEmailForUpdate(request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException();
        }

        User user = User.create(request.getEmail(), request.getNickname(), encodedPassword);
        userRepository.save(user);
    }

    public void login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(AuthenticationFailedException::new);

        if (!user.matchPassword(request.getPassword(), passwordEncoder)) {
            throw new AuthenticationFailedException();
        }

        // TODO : Spring Security session 생성
    }
}
