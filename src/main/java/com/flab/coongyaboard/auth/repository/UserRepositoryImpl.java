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
        return Optional.ofNullable(userEntity.toDomain());
    }

    @Override
    public Optional<User> findByEmailForUpdate(String email) {
        UserEntity userEntity = userMapper.findByEmailForUpdate(email);
        if (userEntity == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(userEntity.toDomain());
    }

    @Override
    public boolean existsByEmail(String email) {
        return userMapper.existsByEmail(email);
    }

    @Override
    public void save(User user) {
        userMapper.insert(user.toEntity());
    }
}
