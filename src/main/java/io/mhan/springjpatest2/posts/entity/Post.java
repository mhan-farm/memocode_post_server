package io.mhan.springjpatest2.posts.entity;

import io.mhan.springjpatest2.comments.entity.Comment;
import io.mhan.springjpatest2.likes.entity.PostLike;
import io.mhan.springjpatest2.tags.entity.PostTag;
import io.mhan.springjpatest2.tags.entity.Tag;
import io.mhan.springjpatest2.users.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.Where;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
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
    @LazyCollection(EXTRA)
    @Builder.Default
    @Getter(AccessLevel.NONE)
    private Set<PostLike> likes = new HashSet<>();

    @OneToMany(mappedBy = "post", cascade = PERSIST, orphanRemoval = true)
    @Builder.Default
    private Set<PostTag> tags = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_post_id")
    private Post parentPost;

    @OneToMany(mappedBy = "parentPost", cascade = PERSIST)
    @LazyCollection(EXTRA)
    @OrderBy("sequence asc")
    @Where(clause = "is_deleted = false")
    private List<Post> childPosts = new ArrayList<>();

    private long sequence;

    private long commentCount;

    private long likeCount;

    private long views;

    private boolean isDeleted;

    private LocalDateTime deletedAt;

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

    public void softDelete() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    public void increaseCommentCount() {
        this.commentCount = comments.size();
    }

    public void increaseViews() {
        this.views++;
    }

    public void addTags(Set<Tag> tags) {
        tags.forEach(tag -> {
            PostTag postTag = PostTag.create(this, tag);
            this.tags.add(postTag);
        });
        this.updated = LocalDateTime.now();
    }

    public void updateTags(Set<Tag> tags) {
        this.tags.clear();
        addTags(tags);
        this.updated = LocalDateTime.now();
    }

    public void addChildPosts(Post post) {

        int childPostCount = childPosts.size() + 1;

        post.updateSequence(childPostCount);
        post.updateParentPost(this);

        this.childPosts.add(post);
    }

    private void updateParentPost(Post parentPost) {
        this.parentPost = parentPost;
    }

    public void updateSequence(long sequence) {
        this.sequence = sequence;
    }
}
