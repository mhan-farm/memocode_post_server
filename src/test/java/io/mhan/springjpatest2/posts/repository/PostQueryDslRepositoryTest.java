package io.mhan.springjpatest2.posts.repository;

import io.mhan.springjpatest2.base.RepositoryTestBase;
import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.posts.repository.vo.Keyword;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.util.Comparator;
import java.util.List;

import static io.mhan.springjpatest2.posts.repository.vo.KeywordType.TITLE;
import static io.mhan.springjpatest2.posts.repository.vo.KeywordType.TITLE_CONTENT;
import static org.assertj.core.api.Assertions.assertThat;

public class PostQueryDslRepositoryTest extends RepositoryTestBase {

    @Test
    @DisplayName("post 모두 조회")
    void t1() {
        List<Post> posts = postRepository.findAll(null, Sort.unsorted());

        assertThat(posts.size()).isEqualTo(1000);
    }

    @Test
    @DisplayName("post의 title값이 10이 포함된 post 조회")
    void t2() {
        Keyword keyword = Keyword.builder()
                .type(TITLE)
                .value("10")
                .build();

        List<Post> posts = postRepository.findAll(keyword, Sort.unsorted());

        boolean result = posts.stream()
                .allMatch(post -> post.getTitle().contains(keyword.getValue()));
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("post의 title값이거나 content값이 10이 포함된 post 조회")
    void t3() {
        Keyword keyword = Keyword.builder()
                .type(TITLE_CONTENT)
                .value("10")
                .build();

        List<Post> posts = postRepository.findAll(keyword, Sort.unsorted());

        boolean result = posts.stream()
                .allMatch(post -> post.getTitle().contains(keyword.getValue()) ||
                        post.getContent().contains(keyword.getValue()));
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("오래된 순으로 post 조회")
    void t4() {
        Sort.Order order = Sort.Order.asc("created");

        Sort sort = Sort.by(order);

        List<Post> posts = postRepository.findAll(null, sort);

        assertThat(posts).isSortedAccordingTo(Comparator.comparing(Post::getCreated));
    }

    @Test
    @DisplayName("최신순으로 post 조회")
    void t5() {
        Sort.Order order = Sort.Order.desc("created");

        Sort sort = Sort.by(order);

        List<Post> posts = postRepository.findAll(null, sort);

        assertThat(posts).isSortedAccordingTo(Comparator.comparing(Post::getCreated, Comparator.reverseOrder()));
    }

    @Test
    @DisplayName("가장 많은 답변수를 기준으로 정렬을 하여 전체 post 조회")
    void t6() {
        Sort.Order order = Sort.Order.desc("comments");

        Sort sort = Sort.by(order);

        List<Post> posts = postRepository.findAll(null, sort);

        assertThat(posts).isSortedAccordingTo(Comparator.comparing(Post::getCommentCount, Comparator.reverseOrder()));
    }

    @Test
    @DisplayName("가장 많은 좋아요가 많은 순으로 정렬을 하여 전체 post 조회")
    void t7() {
        Sort.Order order = Sort.Order.desc("likes");

        Sort sort = Sort.by(order);

        List<Post> posts = postRepository.findAll(null, sort);

        assertThat(posts).isSortedAccordingTo(Comparator.comparing(Post::getLikeCount, Comparator.reverseOrder()));
    }

    @Test
    @DisplayName("title만 10이 포함된 post 조회 and 가장 조회수가 많은순으로 정렬")
    void t8() {
        Keyword keyword = Keyword.builder()
                .type(TITLE)
                .value("10")
                .build();
        Sort.Order order = Sort.Order.desc("views");

        Sort sort = Sort.by(order);

        List<Post> posts = postRepository.findAll(keyword, sort);

        boolean result = posts.stream()
                .allMatch(post -> post.getTitle().contains(keyword.getValue()));
        assertThat(result).isTrue();
        assertThat(posts).isSortedAccordingTo(Comparator.comparing(Post::getViews, Comparator.reverseOrder()));
    }


}
