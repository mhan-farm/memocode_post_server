package io.mhan.springjpatest2.posts.repository;

import io.mhan.springjpatest2.base.init.TestService;
import io.mhan.springjpatest2.posts.entity.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
        List<Post> posts = postRepository.findAll();

        Assertions.assertThat(posts.size()).isEqualTo(50);
    }
}
