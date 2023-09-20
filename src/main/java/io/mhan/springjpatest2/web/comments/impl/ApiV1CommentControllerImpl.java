package io.mhan.springjpatest2.web.comments.impl;

import io.mhan.springjpatest2.base.annotation.IsUserOrAdmin;
import io.mhan.springjpatest2.base.requestscope.SecurityUser;
import io.mhan.springjpatest2.comments.dto.CommentDto;
import io.mhan.springjpatest2.comments.request.CommentUpdateRequest;
import io.mhan.springjpatest2.comments.service.CommentService;
import io.mhan.springjpatest2.web.comments.ApiV1CommentController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class ApiV1CommentControllerImpl implements ApiV1CommentController {

    private final CommentService commentService;
    private final SecurityUser securityUser;

    @IsUserOrAdmin
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long commentId,
                                                    @RequestBody CommentUpdateRequest request) {

        commentService.updateMyComment(request.getContent(), commentId, securityUser.getId());
        CommentDto commentDto = commentService.getActiveCommentDtoById(commentId);

        return ResponseEntity.ok(commentDto);
    }

    @IsUserOrAdmin
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> softDeleteComment(@PathVariable Long commentId) {

        commentService.softDeleteMyComment(commentId, securityUser.getId());

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto> getComment(
            @PathVariable Long commentId) {

        CommentDto commentDto = commentService.getActiveCommentDtoById(commentId);

        return ResponseEntity.ok(commentDto);
    }
}
