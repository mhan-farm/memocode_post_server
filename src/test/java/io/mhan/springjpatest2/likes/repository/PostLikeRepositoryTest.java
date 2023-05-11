package io.mhan.springjpatest2.likes.repository;

import io.mhan.springjpatest2.base.RepositoryTestBase;
import io.mhan.springjpatest2.likes.entity.PostLike;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PostLikeRepositoryTest extends RepositoryTestBase {

    @Test
    @DisplayName("user1이 좋아한 post 조회")
    void t1() {
        List<PostLike> postLikes = postLikeRepository.findByUserId(user1.getId());

        boolean result = postLikes.stream()
                .allMatch(postLike -> postLike.getUser().getId().equals(user1.getId()));
        assertThat(result).isTrue();
    }
}
