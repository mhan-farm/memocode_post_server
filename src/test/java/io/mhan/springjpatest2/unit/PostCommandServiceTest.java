package io.mhan.springjpatest2.unit;

import io.mhan.springjpatest2.posts.request.PostCreateRequest;
import io.mhan.springjpatest2.posts.service.PostCommandService;
import io.mhan.springjpatest2.users.entity.Author;
import io.mhan.springjpatest2.users.service.AuthorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class PostCommandServiceTest {

    @Autowired
    private PostCommandService postCommandService;

    @Autowired
    private AuthorService authorService;

    @Test
    @DisplayName("게시글 생성")
    void createPost() {
        // given
        Author author = authorService.createAndSave(UUID.randomUUID(), "user1");
        PostCreateRequest request = PostCreateRequest.builder()
                .title("title1")
                .content("content1")
                .tags("tag1,tag2,tag3")
                .build();

        // when
        UUID postId = postCommandService.register(author.getId(), request);

        // then
        assertThat(postId).isNotNull();
    }
}
