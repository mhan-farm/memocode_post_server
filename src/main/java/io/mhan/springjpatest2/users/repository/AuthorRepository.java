package io.mhan.springjpatest2.users.repository;

import io.mhan.springjpatest2.users.entity.Author;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository extends CrudRepository<Author, UUID>, AuthorQueryDslRepository {

    List<Author> findAll();

    List<Author> findByIdIn(List<UUID> ids);

    Optional<Author> findByUsername(String username);
}
