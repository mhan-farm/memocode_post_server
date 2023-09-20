package io.mhan.springjpatest2.users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.mhan.springjpatest2.users.entity.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("name")
    private String name;

    public static AuthorDto fromAuthor(Author author) {
        AuthorDto authorDto = AuthorDto.builder()
                .nickname(author.getNickname())
                .name(author.getName())
                .build();

        return authorDto;
    }
}
