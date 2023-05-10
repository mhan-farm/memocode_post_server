package io.mhan.springjpatest2.posts.repository.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Keyword {
    private KeywordType type;
    private String value;
}
