package io.mhan.springjpatest2.base.init;


import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.users.entity.User;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
public class InitData {

    public static Long USER_ID = 1L;

    @Bean
    @Profile("default")
    public ApplicationRunner defaultInitData(TestService testService) {
        return args -> {
            List<User> users = testService.createUsers(3);
            List<Post> posts = testService.createTestPosts(10, 1000, users);

            testService.createTestComments(10, posts, users);
            testService.createPostLikes(10, posts, users);

            testService.createTestSubPosts(posts.get(0), 10, 100);
        };
    }
}
