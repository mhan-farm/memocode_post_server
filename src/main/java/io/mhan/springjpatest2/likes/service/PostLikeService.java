package io.mhan.springjpatest2.likes.service;

import io.mhan.springjpatest2.likes.entity.PostLike;
import io.mhan.springjpatest2.likes.repository.PostLikeRepository;
import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.posts.service.PostService;
import io.mhan.springjpatest2.users.entity.User;
import io.mhan.springjpatest2.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final UserService userService;
    private final PostService postService;

    @Transactional
    public PostLike createAndSave(Long postId, Long userId) {

        Optional<PostLike> opPostLike = findByPostIdAndUserId(postId, userId);

        if (opPostLike.isPresent()) {
            return opPostLike.get();
        }

        Post post = postService.findByIdElseThrow(postId);
        User user = userService.findByIdElseThrow(userId);

        PostLike postLike = postLikeRepository.save(PostLike.create(post, user));
        postService.increaseLike(post.getId());

        return postLike;
    }

    private Optional<PostLike> findByPostIdAndUserId(Long postId, Long userId) {
        return postLikeRepository.findByPostIdAndUserId(postId, userId);
    }
}
