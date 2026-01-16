package com.flab.coongyaboard.auth.repository;

import com.flab.coongyaboard.auth.domain.User;
import com.flab.coongyaboard.auth.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    UserEntity findByEmailForUpdate(@Param("email") String email);

    boolean existsByEmail(@Param("email") String email);

    void insert(UserEntity userEntity);
}
