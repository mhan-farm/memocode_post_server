package io.mhan.springjpatest2.posts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.users.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @JsonProperty("created")
    private LocalDateTime created;

    @JsonProperty("updated")
    private LocalDateTime updated;

    @JsonProperty("comment_count")
    private long commentCount;

    @JsonProperty("like_count")
    private long likeCount;

    @JsonProperty("views")
    private long views;

    public static PostDto fromPost(Post post) {
        UserDto userDto = UserDto.fromUser(post.getAuthor());

        PostDto postDto = PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(userDto)
                .created(post.getCreated())
                .commentCount(post.getCommentCount())
                .likeCount(post.getLikeCount())
                .views(post.getViews())
                .build();

        return postDto;
    }

    public static List<PostDto> fromPosts(List<Post> posts) {
        return posts.stream()
                .map(PostDto::fromPost)
                .collect(Collectors.toList());
    }

    public static Page<PostDto> fromPagePosts(Page<Post> page) {
        List<Post> posts = page.getContent();

        List<PostDto> postDtos = PostDto.fromPosts(posts);

        return PageableExecutionUtils.getPage(postDtos, page.getPageable(), page::getTotalElements);
    }
}
