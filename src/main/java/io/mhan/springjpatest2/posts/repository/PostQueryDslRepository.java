package io.mhan.springjpatest2.posts.repository;

import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.posts.repository.vo.PostKeyword;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface PostQueryDslRepository {
    Page<Post> findPublicPostAll(PostKeyword keyword, Pageable pageable);
    Page<Post> findByAuthorId(UUID authorId, PostKeyword keyword, Pageable pageable);
    Optional<Post> findActiveById(UUID postId);
}
