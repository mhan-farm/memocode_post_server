package io.mhan.springjpatest2.likes.repository;


import io.mhan.springjpatest2.likes.entity.PostLike;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PostLikeRepository extends CrudRepository<PostLike, Long>, PostLikeQueryDslRepository {
    Optional<PostLike> findByPostIdAndUserId(Long postId, Long userId);
}
