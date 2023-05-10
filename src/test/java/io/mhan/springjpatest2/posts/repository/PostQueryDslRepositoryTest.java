package io.mhan.springjpatest2.posts.repository;

import io.mhan.springjpatest2.base.init.TestService;
import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.posts.repository.vo.Keyword;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static io.mhan.springjpatest2.posts.repository.vo.KeywordType.TITLE;
import static io.mhan.springjpatest2.posts.repository.vo.KeywordType.TITLE_CONTENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@SpringBootTest
@TestInstance(PER_CLASS)
public class PostQueryDslRepositoryTest {

    @Autowired
    TestService testService;

    @Autowired
    PostRepository postRepository;

    @BeforeAll
    void beforeAll() {
        testService.testData();
    }

    @AfterAll
    void afterAll() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("post 모두 조회")
    void t1() {
        List<Post> posts = postRepository.findAll(null);

        assertThat(posts.size()).isEqualTo(50);
    }

    @Test
    @DisplayName("post의 title값이 10이 포함된 post 조회")
    void t2() {
        Keyword keyword = Keyword.builder()
                .type(TITLE)
                .value("10")
                .build();

        List<Post> posts = postRepository.findAll(keyword);

        assertThat(posts.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("post의 title값이거나 content값이 10이 포함된 post 조회")
    void t3() {
        Keyword keyword = Keyword.builder()
                .type(TITLE_CONTENT)
                .value("10")
                .build();

        List<Post> posts = postRepository.findAll(keyword);

        assertThat(posts.size()).isEqualTo(2);
    }
}
