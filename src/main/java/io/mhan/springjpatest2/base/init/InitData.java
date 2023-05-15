package io.mhan.springjpatest2.base.init;

import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.users.entity.User;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class InitData {

    @Bean
    public ApplicationRunner defaultInitData(TestService testService) {
        return args -> {
            List<User> users = testService.createUsers(10);
            List<Post> posts = testService.createTestPosts(100, 1000, users);
            testService.createTestComments(100, posts, users);
            testService.createPostLikes(100, posts, users);
        };
    }
}
