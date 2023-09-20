package io.mhan.springjpatest2.posts.repository;

import io.mhan.springjpatest2.posts.entity.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface PostRepository extends CrudRepository<Post, Long>, PostQueryDslRepository {

    List<Post> findByIdIn(Collection<UUID> postIds);
}
