package io.mhan.springjpatest2.posts.controller;

import io.mhan.springjpatest2.base.response.SuccessResponse;
import io.mhan.springjpatest2.posts.dto.PostDto;
import io.mhan.springjpatest2.posts.request.PostCreateRequest;
import io.mhan.springjpatest2.posts.request.PostUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Tag(name = "ApiV1PostController", description = "POST 사용자 CRUD")
public interface ApiV1PostController {

    @Operation(summary = "전체 조회")
    SuccessResponse<Page<PostDto>> getAll(String keywordType, String keyword, Pageable pageable);

    @Operation(summary = "생성")
    SuccessResponse<PostDto> newPost(PostCreateRequest request);

    @Operation(summary = "업데이트")
    SuccessResponse<PostDto> updatePost(Long postId, PostUpdateRequest request);

    @Operation(summary = "단건 조회")
    SuccessResponse<PostDto> getPost(Long postId, PostUpdateRequest request);

    @Operation(summary = "내 post 전체 조회")
    SuccessResponse<Page<PostDto>> getMyPosts(String keywordType, String keyword, Pageable pageable);

    @Operation(summary = "소프트 삭제")
    SuccessResponse<Void> softDeleteMyPost(Long postId);
}
