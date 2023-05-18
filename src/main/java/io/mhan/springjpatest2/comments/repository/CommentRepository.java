package io.mhan.springjpatest2.comments.repository;

import io.mhan.springjpatest2.comments.entity.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long>, CommentQueryDslRepository {
}
