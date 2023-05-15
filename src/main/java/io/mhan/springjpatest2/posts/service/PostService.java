package io.mhan.springjpatest2.posts.service;

import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.posts.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
}
