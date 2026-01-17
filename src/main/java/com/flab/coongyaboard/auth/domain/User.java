package com.flab.coongyaboard.auth.domain;

import org.springframework.security.crypto.password.PasswordEncoder;

public class User {

    private final String email;
    private final String nickname;
    private final String password;

    private User(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public static User create(String email, String nickname, String password) {
        throwIfNullOrEmpty(email, "email is empty");
        throwIfNullOrEmpty(nickname, "nickname is empty");
        throwIfNullOrEmpty(password, "password is empty");

        return new User(email, nickname, password);
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
