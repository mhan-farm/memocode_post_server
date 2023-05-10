package io.mhan.springjpatest2.base.init;

import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.posts.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {
    private final PostRepository postRepository;

    public void testData() {
        for (int i=1; i<=50; i++) {
            Post post = Post.create("title" + i, "content" + (i + 1));
            postRepository.save(post);
        }
    }
}
