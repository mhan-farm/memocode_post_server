package io.mhan.springjpatest2.tags.repository;

import io.mhan.springjpatest2.tags.entity.Tag;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface TagRepository extends CrudRepository<Tag, Long> {
    Optional<Tag> findByName(String name);
    Set<Tag> findAllByNameIn(Set<String> stringNames);
}
