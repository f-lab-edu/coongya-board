package com.flab.coongyaboard.auth.domain;

import com.flab.coongyaboard.auth.entity.UserEntity;

import java.time.LocalDateTime;

public class User {

    private final Long id;
    private final String email;
    private final String nickname;
    private final String password;
    private final LocalDateTime createdAt;

    private User(Long id, String email, String nickname, String password, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.createdAt = createdAt;
    }

    public static User create(String email, String nickname, String password) {
        if (email == null || email.isEmpty()) {
          throw new RuntimeException("email is empty");
        }
        if (nickname == null || nickname.isEmpty()) {
            throw new RuntimeException("nickname is empty");
        }
        if (password == null || password.isEmpty()) {
            throw new RuntimeException("password is empty");
        }

        return new User(null, email, nickname, password, null);
    }

    public UserEntity toEntity() {
        return new UserEntity(this.id, this.email, this.nickname, this.password, this.createdAt);
    }
}
