package io.mhan.springjpatest2.users.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.mhan.springjpatest2.users.entity.User;
import io.mhan.springjpatest2.users.repository.UserQueryDslRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static io.mhan.springjpatest2.users.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserQueryDslRepositoryImpl implements UserQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<User> findActiveById(Long userId) {
        JPAQuery<User> contentQuery = jpaQueryFactory
                .select(user)
                .from(user)
                .where(
                        eqUserId(userId),
                        eqIsDeleted(false)
                );

        User content = contentQuery.fetchOne();

        return Optional.ofNullable(content);
    }

    private static BooleanExpression eqIsDeleted(boolean isDeleted) {
        return user.isDeleted.eq(isDeleted);
    }

    private static BooleanExpression eqUserId(Long userId) {
        return user.id.eq(userId);
    }
}
