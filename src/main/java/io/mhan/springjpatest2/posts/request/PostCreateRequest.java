package io.mhan.springjpatest2.posts.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequest {

    @NotBlank(message = "제목을 입력해주세요")
    @Size(max = 255, message = "제목의 최대 길이는 255자입니다")
    private String title;

    // TODO 나중에 길이제한을 고려해보아야 함
    @NotBlank(message = "내용을 입력해주세요")
    private String content;

    // 태그 필수 아님
    @Pattern(regexp = "^[^,]+(,[^,]+)*$", message = "tags는 쉼표로만 구분되어야 합니다.")
    private String tags;

    private Boolean isPrivate = Boolean.FALSE;
}
