package io.mhan.springjpatest2.comments.service;

import io.mhan.springjpatest2.comments.entity.Comment;
import io.mhan.springjpatest2.comments.repository.CommentRepository;
import io.mhan.springjpatest2.users.entity.User;
import io.mhan.springjpatest2.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;

    @Transactional
    public Comment createAndSave(String content, Long userId) {

        User user = userService.findByIdElseThrow(userId);

        Comment comment = Comment.create(content, user);

        return commentRepository.save(comment);
    }
}
