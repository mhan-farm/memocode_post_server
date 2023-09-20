package io.mhan.springjpatest2.posts.repository.impl;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.mhan.springjpatest2.base.utils.QueryDslUtils;
import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.posts.repository.PostQueryDslRepository;
import io.mhan.springjpatest2.posts.repository.vo.PostKeyword;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import static io.mhan.springjpatest2.posts.entity.QPost.post;

@Repository
@Transactional
@RequiredArgsConstructor
public class PostQueryDslRepositoryImpl implements PostQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Post> findPublicPostAll(PostKeyword keyword, Pageable pageable) {

        Predicate[] where = {
                containsPostKeyword(keyword),
                eqIsDeleted(false),
                post.isPrivate.eq(false)
        };

        // 쿼리 생성
        JPAQuery<Post> contentQuery = jpaQueryFactory
                .select(post)
                .from(post)
                .where(where)
                .orderBy(postOrders(pageable.getSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        // 쿼리 실행
        List<Post> content = contentQuery.fetch();

        JPAQuery<Long> countQuery = getCountQuery(where);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<Post> findByAuthorId(UUID authorId, PostKeyword keyword, Pageable pageable) {

        Predicate[] where = {
                eqAuthorId(authorId),
                containsPostKeyword(keyword),
                eqIsDeleted(false)
        };

        // 쿼리 생성
        JPAQuery<Post> contentQuery = jpaQueryFactory
                .select(post)
                .from(post)
                .where(where)
                .orderBy(postOrders(pageable.getSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        // 쿼리 실행
        List<Post> content = contentQuery.fetch();

        JPAQuery<Long> countQuery = getCountQuery(where);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Optional<Post> findActiveById(UUID postId) {
        JPAQuery<Post> contentQuery = jpaQueryFactory
                .select(post)
                .from(post)
                .where(
                        eqPostId(postId),
                        eqIsDeleted(false)
                );

        Post content = contentQuery.fetchOne();

        return Optional.ofNullable(content);
    }

    private static BooleanExpression eqPostId(UUID postId) {
        return post.id.eq(postId);
    }

    private JPAQuery<Long> getCountQuery(Predicate... where) {
        return jpaQueryFactory
                .select(post.count())
                .from(post)
                .where(where);
    }

    private static BooleanExpression eqIsDeleted(boolean isDeleted) {
        return post.isDeleted.eq(isDeleted);
    }

    private static BooleanExpression eqAuthorId(UUID authorId) {
        return post.author.id.eq(authorId);
    }

    private OrderSpecifier<?>[] postOrders(Sort sort) {

        Function<String, Expression<?>> expressionFunction = (property) -> switch (property) {
            case "created" -> post.id;
            case "comments" -> post.commentCount;
            case "likes" -> post.likeCount;
            case "views" -> post.views;
            default -> post.id;
        };

        return QueryDslUtils.getOrderSpecifiers(sort, expressionFunction);
    }

    public static BooleanExpression containsPostKeyword(PostKeyword keyword) {
        if (keyword == null) {
            return null;
        }

        if (keyword.getValue() == null || keyword.getValue().isBlank()) {
            return null;
        }

        if (keyword.getType() == null) {
            return null;
        }

        return switch (keyword.getType()) {
            case TITLE -> post.title.contains(keyword.getValue());
            case TITLE_CONTENT ->
                    post.title.contains(keyword.getValue()).or(post.content.contains(keyword.getValue()));
            case AUTHOR -> post.author.username.contains(keyword.getValue());
        };
    }
}
