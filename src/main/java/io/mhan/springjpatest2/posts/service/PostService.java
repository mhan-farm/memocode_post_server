package io.mhan.springjpatest2.posts.service;

import io.mhan.springjpatest2.posts.dto.PostDto;
import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.posts.exception.PostException;
import io.mhan.springjpatest2.posts.repository.PostRepository;
import io.mhan.springjpatest2.posts.repository.vo.PostKeyword;
import io.mhan.springjpatest2.tags.entity.Tag;
import io.mhan.springjpatest2.tags.service.TagService;
import io.mhan.springjpatest2.users.entity.User;
import io.mhan.springjpatest2.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static io.mhan.springjpatest2.base.exception.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final UserService userService;

    private final TagService tagService;

    public Post findActiveByIdElseThrow(Long postId) {
        return findActiveById(postId)
                .orElseThrow(() -> new PostException(POST_NOT_FOUND));
    }

    @Transactional
    public PostDto getPostDtoById(Long postId) {
        Post post = findActiveByIdElseThrow(postId);

        return PostDto.fromPost(post);
    }

    @Transactional
    public PostDto getPostDtoByIdAndIncreaseViews(Long postId) {

        increaseViews(postId);

        PostDto postDto = getPostDtoById(postId);

        return postDto;
    }

    @Transactional
    public void increaseViews(Long postId) {
        Post post = findActiveByIdElseThrow(postId);
        post.increaseViews();
    }

    private Optional<Post> findActiveById(Long id) {
        return postRepository.findActiveById(id);
    }

    public Page<PostDto> getPostDtoAll(PostKeyword postKeyword, Pageable pageable) {

        Page<Post> page = postRepository.findAll(postKeyword, pageable);

        return PostDto.fromPagePost(page);
    }

    @Transactional
    public Long createAndSave(String title, String content, Long authorId, String tagNames) {

        User author = userService.findActiveByIdElseThrow(authorId);

        Post post = Post.create(title, content, author);

        Post savedPost = postRepository.save(post);

        Set<Tag> tags = tagService.findOrCreateAndSaveTagsByStringNames(tagNames);

        savedPost.addTags(tags);

        return savedPost.getId();
    }

    @Transactional
    public void update(String title, String content, Long postId, Long authorId, String tagNames) {
        User author = userService.findActiveByIdElseThrow(authorId);
        Post post = findActiveByIdElseThrow(postId);

        if (!author.getId().equals(post.getAuthor().getId())) {
            throw new PostException(POST_NOT_MODIFY);
        }

        post.updateTitleAndContent(title, content);

        Set<Tag> tags = tagService.findOrCreateAndSaveTagsByStringNames(tagNames);

        post.updateTags(tags);
    }

    public Page<PostDto> getMyPostDtoAll(Long authorId, PostKeyword postKeyword, Pageable pageable) {
        Page<Post> page = postRepository.findByAuthorId(authorId, postKeyword, pageable);

        return PostDto.fromPagePost(page);
    }

    @Transactional
    public void softDeleteMyPost(Long postId, Long authorId) {
        User author = userService.findActiveByIdElseThrow(authorId);
        Post post = findActiveByIdElseThrow(postId);

        if (!author.getId().equals(post.getAuthor().getId())) {
            throw new PostException(POST_NOT_DELETE);
        }

        post.softDelete();
    }
}
