package io.mhan.springjpatest2.posts.entity;

import io.mhan.springjpatest2.comments.entity.Comment;
import io.mhan.springjpatest2.users.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(mappedBy = "post", cascade = PERSIST)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    private long commentCount;

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
}
