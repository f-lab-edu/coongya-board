package com.flab.coongyaboard.auth.repository;

import com.flab.coongyaboard.auth.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    UserEntity findByEmail(@Param("email") String email);

    UserEntity findByEmailForUpdate(@Param("email") String email);

    boolean existsByEmail(@Param("email") String email);

    Long findIdByEmail(@Param("email") String email);

    void insert(UserEntity userEntity);
}
