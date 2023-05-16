package io.mhan.springjpatest2.posts.controller;

import io.mhan.springjpatest2.base.response.SuccessResponse;
import io.mhan.springjpatest2.posts.dto.PostDto;
import io.mhan.springjpatest2.posts.repository.vo.PostKeyword;
import io.mhan.springjpatest2.posts.repository.vo.PostKeywordType;
import io.mhan.springjpatest2.posts.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequiredArgsConstructor
public class AjaxPostController {

    private final PostService postService;

    @GetMapping("/ajax/posts")
    public SuccessResponse<Page<PostDto>> findAll(
            String keywordType, String keyword,
            @PageableDefault(sort = "created", direction = DESC) Pageable pageable) {

        PostKeyword postKeyword = PostKeyword.builder()
                .type(PostKeywordType.of(keywordType))
                .value(keyword)
                .build();

        Page<PostDto> page = postService.findAll(postKeyword, pageable);

        return SuccessResponse.ok("게시글 전체 조회에 성공하셨습니다.", page);
    }
}
