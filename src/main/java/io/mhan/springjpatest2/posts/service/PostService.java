package io.mhan.springjpatest2.posts.service;

import io.mhan.springjpatest2.posts.dto.PostDto;
import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.posts.repository.PostRepository;
import io.mhan.springjpatest2.posts.repository.vo.PostKeyword;
import io.mhan.springjpatest2.posts.repository.vo.PostKeywordType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post findByIdElseThrow(Long postId) {
        return findById(postId)
                .orElseThrow();
    }

    private Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Transactional
    public void increaseLike(Long postId) {
        Post post = findByIdElseThrow(postId);
        post.increaseLike();
    }

    public Page<PostDto> findAll(PostKeyword postKeyword, Pageable pageable) {

        Page<Post> page = postRepository.findAll(postKeyword, pageable);

        return PostDto.fromPagePost(page);
    }
}
