package io.mhan.springjpatest2.comments.controller.impl;

import io.mhan.springjpatest2.base.response.SuccessResponse;
import io.mhan.springjpatest2.comments.controller.ApiV1CommentController;
import io.mhan.springjpatest2.comments.dto.CommentDto;
import io.mhan.springjpatest2.comments.request.CommentUpdateRequest;
import io.mhan.springjpatest2.comments.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class ApiV1CommentControllerImpl implements ApiV1CommentController {

    private final CommentService commentService;

    @PostMapping("/{commentId}")
    public SuccessResponse<CommentDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentUpdateRequest request,
            @AuthenticationPrincipal OAuth2IntrospectionAuthenticatedPrincipal principal
    ) {

        commentService.updateMyComment(request.getContent(), commentId, UUID.fromString(principal.getName()));
        CommentDto commentDto = commentService.getActiveCommentDtoById(commentId);

        return SuccessResponse.ok("comment " + commentId + " 번의 답변을 수정하였습니다.", commentDto);
    }

    @DeleteMapping("/{commentId}")
    public SuccessResponse<Void> softDeleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal OAuth2IntrospectionAuthenticatedPrincipal principal
    ) {

        commentService.softDeleteMyComment(commentId, UUID.fromString(principal.getName()));

        return SuccessResponse.noContent("comment " + commentId + " 번의 답변을 삭제하였습니다.");
    }

    @GetMapping("/{commentId}")
    public SuccessResponse<CommentDto> getComment(
            @PathVariable Long commentId) {

        CommentDto commentDto = commentService.getActiveCommentDtoById(commentId);

        return SuccessResponse.ok("comment " + commentId + "번의 답변을 조회하였습니다.", commentDto);
    }
}
