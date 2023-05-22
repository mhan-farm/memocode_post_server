package io.mhan.springjpatest2.users.repository;

import io.mhan.springjpatest2.users.entity.User;

import java.util.Optional;

public interface UserQueryDslRepository {
    Optional<User> findActiveById(Long userId);
}
