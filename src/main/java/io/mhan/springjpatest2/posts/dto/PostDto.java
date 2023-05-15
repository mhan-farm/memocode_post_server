package io.mhan.springjpatest2.posts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.users.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    @JsonProperty("post_id")
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;

    @JsonProperty("author")
    private UserDto author;

    @JsonProperty("comment_count")
    private long commentCount;

    @JsonProperty("like_count")
    private long likeCount;

    @JsonProperty("views")
    private long views;

    @JsonProperty("created")
    private LocalDateTime created;

    @JsonProperty("updated")
    private LocalDateTime updated;

    public static PostDto fromPost(Post post) {

        UserDto author = UserDto.fromUser(post.getAuthor());

        PostDto postDto = PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(author)
                .commentCount(post.getCommentCount())
                .likeCount(post.getLikeCount())
                .views(post.getViews())
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
