package io.mhan.springjpatest2.posts.entity;

import io.mhan.springjpatest2.posts.repository.PostRepository;
import io.mhan.springjpatest2.users.entity.User;
import io.mhan.springjpatest2.users.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("post 생성")
    void t1() {

        User user = userRepository.save(User.create("user1", "pass"));

        Post post = Post.create("title1", "content1", user);

        postRepository.save(post);
    }
}
