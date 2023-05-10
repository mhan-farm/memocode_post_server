package io.mhan.springjpatest2.posts.entity;

import io.mhan.springjpatest2.posts.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("post 생성")
    void t1() {
        Post post = Post.create("title1", "content1");
        postRepository.save(post);
    }
}
