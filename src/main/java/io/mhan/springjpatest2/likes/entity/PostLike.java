package io.mhan.springjpatest2.likes.entity;

import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.users.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = {"post", "user"})
public class PostLike {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Post post;

    @ManyToOne(fetch = LAZY)
    private User user;

    private LocalDateTime created;

    private LocalDateTime updated;

    public static PostLike create(Post post, User user) {
        PostLike postLike = PostLike.builder()
                .post(post)
                .user(user)
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .build();

        return postLike;
    }
}

