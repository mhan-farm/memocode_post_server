package io.mhan.springjpatest2.comments.repository.impl;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.mhan.springjpatest2.base.utils.QueryDslUtils;
import io.mhan.springjpatest2.comments.entity.Comment;
import io.mhan.springjpatest2.comments.repository.CommentQueryDslRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import static io.mhan.springjpatest2.comments.entity.QComment.comment;

@Repository
@RequiredArgsConstructor
public class CommentQueryDslRepositoryImpl implements CommentQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Comment> findActiveAllByPostId(UUID postId, Pageable pageable) {

        Predicate[] where = {
                eqPostId(postId),
                eqIsDeleted(false)
        };

        JPAQuery<Comment> contentQuery = jpaQueryFactory
                .select(comment)
                .from(comment)
                .where(where)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(commentOrders(pageable.getSort()));

        List<Comment> content = contentQuery.fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(comment.count())
                .from(comment)
                .where(where);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Optional<Comment> findActiveById(Long commentId) {
        JPAQuery<Comment> contentQuery = jpaQueryFactory
                .select(comment)
                .from(comment)
                .where(
                        comment.id.eq(commentId),
                        eqIsDeleted(false)
                );

        Comment content = contentQuery.fetchOne();

        return Optional.ofNullable(content);
    }

    private static BooleanExpression eqIsDeleted(boolean isDeleted) {
        return comment.isDeleted.eq(isDeleted);
    }

    private static BooleanExpression eqPostId(UUID postId) {
        return comment.post.id.eq(postId);
    }

    private OrderSpecifier<?>[] commentOrders(Sort sort) {

        Function<String, Expression<?>> expressionFunction = (property) -> switch (property) {
            default -> comment.id;
        };

        return QueryDslUtils.getOrderSpecifiers(sort, expressionFunction);
    }
}
