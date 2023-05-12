package io.mhan.springjpatest2.likes.repository;

import io.mhan.springjpatest2.base.RepositoryTestBase;
import io.mhan.springjpatest2.likes.entity.PostLike;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PostLikeRepositoryTest extends RepositoryTestBase {

    @Test
    @DisplayName("user1이 좋아한 post 조회")
    void t1() {
        List<PostLike> postLikes = postLikeRepository.findByUserId(user1.getId(), Sort.unsorted());

        boolean result = postLikes.stream()
                .allMatch(postLike -> postLike.getUser().getId().equals(user1.getId()));
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("user1가 좋아한 post 조회 and 좋아한 post 최신순 조회")
    void t2() {
        Sort.Order order = Sort.Order.desc("created");
        Sort sort = Sort.by(order);
        List<PostLike> postLikes = postLikeRepository.findByUserId(user1.getId(), sort);

        boolean result = postLikes.stream()
                .allMatch(postLike -> postLike.getUser().getId().equals(user1.getId()));
        assertThat(result).isTrue();
        assertThat(postLikes).isSortedAccordingTo(Comparator.comparing(PostLike::getCreated, Comparator.reverseOrder()));
    }
}
