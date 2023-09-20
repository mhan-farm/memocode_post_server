package io.mhan.springjpatest2.web.comments;

import io.mhan.springjpatest2.comments.dto.CommentDto;
import io.mhan.springjpatest2.comments.request.CommentUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "ApiV1CommentController", description = "COMMENT 사용자 CRUD")
public interface ApiV1CommentController {

    @Operation(summary = "단건 수정")
    ResponseEntity<CommentDto> updateComment(
            Long commentId,
            CommentUpdateRequest request
    );

    @Operation(summary = "소프트 삭제")
    ResponseEntity<Void> softDeleteComment(
            Long commentId
    );

    @Operation(summary = "단건 조회")
    ResponseEntity<CommentDto> getComment(Long commentId);
}
