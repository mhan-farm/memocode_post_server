package io.mhan.springjpatest2.web.posts;

import io.mhan.springjpatest2.posts.dto.PostDto;
import io.mhan.springjpatest2.posts.request.PostCreateRequest;
import io.mhan.springjpatest2.posts.request.PostUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Tag(name = "ApiV1PostCommandController", description = "POST 생성, 변경, 삭제")
public interface ApiV1PostCommandController {
    @Operation(summary = "생성")
    ResponseEntity<PostDto> newPost(PostCreateRequest request);

    @Operation(summary = "업데이트",
            description = "title, content, tags를 업데이트할 수 있으며 모든 필드를 포함하지 않고 변경하고 싶은 필드만 추가해서 작성하면 됨")
    ResponseEntity<PostDto> updatePost(UUID postId, PostUpdateRequest request);

    @Operation(summary = "소프트 삭제")
    ResponseEntity<Void> softDeleteMyPost(UUID postId);
}
