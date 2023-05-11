package io.mhan.springjpatest2.posts.entity;

import io.mhan.springjpatest2.comments.entity.Comment;
import io.mhan.springjpatest2.likes.entity.PostLike;
import io.mhan.springjpatest2.users.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Post {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Setter
    private String title;

    private String content;

    @ManyToOne(fetch = LAZY)
    private User author;

    private LocalDateTime created;

    private LocalDateTime updated;

    @OneToMany(mappedBy = "post", cascade = {PERSIST})
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = {PERSIST})
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Builder.Default
    private Set<PostLike> likes = new HashSet<>();

    private long commentCount;
    private long likeCount;

    @Setter
    private long views;

    public static Post create(String title, String content, User author) {

        Assert.notNull(title, "title은 null이 될 수 없습니다.");
        Assert.notNull(content, "title은 null이 될 수 없습니다.");

        Post post = Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .build();

        return post;
    }

    public void addComment(Comment comment) {
        comment.setPost(this);
        this.comments.add(comment);
        this.commentCount = comments.size();
    }

    public void addPostLike(User user) {
        PostLike postLike = PostLike.create(this, user);
        this.likes.add(postLike);
        this.likeCount = likes.size();
    }
}
