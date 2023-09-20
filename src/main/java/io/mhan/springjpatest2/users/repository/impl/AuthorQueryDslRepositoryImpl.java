package io.mhan.springjpatest2.users.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.mhan.springjpatest2.users.entity.Author;
import io.mhan.springjpatest2.users.repository.AuthorQueryDslRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static io.mhan.springjpatest2.users.entity.QAuthor.author;

@Repository
@RequiredArgsConstructor
public class AuthorQueryDslRepositoryImpl implements AuthorQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Author> findActiveById(UUID userId) {
        JPAQuery<Author> contentQuery = jpaQueryFactory
                .select(author)
                .from(author)
                .where(
                        eqAuthorId(userId),
                        eqIsDeleted(false)
                );

        Author content = contentQuery.fetchOne();

        return Optional.ofNullable(content);
    }

    private static BooleanExpression eqIsDeleted(boolean isDeleted) {
        return author.isDeleted.eq(isDeleted);
    }

    private static BooleanExpression eqAuthorId(UUID authorId) {
        return author.id.eq(authorId);
    }
}
