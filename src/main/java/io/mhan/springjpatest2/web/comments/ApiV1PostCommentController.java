package io.mhan.springjpatest2.web.comments;

import io.mhan.springjpatest2.comments.dto.CommentDto;
import io.mhan.springjpatest2.comments.request.CommentCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Tag(name = "ApiV1PostCommentController", description = "POST COMMENT 사용자 CRUD")
public interface ApiV1PostCommentController {

    @Operation(summary = "전체 조회")
    ResponseEntity<Page<CommentDto>> getAllByPostId(UUID postId, Pageable pageable);

    @Operation(summary = "생성")
    ResponseEntity<CommentDto> createComment(UUID postId, CommentCreateRequest request);
}
