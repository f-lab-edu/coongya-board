package com.flab.coongyaboard.auth.repository;

import com.flab.coongyaboard.auth.domain.User;
import com.flab.coongyaboard.auth.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserMapper userMapper;

    @Override
    public Optional<User> findByEmail(String email) {
        UserEntity userEntity = userMapper.findByEmail(email);
        if (userEntity == null) {
            return Optional.empty();
        }
        return Optional.of(userEntity.toDomain());
    }

    @Override
    public Optional<User> findByEmailForUpdate(String email) {
        UserEntity userEntity = userMapper.findByEmailForUpdate(email);
        if (userEntity == null) {
            return Optional.empty();
        }
        return Optional.of(userEntity.toDomain());
    }

    @Override
    public boolean existsByEmail(String email) {
        return userMapper.existsByEmail(email);
    }

    @Override
    public Long findIdByEmail(String email) {
        return userMapper.findIdByEmail(email);
    }

    @Override
    public void save(String email, String nickname, String password) {
        userMapper.insert(new UserEntity(null, email, nickname, password, null));
    }
}
