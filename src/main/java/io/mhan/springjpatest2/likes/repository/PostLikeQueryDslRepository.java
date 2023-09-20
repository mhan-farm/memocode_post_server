package io.mhan.springjpatest2.likes.repository;

import io.mhan.springjpatest2.likes.entity.PostLike;
import io.mhan.springjpatest2.posts.repository.vo.PostKeyword;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

public interface PostLikeQueryDslRepository {
    List<PostLike> findByUserId(UUID authorId, Sort sort, PostKeyword keyword);
    Page<PostLike> findByUserId(UUID authorId, PostKeyword keyword, Pageable pageable);
}
