package io.mhan.springjpatest2.base;

import io.mhan.springjpatest2.base.init.TestService;
import io.mhan.springjpatest2.comments.repository.CommentRepository;
import io.mhan.springjpatest2.likes.repository.PostLikeRepository;
import io.mhan.springjpatest2.posts.repository.PostRepository;
import io.mhan.springjpatest2.users.entity.User;
import io.mhan.springjpatest2.users.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@Transactional
@SpringBootTest
@TestInstance(PER_CLASS)
public abstract class RepositoryTestBase {

    @Autowired
    public TestService testService;

    @Autowired
    public PostRepository postRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public CommentRepository commentRepository;

    @Autowired
    public PostLikeRepository postLikeRepository;

    public User user1;

    @BeforeAll
    void init() {
        testService.testData(1000, 100, 10, 50);
        user1 = userRepository.findByUsername("user1").get();
    }

    @AfterAll
    void clear() {
        testService.deleteAll();
    }
}
