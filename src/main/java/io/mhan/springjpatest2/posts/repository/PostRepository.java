package io.mhan.springjpatest2.posts.repository;

import io.mhan.springjpatest2.posts.entity.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long>, PostQueryDslRepository {
}
