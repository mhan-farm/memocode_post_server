package io.mhan.springjpatest2.posts.repository;

import io.mhan.springjpatest2.posts.entity.Post;

import java.util.List;

public interface PostQueryDslRepository {
    List<Post> findAll();
}
