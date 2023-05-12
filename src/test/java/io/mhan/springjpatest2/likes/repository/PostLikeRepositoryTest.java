package io.mhan.springjpatest2.likes.repository;

import io.mhan.springjpatest2.base.RepositoryTestBase;
import io.mhan.springjpatest2.likes.entity.PostLike;
import io.mhan.springjpatest2.posts.repository.vo.Keyword;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.util.Comparator;
import java.util.List;

import static io.mhan.springjpatest2.posts.repository.vo.KeywordType.TITLE;
import static org.assertj.core.api.Assertions.assertThat;

public class PostLikeRepositoryTest extends RepositoryTestBase {

    @Test
    @DisplayName("user1이 좋아한 post 조회")
    void t1() {
        List<PostLike> postLikes = postLikeRepository.findByUserId(user1.getId(), Sort.unsorted(), null);

        boolean result = postLikes.stream()
                .allMatch(postLike -> postLike.getUser().getId().equals(user1.getId()));
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("user1가 좋아한 post 조회 and 좋아한 post 최신순 조회")
    void t2() {
        Sort.Order order = Sort.Order.desc("created");
        Sort sort = Sort.by(order);
        List<PostLike> postLikes = postLikeRepository.findByUserId(user1.getId(), sort, null);

        boolean result = postLikes.stream()
                .allMatch(postLike -> postLike.getUser().getId().equals(user1.getId()));
        assertThat(result).isTrue();
        assertThat(postLikes).isSortedAccordingTo(Comparator.comparing(PostLike::getCreated, Comparator.reverseOrder()));
    }

    @Test
    @DisplayName("user1가 좋아한 post 조회 and 좋아한 post 최신순 조회 and keyword title 10이 포함되게 검색")
    void t3() {
        Keyword keyword = Keyword.builder()
                .type(TITLE)
                .value("10")
                .build();
        Sort.Order order = Sort.Order.desc("created");
        Sort sort = Sort.by(order);
        List<PostLike> postLikes = postLikeRepository.findByUserId(user1.getId(), sort, keyword);

        boolean result = postLikes.stream()
                .allMatch(postLike -> postLike.getUser().getId().equals(user1.getId()));
        boolean result2 = postLikes.stream()
                .allMatch(postLike -> postLike.getPost().getTitle().contains(keyword.getValue()));

        assertThat(result).isTrue();
        assertThat(result2).isTrue();
        assertThat(postLikes).isSortedAccordingTo(Comparator.comparing(PostLike::getCreated, Comparator.reverseOrder()));

    }

}
