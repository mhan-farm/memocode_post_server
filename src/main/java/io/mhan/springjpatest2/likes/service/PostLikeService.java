package io.mhan.springjpatest2.likes.service;

import io.mhan.springjpatest2.likes.entity.PostLike;
import io.mhan.springjpatest2.likes.repository.PostLikeRepository;
import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.posts.service.PostQueryService;
import io.mhan.springjpatest2.users.entity.Author;
import io.mhan.springjpatest2.users.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final AuthorService userService;
    private final PostQueryService postService;

    @Transactional
    public PostLike createAndSave(UUID postId, UUID authorId) {

        Optional<PostLike> opPostLike = findByPostIdAndAuthorId(postId, authorId);

        if (opPostLike.isPresent()) {
            return opPostLike.get();
        }

        Post post = postService.findActiveByIdElseThrow(postId);
        Author author = userService.findActiveByIdElseThrow(authorId);

        PostLike postLike = postLikeRepository.save(PostLike.create(post, author));

        return postLike;
    }

    private Optional<PostLike> findByPostIdAndAuthorId(UUID postId, UUID userId) {
        return postLikeRepository.findByPostIdAndAuthorId(postId, userId);
    }
}
