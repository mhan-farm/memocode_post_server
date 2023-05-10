package io.mhan.springjpatest2.posts.repository;

import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.posts.repository.vo.Keyword;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface PostQueryDslRepository {
    List<Post> findAll(Keyword keyword, Sort sort);
}
