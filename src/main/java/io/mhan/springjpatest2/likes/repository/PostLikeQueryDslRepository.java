package io.mhan.springjpatest2.likes.repository;

import io.mhan.springjpatest2.likes.entity.PostLike;

import java.util.List;

public interface PostLikeQueryDslRepository {
    List<PostLike> findByUserId(Long userId);
}
