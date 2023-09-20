package io.mhan.springjpatest2.web.comments.impl;

import io.mhan.springjpatest2.base.annotation.IsUserOrAdmin;
import io.mhan.springjpatest2.base.requestscope.SecurityUser;
import io.mhan.springjpatest2.comments.dto.CommentDto;
import io.mhan.springjpatest2.comments.entity.Comment;
import io.mhan.springjpatest2.comments.request.CommentCreateRequest;
import io.mhan.springjpatest2.comments.service.CommentService;
import io.mhan.springjpatest2.web.comments.ApiV1PostCommentController;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
@RequiredArgsConstructor
public class ApiV1PostCommentControllerImpl implements ApiV1PostCommentController {

    private final CommentService commentService;
    private final SecurityUser securityUser;

    @GetMapping
    public ResponseEntity<Page<CommentDto>> getAllByPostId(
            @PathVariable UUID postId,
            @PageableDefault(sort = "created", direction = DESC) Pageable pageable) {

        Page<CommentDto> page = commentService.getActiveCommentDtoAllByPostId(postId, pageable);

        return ResponseEntity.ok(page);
    }

    @IsUserOrAdmin
    @PostMapping
    public ResponseEntity<CommentDto> createComment(@PathVariable UUID postId,
                                                    @RequestBody CommentCreateRequest request) {

        Comment comment = commentService.createAndSave(request.getContent(), postId, securityUser.getId());

        CommentDto commentDto = commentService.getActiveCommentDtoById(comment.getId());

        return ResponseEntity.ok(commentDto);
    }
}
