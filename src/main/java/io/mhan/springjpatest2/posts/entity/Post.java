package io.mhan.springjpatest2.posts.entity;

import io.mhan.springjpatest2.comments.entity.Comment;
import io.mhan.springjpatest2.likes.entity.PostLike;
import io.mhan.springjpatest2.tags.entity.PostTag;
import io.mhan.springjpatest2.tags.entity.Tag;
import io.mhan.springjpatest2.users.entity.Author;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.*;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.LAZY;
import static org.hibernate.annotations.LazyCollectionOption.EXTRA;

@Slf4j
@Getter
@Builder
@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@EqualsAndHashCode(of = "id")
public class Post {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "author_id", foreignKey = @ForeignKey(name = "FK_authors_posts"))
    private Author author;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "post", cascade = {PERSIST})
    @Builder.Default
    @Getter(AccessLevel.NONE)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    @LazyCollection(EXTRA)
    @Builder.Default
    @Getter(AccessLevel.NONE)
    private Set<PostLike> likes = new HashSet<>();

    @OneToMany(mappedBy = "post", cascade = PERSIST, orphanRemoval = true)
    @Builder.Default
    private Set<PostTag> tags = new HashSet<>();

    private long commentCount;

    private long likeCount;

    private long views;

    @Builder.Default
    private boolean isDeleted = Boolean.FALSE;

    private LocalDateTime deletedAt;

    private boolean isPrivate;

    public void increaseLike() {
        this.likeCount = comments.size();
    }

    public void updateTitle(String title) {
        Assert.notNull(title, "title은 null이 될 수 없습니다.");

        if (title.isBlank()) {
            throw new IllegalArgumentException("제목을 입력해주세요");
        }

        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateContent(String content) {

        Assert.notNull(content, "content은 null이 될 수 없습니다.");
        if (content.isBlank()) {
            throw new IllegalArgumentException("내용을 입력해주세요");
        }

        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

    public void softDelete() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    public void addTags(Set<Tag> tags) {
        tags.forEach(tag -> {
            PostTag postTag = PostTag.create(this, tag);
            this.tags.add(postTag);
        });
        this.updatedAt = LocalDateTime.now();
    }

    public void updateTags(Set<Tag> tags) {
        this.tags.clear();
        addTags(tags);
        this.updatedAt = LocalDateTime.now();
    }
}
