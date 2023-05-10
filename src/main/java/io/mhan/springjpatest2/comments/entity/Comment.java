package io.mhan.springjpatest2.comments.entity;

import io.mhan.springjpatest2.posts.entity.Post;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = LAZY)
    @Setter
    private Post post;

    private LocalDateTime created;

    private LocalDateTime updated;

    public static Comment create(String content) {
        Assert.notNull(content, "content은 null이 될 수 없습니다.");

        Comment comment = Comment.builder()
                .content(content)
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .build();

        return comment;
    }
}
