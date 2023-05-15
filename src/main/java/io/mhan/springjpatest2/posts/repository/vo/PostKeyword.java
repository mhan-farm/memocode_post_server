package io.mhan.springjpatest2.posts.repository.vo;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostKeyword {
    private PostKeywordType type;
    private String value;
}
