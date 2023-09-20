package io.mhan.springjpatest2.web.posts;

import io.mhan.springjpatest2.posts.dto.PostDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Tag(name = "ApiV1PostQueryController", description = "POST READ")
public interface ApiV1PostQueryController {

    @Operation(summary = "공개된 게시글 전체 조회")
    ResponseEntity<Page<PostDto>> getPublicPostAll(String keywordType, String keyword, Pageable pageable);

    @Operation(summary = "단건 조회")
    ResponseEntity<PostDto> getPost(UUID postId);

    @Operation(summary = "나의 post 전체 조회", description = "내가 쓴 게시글 전체 조회")
    ResponseEntity<Page<PostDto>> getMyPosts(String keywordType, String keyword, Pageable pageable);
}
