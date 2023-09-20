package io.mhan.springjpatest2.posts.service;

import io.mhan.springjpatest2.posts.dto.PostDto;
import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.posts.exception.PostException;
import io.mhan.springjpatest2.posts.repository.PostRepository;
import io.mhan.springjpatest2.posts.repository.vo.PostKeyword;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.mhan.springjpatest2.base.exception.ErrorCode.CAN_NOT_ACCESS_POST;
import static io.mhan.springjpatest2.base.exception.ErrorCode.POST_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostQueryService {

    private final PostRepository postRepository;

    public Post findActiveByIdElseThrow(UUID postId) {
        return findActiveById(postId)
                .orElseThrow(() -> new PostException(POST_NOT_FOUND));
    }

    @Transactional
    public PostDto getPostDtoById(UUID postId, UUID authorId) {
        Post post = findActiveByIdElseThrow(postId);

        // 비공개 게시글인 경우 작성자가 본인인지 확인
        if (post.isPrivate() && (authorId == null || !post.getAuthor().getId().equals(authorId))) {
            throw new PostException(CAN_NOT_ACCESS_POST);
        }

        return PostDto.fromPost(post);
    }

    private Optional<Post> findActiveById(UUID postId) {
        return postRepository.findActiveById(postId);
    }

    public Page<PostDto> getPublicPostDtoAll(PostKeyword postKeyword, Pageable pageable) {

        Page<Post> page = postRepository.findPublicPostAll(postKeyword, pageable);

        return PostDto.fromPagePost(page);
    }

    public Page<PostDto> getMyPostDtoAll(UUID authorId, PostKeyword postKeyword, Pageable pageable) {
        Page<Post> page = postRepository.findByAuthorId(authorId, postKeyword, pageable);

        return PostDto.fromPagePost(page);
    }

    public List<Post> findByIdIn(Collection<UUID> postIds) {
        return postRepository.findByIdIn(postIds);
    }
}
