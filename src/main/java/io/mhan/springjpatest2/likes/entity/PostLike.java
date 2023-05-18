package io.mhan.springjpatest2.likes.entity;

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
@Table(name = "post_like")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class PostLike {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "FK_posts_postLike"))
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_users_postLike"))
    private User user;

    private LocalDateTime created;

    private LocalDateTime updated;

    public static PostLike create(Post post, User user) {

        Assert.notNull(post, "post는 null이 될 수 없습니다.");
        Assert.notNull(user, "user는 null이 될 수 없습니다.");

        PostLike postLike = PostLike.builder()
                .post(post)
                .user(user)
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .build();

        post.increaseLike();

        return postLike;
    }
}

