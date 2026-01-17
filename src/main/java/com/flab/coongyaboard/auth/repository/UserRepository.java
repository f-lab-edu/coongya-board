package com.flab.coongyaboard.auth.repository;

import com.flab.coongyaboard.auth.domain.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailForUpdate(String email);

    boolean existsByEmail(String email);

    Long findIdByEmail(String email);

    void save(String email, String nickname, String password);
}
