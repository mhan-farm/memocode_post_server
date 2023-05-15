package io.mhan.springjpatest2.base.init;

import io.mhan.springjpatest2.comments.entity.Comment;
import io.mhan.springjpatest2.comments.repository.CommentRepository;
import io.mhan.springjpatest2.likes.repository.PostLikeRepository;
import io.mhan.springjpatest2.likes.service.PostLikeService;
import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.posts.repository.PostRepository;
import io.mhan.springjpatest2.users.entity.User;
import io.mhan.springjpatest2.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
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

    private final PostLikeService postLikeService;

    public void createPostLikes(int postLikeCount, List<Post> posts, List<User> authors) {

        List<Post> findPosts = postRepository.findByIdIn(getPostIds(posts));
        List<User> findAuthors = userRepository.findByIdIn(getAuthorIds(authors));

        Random random = new Random();
        for (int i=1; i<=postLikeCount; i++) {
            Post post = findPosts.get(random.nextInt(findPosts.size()));
            User user = findAuthors.get(random.nextInt(findAuthors.size()));

            postLikeService.createAndSave(post.getId(), user.getId());
        }
    }

    public List<User> createUsers(int userCount) {

        for (long i=1; i<=userCount; i++) {
            User user = User.create("user" + i, "pass");
            userRepository.save(user);
        }

        return userRepository.findAll();
    }

    public void createTestComments(int commentCount, List<Post> posts, List<User> authors) {

        List<Post> findPosts = postRepository.findByIdIn(getAuthorIds(authors));
        List<User> findAuthors = userRepository.findByIdIn(getPostIds(posts));

        Random random = new Random();
        for (long i=1; i<=commentCount; i++) {
            Post post = findPosts.get(random.nextInt(posts.size()));

            Comment comment = Comment.create("content" + i, findAuthors.get(random.nextInt(findAuthors.size())));
            post.addComment(comment);
        }
    }

    public List<Post> createTestPosts(int postCount, int viewRange, List<User> authors) {

        List<User> findAuthors = userRepository.findByIdIn(getAuthorIds(authors));

        Random random = new Random();
        for (long i=1; i<=postCount; i++) {
            User author = findAuthors.get(random.nextInt(findAuthors.size()));
            Post post = Post.create("title" + i, "content" + (i + 1), author);
            setViews(post, random.nextInt(viewRange));
            postRepository.save(post);
        }

        return postRepository.findAll(null, Sort.unsorted());
    }

    public void deleteAll() {
        commentRepository.deleteAll();
        postLikeRepository.deleteAll();
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    private List<Long> getAuthorIds(List<User> authors) {
        return authors.stream()
                .map(User::getId)
                .toList();
    }

    private List<Long> getPostIds(List<Post> posts) {
        return posts.stream()
                .map(Post::getId)
                .toList();
    }

    private void setViews(Post post, long views) {
        try {
            Field viewsField = post.getClass().getDeclaredField("views");
            viewsField.setAccessible(true);
            viewsField.setLong(post, views);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
