package io.mhan.springjpatest2.posts.service;

import io.mhan.springjpatest2.posts.dto.PostDto;
import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.posts.repository.PostRepository;
import io.mhan.springjpatest2.posts.repository.vo.PostKeyword;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    
    private final PostRepository postRepository;

    public Post findByIdElseThrow(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow();
    }

    public Page<PostDto> getPostDtoAll(PostKeyword keyword, Pageable pageable) {

        Page<Post> page = postRepository.findAll(keyword, pageable);

        return PostDto.fromPagePosts(page);
    }
}
