package io.mhan.springjpatest2.tags.repository;

import io.mhan.springjpatest2.tags.entity.PostTag;
import org.springframework.data.repository.CrudRepository;

public interface PostTagRepository extends CrudRepository<PostTag, Long> {
}
