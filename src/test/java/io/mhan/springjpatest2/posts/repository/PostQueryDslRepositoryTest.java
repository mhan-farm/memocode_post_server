package io.mhan.springjpatest2.posts.repository;

import io.mhan.springjpatest2.base.init.TestService;
import io.mhan.springjpatest2.comments.repository.CommentRepository;
import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.posts.repository.vo.Keyword;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static io.mhan.springjpatest2.posts.repository.vo.KeywordType.TITLE;
import static io.mhan.springjpatest2.posts.repository.vo.KeywordType.TITLE_CONTENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@Transactional
@SpringBootTest
@TestInstance(PER_CLASS)
public class PostQueryDslRepositoryTest {

    @Autowired
    TestService testService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @BeforeAll
    void init() {
        testService.testData(50, 100);
    }

    @AfterAll
    void clear() {
        testService.deleteAll();
    }

    @Test
    @DisplayName("post 모두 조회")
    void t1() {
        List<Post> posts = postRepository.findAll(null, Sort.unsorted());

        assertThat(posts.size()).isEqualTo(50);
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

        Collections.reverse(posts);
        assertThat(posts).isSortedAccordingTo(Comparator.comparing(Post::getCreated));
    }
}
