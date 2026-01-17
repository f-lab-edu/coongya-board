package com.flab.coongyaboard.auth.entity;

import com.flab.coongyaboard.auth.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    private Long id;
    private String email;
    private String nickname;
    private String password;
    private LocalDateTime createdAt;

    public User toDomain() {
        return User.create(this.email, this.nickname, this.password);
    }
}
