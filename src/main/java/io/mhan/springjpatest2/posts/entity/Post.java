package io.mhan.springjpatest2.posts.entity;

import io.mhan.springjpatest2.comments.entity.Comment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
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

    private String title;

    private String content;

    private LocalDateTime created;

    private LocalDateTime updated;

    @OneToMany(mappedBy = "post", cascade = PERSIST)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    public static Post create(String title, String content) {

        Assert.notNull(title, "title은 null이 될 수 없습니다.");
        Assert.notNull(content, "title은 null이 될 수 없습니다.");

        Post post = Post.builder()
                .title(title)
                .content(content)
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .build();

        return post;
    }

    public void addComment(Comment comment) {
        comment.setPost(this);
        this.comments.add(comment);
    }
}
