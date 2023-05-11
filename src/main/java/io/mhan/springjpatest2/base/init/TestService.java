package io.mhan.springjpatest2.base.init;

import io.mhan.springjpatest2.comments.entity.Comment;
import io.mhan.springjpatest2.comments.repository.CommentRepository;
import io.mhan.springjpatest2.likes.repository.PostLikeRepository;
import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.posts.repository.PostRepository;
import io.mhan.springjpatest2.users.entity.User;
import io.mhan.springjpatest2.users.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;

    public void testData(int postCount, int commentCount, int userCount, int postLikeCount) {
        createUsers(userCount);
        createTestPosts(postCount);
        createTestComments(commentCount);
        createPostLikes(postLikeCount);
    }

    private void createPostLikes(int postLikeCount) {
        List<Post> posts = postRepository.findAll(null, Sort.unsorted());
        List<User> users = userRepository.findAll();

        Random random = new Random();
        for (int i=1; i<=postLikeCount; i++) {
            Post post = posts.get(random.nextInt(posts.size()));
            User user = users.get(random.nextInt(users.size()));

            post.addPostLike(user);
        }
    }

    private void createUsers(int userCount) {
        for (int i=1; i<=userCount; i++) {
            User user = User.create("user" + i, "pass");
            userRepository.save(user);
        }
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
        List<User> users = userRepository.findAll();

        Random random = new Random();
        for (int i=1; i<=count; i++) {
            User user = users.get(random.nextInt(users.size()));
            Post post = Post.create("title" + i, "content" + (i + 1), user);
            postRepository.save(post);
        }
    }

    public void deleteAll() {
        commentRepository.deleteAll();
        postLikeRepository.deleteAll();
        userRepository.deleteAll();
        postRepository.deleteAll();
    }
}
