package io.mhan.springjpatest2.posts.service;

import io.mhan.springjpatest2.posts.dto.PostDto;
import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.posts.exception.PostException;
import io.mhan.springjpatest2.posts.repository.PostRepository;
import io.mhan.springjpatest2.posts.repository.vo.PostKeyword;
import io.mhan.springjpatest2.posts.request.PostCreateRequest;
import io.mhan.springjpatest2.tags.entity.Tag;
import io.mhan.springjpatest2.tags.service.TagService;
import io.mhan.springjpatest2.users.entity.User;
import io.mhan.springjpatest2.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
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
    public Long registerPost(Long authorId, PostCreateRequest request) {

        String title = request.getTitle();
        String content = request.getContent();
        String tagNames = request.getTags();
        Long parentId = request.getParentId();

        Assert.notNull(authorId, "authorId은 null일 수 없습니다.");
        Assert.notNull(title, "title은 null일 수 없습니다.");
        Assert.notNull(content, "content은 null일 수 없습니다.");
        Assert.notNull(tagNames, "tagNames은 null일 수 없습니다.");

        if (parentId != null && parentId <= 0) {
            throw new IllegalArgumentException("parentId가 잘못된 인수가 포함되어 있습니다.");
        }

        if (parentId != null) {
            Post parentPost = findActiveByIdElseThrow(parentId);

            User author = parentPost.getAuthor();
            if (!author.getId().equals(authorId)) {
                throw new PostException(CAN_NOT_ACCESS_POST);
            }

            Post savedPost = createAndSave(title, content, tagNames, author);

            parentPost.addChildPosts(savedPost);

            return savedPost.getId();
        }

        User author = userService.findActiveByIdElseThrow(authorId);

        Post savedPost = createAndSave(title, content, tagNames, author);

        long postCount = postRepository.countActiveAllByAuthorIdAndParentPostIsNull(authorId);
        savedPost.updateSequence(postCount + 1);

        return savedPost.getId();
    }

    private Post createAndSave(String title, String content, String tagNames, User author) {
        Post post = Post.create(title, content, author);

        Set<Tag> tags = tagService.findOrCreateAndSaveTagsByStringNames(tagNames);
        post.addTags(tags);

        Post savedPost = postRepository.save(post);
        return savedPost;
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

    @Transactional
    public void rearrangePostSequence(Long postId, long wantedSequence, Long authorId) {
        if (wantedSequence <= 0) {
            throw new PostException(INCORRECT_SEQUENCE_NUMBER_POST);
        }

        Post post = findActiveByIdElseThrow(postId);

        User author = post.getAuthor();
        if (!author.getId().equals(authorId)) {
            throw new PostException(CAN_NOT_ACCESS_POST);
        }

        Post parentPost = post.getParentPost();
        long currentSequence = post.getSequence();

        if (wantedSequence == post.getSequence()) {
            throw new PostException(ALREADY_EQUAL_SEQUENCE_POST);
        }

        long postCount = postRepository.countActiveAllByAuthorIdAndParentPost(authorId, parentPost);
        if (wantedSequence > postCount) {
            throw new PostException(INCORRECT_SEQUENCE_NUMBER_POST);
        }

        if (currentSequence < wantedSequence) {
            List<Post> subsequentPosts =
                    postRepository.findActiveAllByAuthorIdAndParentPostBetweenSequenceOrderBySequenceASC(
                            authorId, parentPost, currentSequence + 1, wantedSequence);
            subsequentPosts
                    .forEach(subsequentPost -> subsequentPost.updateSequence(subsequentPost.getSequence() - 1));
            post.updateSequence(wantedSequence);
            return;
        }

        if (currentSequence > wantedSequence) {
            List<Post> subsequentPosts =
                    postRepository.findActiveAllByAuthorIdAndParentPostBetweenSequenceOrderBySequenceASC(
                            authorId, parentPost, wantedSequence, currentSequence - 1);
            subsequentPosts
                    .forEach(subsequentPost -> subsequentPost.updateSequence(subsequentPost.getSequence() + 1));
            post.updateSequence(wantedSequence);
            return;
        }

        throw new PostException(INTERNAL_ERROR);
    }

    @Transactional
    public List<Post> findByAuthorIdAndParentPostId(Long authorId, Long parentPostId) {
        Post parentPost = findActiveByIdElseThrow(parentPostId);

        User author = parentPost.getAuthor();
        if (!author.getId().equals(authorId)) {
            throw new PostException(CAN_NOT_ACCESS_POST);
        }

        return postRepository.findActiveAllByAuthorIdAndParentPostOrderBySequenceASC(authorId, parentPost);
    }
}
