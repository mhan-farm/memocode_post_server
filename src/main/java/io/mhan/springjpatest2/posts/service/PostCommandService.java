package io.mhan.springjpatest2.posts.service;

import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.posts.exception.PostException;
import io.mhan.springjpatest2.posts.repository.PostRepository;
import io.mhan.springjpatest2.posts.request.PostCreateRequest;
import io.mhan.springjpatest2.posts.request.PostUpdateRequest;
import io.mhan.springjpatest2.tags.entity.Tag;
import io.mhan.springjpatest2.tags.service.TagService;
import io.mhan.springjpatest2.users.entity.Author;
import io.mhan.springjpatest2.users.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Set;
import java.util.UUID;

import static io.mhan.springjpatest2.base.exception.ErrorCode.POST_NOT_DELETE;
import static io.mhan.springjpatest2.base.exception.ErrorCode.POST_NOT_MODIFY;

@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class PostCommandService {

    private final PostRepository postRepository;

    private final PostQueryService postService;

    private final AuthorService authorService;

    private final TagService tagService;

    /**
     * 게시물 등록
     */
    public UUID register(UUID authorId, @Valid PostCreateRequest request) {

        // author 는 무조건 있어야 한다는 전제, 만약 없을 시 exception 발생
        Author author = authorService.findActiveByIdElseThrow(authorId);

        // 게시글 생성
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .author(author)
                .isPrivate(request.getIsPrivate())
                .build();

        // TODO 이벤트를 발생시키는 방법으로 전환 고민
        // 태그 조회 및 저장
        Set<Tag> tags = tagService.findOrCreateAndSaveTagsByStringNames(request.getTags());
        post.addTags(tags);

        // 게시글 저장
        Post savedPost = postRepository.save(post);

        return savedPost.getId();
    }

    /**
     * 게시물 수정(PATCH)
     */
    public void update(UUID postId, UUID authorId, @Valid PostUpdateRequest request) {

        // author 는 무조건 있어야 한다는 전제, 만약 없을 시 exception 발생
        Author author = authorService.findActiveByIdElseThrow(authorId);
        // post 는 무조건 있어야 한다는 전제, 만약 없을 시 exception 발생
        Post post = postService.findActiveByIdElseThrow(postId);

        // 자신의 게시물인지 확인, 아니면 exception
        if (!author.getId().equals(post.getAuthor().getId())) {
            throw new PostException(POST_NOT_MODIFY);
        }

        // title 존재시 수정
        if (request.getTitle() != null && !request.getTitle().isBlank()) {
            post.updateTitle(request.getTitle());
        }

        // content 존재시 수정
        if (request.getContent() != null && !request.getContent().isBlank()) {
            post.updateContent(request.getContent());
        }

        // tag 존재시 수정
        if (request.getTags() != null) {
            Set<Tag> tags = tagService.findOrCreateAndSaveTagsByStringNames(request.getTags());
            post.updateTags(tags);
        }
    }

    public void softDeleteMyPost(UUID postId, UUID authorId) {
        // author 는 무조건 있어야 한다는 전제, 만약 없을 시 exception 발생
        Author author = authorService.findActiveByIdElseThrow(authorId);
        // post 는 무조건 있어야 한다는 전제, 만약 없을 시 exception 발생
        Post post = postService.findActiveByIdElseThrow(postId);

        // 자신의 게시물인지 확인, 아니면 exception
        if (!author.getId().equals(post.getAuthor().getId())) {
            throw new PostException(POST_NOT_DELETE);
        }

        post.softDelete();
    }
}
