package io.mhan.springjpatest2.comments.repository;

import io.mhan.springjpatest2.comments.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CommentQueryDslRepository {
    Page<Comment> findActiveAllByPostId(Long postId, Pageable pageable);

    Optional<Comment> findActiveById(Long commentId);
}
