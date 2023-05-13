package io.mhan.springjpatest2.comments;

import io.mhan.springjpatest2.base.init.TestService;
import io.mhan.springjpatest2.posts.repository.PostRepository;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class CommentTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TestService testService;

    @BeforeAll
    void beforeAll() {

    }

//    @Test
//    @DisplayName("comment 생성")
//    void t1() {
//        User user = userRepository.save(User.create("user1", "pass"));
//        Post post = postRepository.save(Post.create("title1", "content1", user));
//
//        Comment comment = Comment.create("content1");
//        post.addComment(comment);
//
//        Post findPost = postRepository.findById(post.getId()).get();
//
//        Comment comment2 = Comment.create("content2");
//        findPost.addComment(comment2);
//    }

}
