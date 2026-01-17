package com.flab.coongyaboard.auth.service;

import com.flab.coongyaboard.auth.domain.User;
import com.flab.coongyaboard.auth.dto.LoginRequest;
import com.flab.coongyaboard.auth.dto.SignupRequest;
import com.flab.coongyaboard.auth.exception.AuthenticationFailedException;
import com.flab.coongyaboard.auth.exception.DuplicateEmailException;
import com.flab.coongyaboard.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignupRequest request) {

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Optional<User> existingUser = userRepository.findByEmailForUpdate(request.getEmail());

        if (existingUser.isPresent()) {
            throw new DuplicateEmailException();
        }

        userRepository.save(request.getEmail(), request.getNickname(), encodedPassword);
    }

    public void login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(AuthenticationFailedException::new);

        if (!user.matchPassword(request.getPassword(), passwordEncoder)) {
            throw new AuthenticationFailedException();
        }

        Long userId = userRepository.findIdByEmail(request.getEmail());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userId,
                null,
                // TODO authorities 관리
                Collections.emptyList()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
