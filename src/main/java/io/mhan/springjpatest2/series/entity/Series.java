package io.mhan.springjpatest2.series.entity;

import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.users.entity.Author;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Series {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    private String title;

    @ManyToOne(fetch = LAZY)
    private Author author;

    @Builder.Default
    @OneToMany(mappedBy = "series", cascade = PERSIST, orphanRemoval = true)
    @OrderColumn(name="sequence")
    private Set<PostSeries> postSeries = new LinkedHashSet<>();

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    public void setPosts(List<Post> posts) {
        if (author == null) {
            throw new IllegalArgumentException("author가 존재하지 않습니다.");
        }

        // 주어진 게시물들이 모두 자신의 게시물인지 확인
        UUID authorId = author.getId();
        if (!posts.stream().allMatch(post -> authorId.equals(post.getAuthor().getId()))) {
            throw new IllegalArgumentException("author의 게시물이 아닌 것이 포함되어 있습니다.");
        }

        // 기존 것 모두 삭제
        postSeries.clear();

        // 받아온대로 순차적으로 시퀀스 증가
        AtomicInteger index = new AtomicInteger(0);
        List<PostSeries> postSeriesList = posts.stream()
                .map(post -> PostSeries.builder()
                        .series(this)
                        .post(post)
                        .sequence(index.getAndIncrement())
                        .build())
                .toList();

        postSeries.addAll(postSeriesList);
    }
}
