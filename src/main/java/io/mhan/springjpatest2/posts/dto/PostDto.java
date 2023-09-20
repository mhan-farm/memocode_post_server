package io.mhan.springjpatest2.posts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.users.dto.AuthorDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;

    @JsonProperty("commentCount")
    private long commentCount;

    @JsonProperty("likeCount")
    private long likeCount;

    @JsonProperty("views")
    private long views;

    @JsonProperty("tags")
    private String tags;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    @JsonProperty("author")
    private AuthorDto authorDto;

    public static PostDto fromPost(Post post) {

        AuthorDto authorDto = AuthorDto.fromAuthor(post.getAuthor());
        String stringTags = post.getTags().stream()
                .map(postTag -> postTag.getTag().getName())
                .collect(Collectors.joining(","));

        PostDto postDto = PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .tags(stringTags)
                .commentCount(post.getCommentCount())
                .likeCount(post.getLikeCount())
                .views(post.getViews())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .authorDto(authorDto)
                .build();

        return postDto;
    }

    public static List<PostDto> fromPosts(List<Post> posts) {

        List<PostDto> postDtos = posts.stream()
                .map(PostDto::fromPost)
                .toList();
        return postDtos;
    }

    public static Page<PostDto> fromPagePost(Page<Post> page) {
        List<PostDto> postDtos = PostDto.fromPosts(page.getContent());

        return PageableExecutionUtils.getPage(postDtos, page.getPageable(), page::getTotalElements);
    }
}
