package io.mhan.springjpatest2.posts.service;

import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.posts.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    
    private final PostRepository postRepository;

    public Post findByIdElseThrow(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow();
    }
}
