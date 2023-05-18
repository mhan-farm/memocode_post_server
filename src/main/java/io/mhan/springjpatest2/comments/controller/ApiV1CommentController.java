package io.mhan.springjpatest2.comments.controller;

import io.mhan.springjpatest2.base.response.SuccessResponse;
import io.mhan.springjpatest2.comments.dto.CommentDto;
import io.mhan.springjpatest2.comments.request.CommentUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "ApiV1CommentController", description = "COMMENT 사용자 CRUD")
public interface ApiV1CommentController {

    @Operation(summary = "전체 조회")
    SuccessResponse<CommentDto> updateComment(Long commentId, CommentUpdateRequest request);

    @Operation(summary = "삭제")
    SuccessResponse<Void> deleteComment(Long commentId);

    @Operation(summary = "단건 조회")
    SuccessResponse<CommentDto> getComment(Long commentId);
}
