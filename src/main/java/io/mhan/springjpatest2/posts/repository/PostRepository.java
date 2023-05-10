package io.mhan.springjpatest2.posts.repository;

import io.mhan.springjpatest2.posts.entity.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long>, PostQueryDslRepository {
}
