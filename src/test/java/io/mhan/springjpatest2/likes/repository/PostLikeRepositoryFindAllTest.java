package io.mhan.springjpatest2.likes.repository;

import io.mhan.springjpatest2.base.init.TestService;
import io.mhan.springjpatest2.likes.entity.PostLike;
import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.posts.repository.vo.PostKeyword;
import io.mhan.springjpatest2.users.entity.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

import static io.mhan.springjpatest2.posts.repository.vo.PostKeywordType.TITLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@Transactional
@SpringBootTest
@TestInstance(PER_CLASS)
public class PostLikeRepositoryFindAllTest {

    @Autowired
    TestService testService;

    @Autowired
    PostLikeRepository postLikeRepository;

    User user1;

    @BeforeAll
    void beforeAll() {
        List<User> authors = testService.createUsers(100);
        List<Post> posts = testService.createTestPosts(100, 1000, authors);
        testService.createPostLikes(1000, posts, authors);
        user1 = authors.get(0);
    }

    @AfterAll
    void afterAll() {
        testService.deleteAll();
    }

    @Test
    @DisplayName("user1이 좋아한 post 조회")
    void t1() {
        List<PostLike> postLikes = postLikeRepository.findByUserId(user1.getId(), Sort.unsorted(), null);

        boolean result = postLikes.stream()
                .allMatch(postLike -> postLike.getUser().getId().equals(user1.getId()));
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("user1가 좋아한 post 조회 and 좋아한 post 최신순 조회")
    void t2() {
        Sort.Order order = Sort.Order.desc("created");
        Sort sort = Sort.by(order);
        List<PostLike> postLikes = postLikeRepository.findByUserId(user1.getId(), sort, null);

        boolean result = postLikes.stream()
                .allMatch(postLike -> postLike.getUser().getId().equals(user1.getId()));
        assertThat(result).isTrue();
        assertThat(postLikes).isSortedAccordingTo(Comparator.comparing(PostLike::getCreated, Comparator.reverseOrder()));
    }

    @Test
    @DisplayName("user1가 좋아한 post 조회 and 좋아한 post 최신순 조회 and keyword title title이 포함되게 검색")
    void t3() {
        PostKeyword keyword = PostKeyword.builder()
                .type(TITLE)
                .value("title")
                .build();
        Sort.Order order = Sort.Order.desc("created");
        Sort sort = Sort.by(order);
        List<PostLike> postLikes = postLikeRepository.findByUserId(user1.getId(), sort, keyword);

        boolean result = postLikes.stream()
                .allMatch(postLike -> postLike.getUser().getId().equals(user1.getId()));
        boolean result2 = postLikes.stream()
                .allMatch(postLike -> postLike.getPost().getTitle().contains(keyword.getValue()));

        assertThat(result).isTrue();
        assertThat(result2).isTrue();
        assertThat(postLikes).isSortedAccordingTo(Comparator.comparing(PostLike::getCreated, Comparator.reverseOrder()));
    }

    @Test
    @DisplayName("user1가 좋아한 post 조회 and 좋아요한 최신순 and page=0 size=10")
    void t4() {
        Sort.Order order = Sort.Order.desc("created");
        Sort sort = Sort.by(order);
        Pageable pageable = PageRequest.of(0, 10, sort);

        Page<PostLike> page = postLikeRepository.findByUserId(user1.getId(), null, pageable);

        List<PostLike> content = page.getContent();

        assertThat(page.isFirst()).isTrue();
        assertThat(page.getSize()).isEqualTo(pageable.getPageSize());
        assertThat(page.getNumber()).isEqualTo(pageable.getOffset());
        assertThat(content).isSortedAccordingTo(Comparator.comparing(PostLike::getCreated, Comparator.reverseOrder()));
    }

}
