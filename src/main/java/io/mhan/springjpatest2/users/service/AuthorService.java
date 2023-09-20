package io.mhan.springjpatest2.users.service;

import io.mhan.springjpatest2.users.entity.Author;
import io.mhan.springjpatest2.users.exception.AuthorException;
import io.mhan.springjpatest2.users.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static io.mhan.springjpatest2.base.exception.ErrorCode.AUTHOR_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public Author findActiveByIdElseThrow(UUID id) {
        return authorRepository.findActiveById(id)
                .orElseThrow(() -> new AuthorException(AUTHOR_NOT_FOUND));
    }

    public Author createAndSave(UUID id, String username) {
        Author author = Author.builder()
                .id(id)
                .username(username)
                .build();

        return authorRepository.save(author);
    }
}
