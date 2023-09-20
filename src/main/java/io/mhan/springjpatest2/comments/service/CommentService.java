package io.mhan.springjpatest2.comments.service;

import io.mhan.springjpatest2.comments.dto.CommentDto;
import io.mhan.springjpatest2.comments.entity.Comment;
import io.mhan.springjpatest2.comments.exception.CommentException;
import io.mhan.springjpatest2.comments.repository.CommentRepository;
import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.posts.service.PostQueryService;
import io.mhan.springjpatest2.users.entity.Author;
import io.mhan.springjpatest2.users.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static io.mhan.springjpatest2.base.exception.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final AuthorService userService;
    private final PostQueryService postService;

    @Transactional
    public Comment createAndSave(String content, UUID postId, UUID authorId) {

        Author user = userService.findActiveByIdElseThrow(authorId);
        Post post = postService.findActiveByIdElseThrow(postId);

        Comment comment = Comment.create(content, user, post);

        return commentRepository.save(comment);
    }

    public Page<CommentDto> getActiveCommentDtoAllByPostId(UUID postId, Pageable pageable) {

        Page<Comment> page = commentRepository.findActiveAllByPostId(postId, pageable);

        return CommentDto.fromPageComments(page);
    }

    @Transactional
    public void updateMyComment(String content, Long commentId, UUID authorId) {

        Comment comment = findActiveByIdElseThrow(commentId);

        if (!comment.getAuthor().getId().equals(authorId)) {
            throw new CommentException(COMMENT_NOT_MODIFY);
        }

        comment.updateContent(content);
    }

    @Transactional
    public void softDeleteMyComment(Long commentId, UUID authorId) {

        Comment comment = findActiveByIdElseThrow(commentId);

        if (!comment.getAuthor().getId().equals(authorId)) {
            throw new CommentException(COMMENT_NOT_DELETE);
        }

        comment.softDelete();
    }

    public Comment findActiveByIdElseThrow(Long commentId) {
        Comment comment = commentRepository.findActiveById(commentId)
                .orElseThrow(() -> new CommentException(COMMENT_NOT_FOUND));

        return comment;
    }

    public CommentDto getActiveCommentDtoById(Long commentId) {
        Comment comment = findActiveByIdElseThrow(commentId);

        return CommentDto.fromComment(comment);
    }
}
