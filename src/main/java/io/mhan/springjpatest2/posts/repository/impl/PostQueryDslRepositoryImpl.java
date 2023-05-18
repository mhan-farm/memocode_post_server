package io.mhan.springjpatest2.posts.repository.impl;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
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
import java.util.function.Function;

import static io.mhan.springjpatest2.posts.entity.QPost.post;

@Repository
@Transactional
@RequiredArgsConstructor
public class PostQueryDslRepositoryImpl implements PostQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> findAll(PostKeyword keyword, Sort sort) {

        // 쿼리 생성
        JPAQuery<Post> contentQuery = jpaQueryFactory
                .select(post)
                .from(post)
                .where(containsPostKeyword(keyword))
                .orderBy(postOrders(sort));

        // 쿼리 실행
        List<Post> posts = contentQuery.fetch();

        return posts;
    }

    @Override
    public Page<Post> findAll(PostKeyword keyword, Pageable pageable) {
        // 쿼리 생성
        JPAQuery<Post> contentQuery = jpaQueryFactory
                .select(post)
                .from(post)
                .where(containsPostKeyword(keyword))
                .orderBy(postOrders(pageable.getSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        // 쿼리 실행
        List<Post> content = contentQuery.fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(post.count())
                .from(post)
                .where(containsPostKeyword(keyword));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private OrderSpecifier<?>[] postOrders(Sort sort) {
        Function<String, Expression<?>> expressionFunction = (property) -> switch (property) {
            case "created" -> post.created;
            case "comments" -> post.commentCount;
            case "likes" -> post.likeCount;
            case "views" -> post.views;
            default -> post.created;
        };

        return QueryDslUtils.getOrderSpecifiers(sort, expressionFunction);
    }

    public static BooleanExpression containsPostKeyword(PostKeyword keyword) {
        if (keyword == null) {
            return null;
        }

        if (keyword.getValue() == null) {
            return null;
        }

        return switch (keyword.getType()) {
                    case TITLE -> post.title.contains(keyword.getValue());
                    case TITLE_CONTENT ->
                            post.title.contains(keyword.getValue()).or(post.content.contains(keyword.getValue()));
                };
    }
}
