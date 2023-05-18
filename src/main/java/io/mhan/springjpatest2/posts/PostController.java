package io.mhan.springjpatest2.posts;

import io.mhan.springjpatest2.base.SuccessResponse;
import io.mhan.springjpatest2.posts.dto.PostDto;
import io.mhan.springjpatest2.posts.repository.vo.PostKeyword;
import io.mhan.springjpatest2.posts.repository.vo.PostKeywordType;
import io.mhan.springjpatest2.posts.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public SuccessResponse<Page<PostDto>> getAll(
            String keyword, String keywordType,
            @PageableDefault(size = 20, page = 0, sort = "created", direction = DESC) Pageable pageable) {

        PostKeyword postKeyword = PostKeyword.builder()
                .value(keyword)
                .type(PostKeywordType.of(keywordType))
                .build();

        Page<PostDto> page = postService.getPostDtoAll(postKeyword, pageable);

        return SuccessResponse.ok("post 전체 조회에 성공하셨습니다.", page);
    }
}
