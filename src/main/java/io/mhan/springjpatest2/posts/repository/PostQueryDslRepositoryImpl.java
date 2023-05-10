package io.mhan.springjpatest2.posts.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.posts.repository.vo.Keyword;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static io.mhan.springjpatest2.posts.entity.QPost.post;

@Repository
@RequiredArgsConstructor
public class PostQueryDslRepositoryImpl implements PostQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> findAll(Keyword keyword, Sort sort) {

        // 쿼리 생성
        JPAQuery<Post> contentQuery = jpaQueryFactory
                .select(post)
                .from(post)
                .where(containsKeyword(keyword))
                .orderBy(getOrderSpecifiers(sort));

        // 쿼리 실행
        List<Post> posts = contentQuery.fetch();

        return posts;
    }

    private OrderSpecifier[] getOrderSpecifiers(Sort sort) {
        return sort.stream()
                .map(order -> getOrderSpecifier(order))
                .distinct()
                .toArray(OrderSpecifier[]::new);
    }

    private OrderSpecifier getOrderSpecifier(Sort.Order o) {
        Order order = o.getDirection().isAscending() ? Order.ASC : Order.DESC;

        Expression<?> expression = switch (o.getProperty()) {
            case "created" -> post.created;
            default -> post.created;
        };
        return new OrderSpecifier(order, expression);
    }

    private BooleanExpression containsKeyword(Keyword keyword) {
        return keyword == null ? null : switch (keyword.getType()) {
            case TITLE -> post.title.contains(keyword.getValue());
            case TITLE_CONTENT ->
                    post.title.contains(keyword.getValue()).or(post.content.contains(keyword.getValue()));
        };
    }
}
