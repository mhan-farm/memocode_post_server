package io.mhan.springjpatest2.comments.controller;

import io.mhan.springjpatest2.base.response.SuccessResponse;
import io.mhan.springjpatest2.comments.dto.CommentDto;
import io.mhan.springjpatest2.comments.request.CommentCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;

import java.util.UUID;

@Tag(name = "ApiV1PostCommentController", description = "POST COMMENT 사용자 CRUD")
public interface ApiV1PostCommentController {

    @Operation(summary = "전체 조회")
    SuccessResponse<Page<CommentDto>> getAllByPostId(UUID postId, Pageable pageable);

    @Operation(summary = "생성")
    SuccessResponse<CommentDto> createComment(UUID postId, CommentCreateRequest request, OAuth2IntrospectionAuthenticatedPrincipal principal);
}
