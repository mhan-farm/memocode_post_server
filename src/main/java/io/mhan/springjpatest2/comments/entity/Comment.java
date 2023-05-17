package io.mhan.springjpatest2.comments.entity;

import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.users.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = LAZY)
    @Setter
    private Post post;

    @ManyToOne(fetch = LAZY)
    private User user;

    private LocalDateTime created;

    private LocalDateTime updated;

    public static Comment create(String content, User user) {

        Assert.notNull(content, "content은 null이 될 수 없습니다.");
        Assert.notNull(user, "user은 null이 될 수 없습니다.");

        Comment comment = Comment.builder()
                .content(content)
                .user(user)
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .build();

        return comment;
    }

    public void insertPost(Post post) {

        if (this.id != null) {
            throw new IllegalArgumentException();
        }

        this.post = post;
    }
}
