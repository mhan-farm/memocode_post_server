package io.mhan.springjpatest2.likes.repository.impl;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.mhan.springjpatest2.base.utils.QueryDslUtils;
import io.mhan.springjpatest2.likes.entity.PostLike;
import io.mhan.springjpatest2.likes.repository.PostLikeQueryDslRepository;
import io.mhan.springjpatest2.posts.repository.vo.PostKeyword;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Function;

import static io.mhan.springjpatest2.likes.entity.QPostLike.postLike;
import static io.mhan.springjpatest2.posts.repository.impl.PostQueryDslRepositoryImpl.containsPostKeyword;

@Repository
@RequiredArgsConstructor
public class PostLikeQueryDslRepositoryImpl implements PostLikeQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PostLike> findByUserId(Long userId, Sort sort, PostKeyword postKeyword) {
        JPAQuery<PostLike> contentQuery = jpaQueryFactory
                .select(postLike)
                .from(postLike)
                .where(
                        eqUserId(userId),
                        containsPostKeyword(postKeyword)
                )
                .orderBy(postLikeOrders(sort));

        List<PostLike> postLikes = contentQuery.fetch();

        return postLikes;
    }

    private BooleanExpression eqUserId(Long userId) {
        return postLike.user.id.eq(userId);
    }

    private OrderSpecifier<?>[] postLikeOrders(Sort sort) {
        Function<String, Expression<?>> expressionFunction = (property) -> switch (property) {
            case "created" -> postLike.created;
            default -> postLike.id;
        };

        return QueryDslUtils.getOrderSpecifiers(sort, expressionFunction);
    }
}
