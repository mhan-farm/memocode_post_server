package io.mhan.springjpatest2.base.init;

import io.mhan.springjpatest2.comments.entity.Comment;
import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.posts.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class TestService {
    private final PostRepository postRepository;


    public void testData() {

        // post 생성
        for (int i=1; i<=50; i++) {
            Post post = Post.create("title" + i, "content" + (i + 1));
            postRepository.save(post);
        }

        List<Post> posts = postRepository.findAll(null, Sort.unsorted());

        Random random = new Random();
        for (int i=1; i<=1000; i++) {
            Post post = posts.get(random.nextInt(posts.size()));

            Comment comment = Comment.create("content" + i);
            post.addComment(comment);
        }
    }
}
