package io.mhan.springjpatest2.likes.repository;

import io.mhan.springjpatest2.likes.entity.PostLike;

import java.util.List;

public class PostLikeQueryDslRepositoryImpl implements PostLikeQueryDslRepository {
    @Override
    public List<PostLike> findByUserId(Long userId) {
        return null;
    }
}
