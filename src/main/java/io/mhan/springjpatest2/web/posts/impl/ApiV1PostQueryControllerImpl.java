package io.mhan.springjpatest2.web.posts.impl;

import io.mhan.springjpatest2.base.annotation.IsUserOrAdmin;
import io.mhan.springjpatest2.base.requestscope.SecurityUser;
import io.mhan.springjpatest2.posts.dto.PostDto;
import io.mhan.springjpatest2.posts.repository.vo.PostKeyword;
import io.mhan.springjpatest2.posts.repository.vo.PostKeywordType;
import io.mhan.springjpatest2.posts.service.PostQueryService;
import io.mhan.springjpatest2.web.posts.ApiV1PostQueryController;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class ApiV1PostQueryControllerImpl implements ApiV1PostQueryController {

    private final PostQueryService postService;
    private final SecurityUser securityUser;

    // 공개된 post 리스트 조회
    @GetMapping
    public ResponseEntity<Page<PostDto>> getPublicPostAll(
            @RequestParam(required = false, value = "title,content") String keywordType,
            @RequestParam(required = false, value = "") String keyword,
            @PageableDefault(sort = "created", direction = DESC) Pageable pageable) {

        PostKeyword postKeyword = PostKeyword.builder()
                .type(PostKeywordType.of(keywordType))
                .value(keyword)
                .build();

        Page<PostDto> page = postService.getPublicPostDtoAll(postKeyword, pageable);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable UUID postId) {

        PostDto postDto = postService.getPostDtoById(postId, securityUser.getId());

        return ResponseEntity.ok(postDto);
    }

    // 내가 쓴 게시글 전체 조회
    @IsUserOrAdmin
    @GetMapping("/my")
    public ResponseEntity<Page<PostDto>> getMyPosts(
            @RequestParam(required = false, value = "title,content") String keywordType,
            @RequestParam(required = false, value = "") String keyword,
            @PageableDefault(sort = "created", direction = DESC) Pageable pageable) {

        PostKeyword postKeyword = PostKeyword.builder()
                .type(PostKeywordType.of(keywordType))
                .value(keyword)
                .build();

        Page<PostDto> postDtos = postService.getMyPostDtoAll(securityUser.getId(), postKeyword, pageable);

        return ResponseEntity.ok(postDtos);
    }
}
