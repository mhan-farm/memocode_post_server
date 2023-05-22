package io.mhan.springjpatest2.posts.entity;

import io.mhan.springjpatest2.comments.entity.Comment;
import io.mhan.springjpatest2.likes.entity.PostLike;
import io.mhan.springjpatest2.users.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicUpdate;
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
import static lombok.AccessLevel.NONE;

@Slf4j
@Getter
@Builder
@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class Post {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "author_id", foreignKey = @ForeignKey(name = "FK_users_posts"))
    private User author;

    private LocalDateTime created;

    private LocalDateTime updated;

    @OneToMany(mappedBy = "post", cascade = {PERSIST})
    @Builder.Default
    @Getter(AccessLevel.NONE)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Builder.Default
    @Getter(AccessLevel.NONE)
    private Set<PostLike> likes = new HashSet<>();

    private long commentCount;

    private long likeCount;

    private long views;

    private boolean isDeleted;

    public static Post create(String title, String content, User author) {

        Assert.notNull(title, "title은 null이 될 수 없습니다.");
        Assert.notNull(content, "content은 null이 될 수 없습니다.");
        Assert.notNull(author, "author은 null이 될 수 없습니다.");

        Post post = Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .isDeleted(false)
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .build();

        return post;
    }

    public void increaseLike() {
        this.likeCount = comments.size();
    }

    public void updateTitleAndContent(String title, String content) {

        Assert.notNull(title, "title은 null이 될 수 없습니다.");
        Assert.notNull(content, "content은 null이 될 수 없습니다.");

        this.title = title;
        this.content = content;
        this.updated = LocalDateTime.now();
    }

    public void delete() {
        this.isDeleted = true;
    }

    public void increaseCommentCount() {
        this.commentCount = comments.size();
    }

    public void increaseViews() {
        this.views++;
    }
}
