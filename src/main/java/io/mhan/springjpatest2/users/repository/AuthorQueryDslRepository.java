package io.mhan.springjpatest2.users.repository;

import io.mhan.springjpatest2.users.entity.Author;

import java.util.Optional;
import java.util.UUID;

public interface AuthorQueryDslRepository {
    Optional<Author> findActiveById(UUID userId);
}
