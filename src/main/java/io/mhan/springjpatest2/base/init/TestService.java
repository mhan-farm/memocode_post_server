package io.mhan.springjpatest2.base.init;

import io.mhan.springjpatest2.comments.entity.Comment;
import io.mhan.springjpatest2.comments.repository.CommentRepository;
import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.posts.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TestService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public void testData(int postCount, int commentCount) {
        createTestPosts(postCount);
        createTestComments(commentCount);
    }

    private void createTestComments(int count) {
        List<Post> posts = postRepository.findAll(null, Sort.unsorted());

        Random random = new Random();
        for (int i=1; i<=count; i++) {
            Post post = posts.get(random.nextInt(posts.size()));

            Comment comment = Comment.create("content" + i);
            post.addComment(comment);
        }
    }

    private void createTestPosts(int count) {
        for (int i=1; i<=count; i++) {
            Post post = Post.create("title" + i, "content" + (i + 1));
            postRepository.save(post);
        }
    }

    public void deleteAll() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
    }
}
