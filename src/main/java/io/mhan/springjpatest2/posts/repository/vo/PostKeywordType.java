package io.mhan.springjpatest2.posts.repository.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostKeywordType {
    TITLE_CONTENT("title,content"),
    TITLE("title"),
    AUTHOR("author");

    private final String name;

    public static PostKeywordType of(String name) {
        if (name == null || name.isBlank()) {
            return TITLE_CONTENT;
        }

        PostKeywordType postKeywordType = TITLE_CONTENT;
        for (PostKeywordType postKeyword : PostKeywordType.values()) {
            if (postKeyword.name.equals(name)) {
                postKeywordType = postKeyword;
            }
        }

        return postKeywordType;
    }
}
