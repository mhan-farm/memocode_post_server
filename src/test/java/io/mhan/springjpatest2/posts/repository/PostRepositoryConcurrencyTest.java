package io.mhan.springjpatest2.posts.repository;


import io.mhan.springjpatest2.base.init.TestService;
import io.mhan.springjpatest2.comments.entity.Comment;
import io.mhan.springjpatest2.likes.service.PostLikeService;
import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.users.entity.User;
import io.mhan.springjpatest2.users.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@Slf4j
@SpringBootTest
@TestInstance(PER_CLASS)
public class PostRepositoryConcurrencyTest {

    @Autowired
    private TestService testService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostLikeService postLikeService;

    Post post1;
    Post post2;
    Post post3;
    User user1;
    User user2;

    @BeforeAll
    void beforeAll() {
        List<User> authors = testService.createUsers(10);
        List<Post> posts = testService.createTestPosts(10, 100, authors);
        testService.createPostLikes(100, posts, authors);
        post1 = posts.get(0);
        post2 = posts.get(1);
        post3 = posts.get(2);
        user1 = authors.get(0);
        user2 = authors.get(1);
    }

    @AfterAll
    void afterAll() {
        testService.deleteAll();
    }

    @Test
    @DisplayName("mariadb 사용시 REPEATABLE_READ로 작동 확인")
    void t1() {
        long oldCount = post1.getCommentCount();
        long newCount = testService.executeInTransaction(() -> {
            log.info("transaction1 start...");

            // 첫번째 조회
            postRepository.findById(post1.getId()).get();

            // 중간에 commentCount update
            testService.executeInTransaction(() -> {
                log.info("transaction2 start...");

                Post transaction2_post = postRepository.findById(post1.getId()).get();
                User findUser2 = userRepository.findById(user2.getId()).get();
                transaction2_post.addComment(Comment.create("title", findUser2));

                return null;
            });
            log.info("transaction2 end...");

            // 두번째 조회
            return postRepository.findById(post1.getId()).get().getCommentCount();
        });

        assertThat(oldCount).isEqualTo(newCount);
    }

    @Test
    @DisplayName("한 트랜잭션에서 조회하는 도중에 다른 트랜잭션이 commentCount가 카운트 될 때 누락 발생")
    void t2() {
        long oldCommentCount = post2.getCommentCount();
        Long newCommentCount = testService.executeInTransaction(() -> {

            Post transaction1_post = postRepository.findById(post2.getId()).get();
            User findUser1 = userRepository.findById(user2.getId()).get();
            transaction1_post.addComment(Comment.create("title", findUser1));

            try {Thread.sleep(1000);} catch (InterruptedException e) {}
            testService.executeInTransaction(() -> {

                Post transaction2_post = postRepository.findById(post2.getId()).get();
                User findUser2 = userRepository.findById(user2.getId()).get();
                transaction2_post.addComment(Comment.create("title", findUser2));

                return null;
            });

            return transaction1_post.getCommentCount();
        });

        assertThat(newCommentCount).isNotEqualTo(oldCommentCount + 2);
    }

    @Test
    @DisplayName("한 트랜잭션에서 조회하는 도중에 다른 트랜잭션이 likeCount가 카운트 될 때 누락 발생")
    void t3() {
        long oldLikeCount = post3.getLikeCount();
        Long newLikeCount = testService.executeInTransaction(() -> {

            Post transaction1_post = postRepository.findById(post3.getId()).get();

            testService.executeInTransaction(() -> {

                Post transaction2_post = postRepository.findById(post3.getId()).get();
                User findUser2 = userRepository.findById(user2.getId()).get();
                postLikeService.createAndSave(transaction2_post.getId(), findUser2.getId());

                return null;
            });

            User findUser2 = userRepository.findById(user2.getId()).get();
            postLikeService.createAndSave(transaction1_post.getId(), findUser2.getId());
            return transaction1_post.getLikeCount();
        });

        assertThat(newLikeCount).isNotEqualTo(oldLikeCount + 2);
    }
}
