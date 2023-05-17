package io.mhan.springjpatest2.users.service;

import io.mhan.springjpatest2.users.entity.User;
import io.mhan.springjpatest2.users.exception.UserException;
import io.mhan.springjpatest2.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static io.mhan.springjpatest2.base.exception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByIdElseThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));
    }
}
