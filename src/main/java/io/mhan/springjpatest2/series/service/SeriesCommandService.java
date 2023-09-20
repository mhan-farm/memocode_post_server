package io.mhan.springjpatest2.series.service;

import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.posts.service.PostQueryService;
import io.mhan.springjpatest2.series.entity.Series;
import io.mhan.springjpatest2.series.repository.SeriesRepository;
import io.mhan.springjpatest2.series.request.SeriesSetPostRequest;
import io.mhan.springjpatest2.users.entity.Author;
import io.mhan.springjpatest2.users.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class SeriesCommandService {

    private final SeriesRepository seriesRepository;

    private final AuthorService userService;
    private final PostQueryService postQueryService;
    private final SeriesQueryService seriesQueryService;

    public UUID createAndSave(String name, UUID authorId) {
        Series series = create(name, authorId);

        Series savedSeries = seriesRepository.save(series);

        return savedSeries.getId();
    }

    private Series create(String title, UUID authorId) {
        Author author = userService.findActiveByIdElseThrow(authorId);

        Series series = Series.builder()
                .title(title)
                .author(author)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return series;
    }

    public void setPosts(UUID seriesId, SeriesSetPostRequest request) {
        Series series = seriesQueryService.findByIdElseThrow(seriesId);

        Set<UUID> postIds = request.getPostIds();

        // db에서 가져올 때는 순서를 보장받지 못함
        List<Post> unorderedPosts = postQueryService.findByIdIn(postIds);

        // 모든 포스트가 존재하는지 확인
        if (postIds.size() != unorderedPosts.size()) {
            throw new IllegalArgumentException("존재하지 않는 post가 있습니다.");
        }

        // postIds 순서대로 정렬
        List<Post> orderedPosts = postIds.stream()
                .map(id -> unorderedPosts.stream()
                        .filter(post -> post.getId().equals(id))
                        .findFirst().orElse(null))
                .filter(Objects::nonNull)
                .toList();
        series.setPosts(orderedPosts);
    }

    public void deleteById(UUID seriesId, UUID authorId) {
        Series series = seriesQueryService.findByIdElseThrow(seriesId);

        if (!series.getAuthor().getId().equals(authorId)) {
            throw new IllegalArgumentException("시리즈를 삭제할 권한이 없습니다.");
        }

        seriesRepository.deleteById(series.getId());
    }
}
