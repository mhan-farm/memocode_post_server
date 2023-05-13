package io.mhan.springjpatest2.posts.repository;

import io.mhan.springjpatest2.base.init.TestService;
import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.posts.repository.vo.PostKeyword;
import io.mhan.springjpatest2.users.entity.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

import static io.mhan.springjpatest2.posts.repository.vo.PostKeywordType.TITLE;
import static io.mhan.springjpatest2.posts.repository.vo.PostKeywordType.TITLE_CONTENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@Transactional
@SpringBootTest
@TestInstance(PER_CLASS)
public class PostRepositoryFindAllTest {

    @Autowired
    TestService testService;

    @Autowired
    PostRepository postRepository;

    @BeforeAll
    void beforeAll() {
        List<User> authors = testService.createUsers(100);
        List<Post> posts = testService.createTestPosts(100, 1000, authors);
        testService.createTestComments(100, posts, authors);
        testService.createPostLikes(100, posts, authors);
    }

    @AfterAll
    void afterAll() {
        testService.deleteAll();
    }

    @Test
    @DisplayName("post 모두 조회")
    void t1() {
        List<Post> posts = postRepository.findAll(null, Sort.unsorted());

        assertThat(posts.size()).isEqualTo(postRepository.count());
    }

    @Test
    @DisplayName("post의 title값이 10이 포함된 post 조회")
    void t2() {
        PostKeyword keyword = PostKeyword.builder()
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
        PostKeyword keyword = PostKeyword.builder()
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
        PostKeyword keyword = PostKeyword.builder()
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
