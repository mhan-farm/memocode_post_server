package io.mhan.springjpatest2.comments.controller.impl;

import io.mhan.springjpatest2.base.response.SuccessResponse;
import io.mhan.springjpatest2.comments.controller.ApiV1PostCommentController;
import io.mhan.springjpatest2.comments.dto.CommentDto;
import io.mhan.springjpatest2.comments.entity.Comment;
import io.mhan.springjpatest2.comments.request.CommentCreateRequest;
import io.mhan.springjpatest2.comments.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
@RequiredArgsConstructor
public class ApiV1PostCommentControllerImpl implements ApiV1PostCommentController {

    private final CommentService commentService;

    @GetMapping
    public SuccessResponse<Page<CommentDto>> getAllByPostId(
            @PathVariable UUID postId,
            @PageableDefault(sort = "created", direction = DESC) Pageable pageable) {

        Page<CommentDto> page = commentService.getActiveCommentDtoAllByPostId(postId, pageable);

        return SuccessResponse.ok("post " + postId + "번의 답변을 조회하였습니다.", page);
    }

    @PostMapping
    public SuccessResponse<CommentDto> createComment(
            @PathVariable UUID postId,
            @RequestBody CommentCreateRequest request,
            @AuthenticationPrincipal OAuth2IntrospectionAuthenticatedPrincipal principal
    ) {

        Comment comment = commentService.createAndSave(request.getContent(), postId, UUID.fromString(principal.getName()));

        CommentDto commentDto = commentService.getActiveCommentDtoById(comment.getId());

        return SuccessResponse.ok("comment를 생성하였습니다.", commentDto);
    }
}
