package com.flab.coongyaboard.auth.repository;

import com.flab.coongyaboard.auth.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    User findByEmailForUpdate(@Param("email") String email);

    boolean existsByEmail(@Param("email") String email);

    void insert(User user);
}
