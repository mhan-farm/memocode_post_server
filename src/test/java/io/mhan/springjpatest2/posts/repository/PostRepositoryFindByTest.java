package io.mhan.springjpatest2.posts.repository;

import io.mhan.springjpatest2.base.init.TestService;
import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.users.entity.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@Transactional
@SpringBootTest
@TestInstance(PER_CLASS)
public class PostRepositoryFindByTest {

    @Autowired
    private TestService testService;

    @Autowired
    private PostRepository postRepository;

    User user1;

    Post post1;

    @BeforeAll
    void beforeAll() {
        List<User> authors = testService.createUsers(10);
        List<Post> posts = testService.createTestPosts(10, 100, authors);
        user1 = authors.get(0);
        post1 = posts.get(0);
    }

    @AfterAll
    void afterAll() {
        testService.deleteAll();
    }

    @Test
    @DisplayName("post id를 통해서 한번 찾아오기")
    void t2() {
        Post findPost = postRepository.findById(post1.getId()).get();
    }

    @Test
    @DisplayName("post id를 통해서 두번 찾아오기")
    void t3() {
        Post findPost = postRepository.findById(post1.getId()).get();
        Post findPost2 = postRepository.findById(post1.getId()).get();
    }
    // @Id가 달린 필드는 기본적으로 고유하기 때문에 캐싱을 한다.
    // 그래서 쿼리 select가 한번 발생한다.
    // 그래서 왠만하면 id를 통해 검색해오는 것이 성능상 유리하다.
}

