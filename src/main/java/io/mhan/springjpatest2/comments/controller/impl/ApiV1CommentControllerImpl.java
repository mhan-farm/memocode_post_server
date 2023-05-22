package io.mhan.springjpatest2.comments.controller.impl;

import io.mhan.springjpatest2.base.response.SuccessResponse;
import io.mhan.springjpatest2.comments.controller.ApiV1CommentController;
import io.mhan.springjpatest2.comments.dto.CommentDto;
import io.mhan.springjpatest2.comments.request.CommentUpdateRequest;
import io.mhan.springjpatest2.comments.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static io.mhan.springjpatest2.base.init.InitData.USER_ID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class ApiV1CommentControllerImpl implements ApiV1CommentController {

    private final CommentService commentService;

    @PostMapping("/{commentId}")
    public SuccessResponse<CommentDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentUpdateRequest request) {

        commentService.updateMyComment(request.getContent(), commentId, USER_ID);
        CommentDto commentDto = commentService.getActiveCommentDtoById(commentId);

        return SuccessResponse.ok("comment " + commentId + " 번의 답변을 수정하였습니다.", commentDto);
    }

    @DeleteMapping("/{commentId}")
    public SuccessResponse<Void> softDeleteComment(
            @PathVariable Long commentId) {

        commentService.softDeleteMyComment(commentId, USER_ID);

        return SuccessResponse.noContent("comment " + commentId + " 번의 답변을 삭제하였습니다.");
    }

    @GetMapping("/{commentId}")
    public SuccessResponse<CommentDto> getComment(
            @PathVariable Long commentId) {

        CommentDto commentDto = commentService.getActiveCommentDtoById(commentId);

        return SuccessResponse.ok("comment " + commentId + "번의 답변을 조회하였습니다.", commentDto);
    }
}
