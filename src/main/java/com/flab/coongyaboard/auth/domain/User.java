package com.flab.coongyaboard.auth.domain;

import com.flab.coongyaboard.auth.entity.UserEntity;

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
        if (email == null || email.isEmpty()) {
          throw new RuntimeException("email is empty");
        }
        if (nickname == null || nickname.isEmpty()) {
            throw new RuntimeException("nickname is empty");
        }
        if (password == null || password.isEmpty()) {
            throw new RuntimeException("password is empty");
        }

        return new User(email, nickname, password);
    }

    public UserEntity toEntity() {
        return new UserEntity(null, this.email, this.nickname, this.password, null);
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
