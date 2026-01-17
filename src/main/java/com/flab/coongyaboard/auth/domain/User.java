package com.flab.coongyaboard.auth.domain;

import com.flab.coongyaboard.auth.entity.UserEntity;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

public class User {

    @Getter
    private final Long id;
    private final String email;
    private final String nickname;
    private final String password;

    private User(Long id, String email, String nickname, String password) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public static User create(String email, String nickname, String password) {
        throwIfNullOrEmpty(email, "email is empty");
        throwIfNullOrEmpty(nickname, "nickname is empty");
        throwIfNullOrEmpty(password, "password is empty");

        return new User(null, email, nickname, password);
    }

    public static User fromEntity(UserEntity userEntity) {
        if (userEntity.getId() == null) { throw new RuntimeException("id is null"); }
        throwIfNullOrEmpty(userEntity.getEmail(), "email is empty");
        throwIfNullOrEmpty(userEntity.getNickname(), "nickname is empty");
        throwIfNullOrEmpty(userEntity.getPassword(), "password is empty");

        return new User(userEntity.getId(), userEntity.getEmail(), userEntity.getNickname(), userEntity.getPassword());
    }

    public UserEntity toEntity() {
        return new UserEntity(null, email, nickname, password, null);
    }

    public boolean matchPassword(String rawPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(rawPassword, password);
    }

    private static void throwIfNullOrEmpty(String field, String message) {
        if (field == null || field.isEmpty()) {
            throw new RuntimeException(message);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        User user = (User) object;
        return email.equals(user.email) && nickname.equals(user.nickname) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        int result = email.hashCode();
        result = 31 * result + nickname.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }
}
