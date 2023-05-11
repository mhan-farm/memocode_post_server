package io.mhan.springjpatest2.likes.repository;

import io.mhan.springjpatest2.likes.PostLike;
import org.springframework.data.repository.CrudRepository;

public interface PostLikeRepository extends CrudRepository<PostLike, Long> {
}
