package io.mhan.springjpatest2.likes.repository;


import io.mhan.springjpatest2.likes.entity.PostLike;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface PostLikeRepository extends CrudRepository<PostLike, UUID>, PostLikeQueryDslRepository {
    Optional<PostLike> findByPostIdAndAuthorId(UUID postId, UUID userId);
}
