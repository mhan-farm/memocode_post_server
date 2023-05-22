package io.mhan.springjpatest2.comments.entity;

import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.users.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@Table(name = "comments")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", nullable = false, foreignKey = @ForeignKey(name = "FK_posts_comments"))
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_users_comments"))
    private User user;

    private LocalDateTime created;

    private LocalDateTime updated;

    private boolean isDeleted;

    private LocalDateTime deleted;

    public static Comment create(String content, User user, Post post) {

        Assert.notNull(content, "content은 null이 될 수 없습니다.");
        Assert.notNull(user, "user은 null이 될 수 없습니다.");
        Assert.notNull(post, "post는 null이 될 수 없습니다.");

        Comment comment = Comment.builder()
                .content(content)
                .user(user)
                .post(post)
                .isDeleted(false)
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .build();

        post.increaseCommentCount();

        return comment;
    }

    public void updateContent(String content) {

        Assert.notNull(content, "content는 null이 될 수 없습니다.");

        this.content = content;
        this.updated = LocalDateTime.now();
    }

    public void softDelete() {
        this.isDeleted = true;
        this.deleted = LocalDateTime.now();
    }
}
