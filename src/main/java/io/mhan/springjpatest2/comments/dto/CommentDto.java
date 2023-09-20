package io.mhan.springjpatest2.comments.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.mhan.springjpatest2.comments.entity.Comment;
import io.mhan.springjpatest2.users.dto.AuthorDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    @JsonProperty("comment_id")
    private Long commentId;

    @JsonProperty("content")
    private String content;

    @JsonProperty("author")
    private AuthorDto authorDto;

    @JsonProperty("created")
    private LocalDateTime created;

    @JsonProperty("updated")
    private LocalDateTime updated;

    public static CommentDto fromComment(Comment comment) {

        AuthorDto authorDto = AuthorDto.fromAuthor(comment.getAuthor());

        CommentDto commentDto = CommentDto.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .authorDto(authorDto)
                .created(comment.getCreated())
                .updated(comment.getUpdated())
                .build();

        return commentDto;
    }

    public static List<CommentDto> fromComments(List<Comment> comments) {

        List<CommentDto> commentDtos = comments.stream()
                .map(CommentDto::fromComment)
                .toList();

        return commentDtos;
    }

    public static Page<CommentDto> fromPageComments(Page<Comment> page) {

        List<CommentDto> commentDtos = CommentDto.fromComments(page.getContent());

        return PageableExecutionUtils.getPage(commentDtos, page.getPageable(), page::getTotalElements);
    }
}
