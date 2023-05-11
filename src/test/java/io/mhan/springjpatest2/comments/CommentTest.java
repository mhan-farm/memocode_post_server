package io.mhan.springjpatest2.comments;

import io.mhan.springjpatest2.comments.entity.Comment;
import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.posts.repository.PostRepository;
import io.mhan.springjpatest2.users.entity.User;
import io.mhan.springjpatest2.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommentTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    User user;

    Post post;

    @BeforeAll
    void init() {
         user = userRepository.save(User.create("user1", "pass"));
         post = Post.create("title1", "content1", user);

        Comment comment = Comment.create("content1");
        post.addComment(comment);
        postRepository.save(post);
    }

    @Test
    @DisplayName("comment 생성")
    void t1() {
        Post post = postRepository.findById(this.post.getId()).get();

        Comment comment = Comment.create("content2");
        post.addComment(comment);

        postRepository.save(post);
    }

}
