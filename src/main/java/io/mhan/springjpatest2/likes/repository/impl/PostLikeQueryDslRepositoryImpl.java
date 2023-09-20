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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import static io.mhan.springjpatest2.likes.entity.QPostLike.postLike;
import static io.mhan.springjpatest2.posts.repository.impl.PostQueryDslRepositoryImpl.containsPostKeyword;

@Repository
@RequiredArgsConstructor
public class PostLikeQueryDslRepositoryImpl implements PostLikeQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PostLike> findByUserId(UUID authorId, Sort sort, PostKeyword postKeyword) {
        JPAQuery<PostLike> contentQuery = jpaQueryFactory
                .select(postLike)
                .from(postLike)
                .where(
                        eqAuthorId(authorId),
                        containsPostKeyword(postKeyword)
                )
                .orderBy(postLikeOrders(sort));

        List<PostLike> postLikes = contentQuery.fetch();

        return postLikes;
    }

    @Override
    public Page<PostLike> findByUserId(UUID authorId, PostKeyword postKeyword, Pageable pageable) {
        JPAQuery<PostLike> contentQuery = jpaQueryFactory
                .select(postLike)
                .from(postLike)
                .where(
                        eqAuthorId(authorId),
                        containsPostKeyword(postKeyword)
                )
                .orderBy(postLikeOrders(pageable.getSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<PostLike> content = contentQuery.fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(postLike.count())
                .from(postLike)
                .where(
                        eqAuthorId(authorId),
                        containsPostKeyword(postKeyword)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression eqAuthorId(UUID authorId) {
        return postLike.author.id.eq(authorId);
    }

    private OrderSpecifier<?>[] postLikeOrders(Sort sort) {
        Function<String, Expression<?>> expressionFunction = (property) -> switch (property) {
            case "created" -> postLike.created;
            default -> postLike.id;
        };

        return QueryDslUtils.getOrderSpecifiers(sort, expressionFunction);
    }
}
