package io.mhan.springjpatest2.likes.repository;

import io.mhan.springjpatest2.base.RepositoryTestBase;
import io.mhan.springjpatest2.likes.entity.PostLike;
import io.mhan.springjpatest2.posts.entity.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    @Test
    @DisplayName("user1가 좋아한 post 조회 and 최신순 조회")
    void t2() {
        List<PostLike> postLikes = postLikeRepository.findByUserId(user1.getId());

        List<Post> posts = postLikes.stream()
                .map(PostLike::getPost)
                .collect(Collectors.toList());

        boolean result = postLikes.stream()
                .allMatch(postLike -> postLike.getUser().getId().equals(user1.getId()));
        assertThat(result).isTrue();
        assertThat(posts).isSortedAccordingTo(Comparator.comparing(Post::getCreated, Comparator.reverseOrder()));
    }
}
