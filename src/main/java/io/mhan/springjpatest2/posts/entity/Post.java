package io.mhan.springjpatest2.posts.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title;

    private String content;

    private LocalDateTime created;

    private LocalDateTime updated;

    public static Post create(String title, String content) {

        Assert.notNull(title, "title은 null이 될 수 없습니다.");
        Assert.notNull(content, "title은 null이 될 수 없습니다.");

        Post post = Post.builder()
                .title(title)
                .content(content)
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .build();

        return post;
    }
}
