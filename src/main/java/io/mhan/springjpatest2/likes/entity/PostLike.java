package io.mhan.springjpatest2.likes.entity;

import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.users.entity.Author;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@Table(name = "post_like")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class PostLike {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "FK_posts_postLike"))
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "author_id", foreignKey = @ForeignKey(name = "FK_users_postLike"))
    private Author author;

    private LocalDateTime created;

    private LocalDateTime updated;

    public static PostLike create(Post post, Author author) {

        Assert.notNull(post, "post는 null이 될 수 없습니다.");
        Assert.notNull(author, "author는 null이 될 수 없습니다.");

        PostLike postLike = PostLike.builder()
                .post(post)
                .author(author)
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .build();

        post.increaseLike();

        return postLike;
    }
}

