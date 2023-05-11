package io.mhan.springjpatest2.posts.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.mhan.springjpatest2.base.utils.QueryDslUtils;
import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.posts.repository.vo.Keyword;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
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
    public List<Post> findAll(Keyword keyword, Sort sort) {

        // 쿼리 생성
        JPAQuery<Post> contentQuery = jpaQueryFactory
                .select(post)
                .from(post)
                .where(containsKeyword(keyword))
                .orderBy(postOrders(sort));

        // 쿼리 실행
        List<Post> posts = contentQuery.fetch();

        return posts;
    }

    public static OrderSpecifier<?>[] postOrders(Sort sort) {
        Function<String, Expression<?>> expressionFunction = (property) -> switch (property) {
            case "created" -> post.created;
            case "comments" -> post.commentCount;
            case "likes" -> post.likeCount;
            case "views" -> post.views;
            default -> post.created;
        };

        return QueryDslUtils.getOrderSpecifiers(sort, expressionFunction);
    }


    public static BooleanExpression containsKeyword(Keyword keyword) {
        return keyword == null ? null : switch (keyword.getType()) {
            case TITLE -> post.title.contains(keyword.getValue());
            case TITLE_CONTENT ->
                    post.title.contains(keyword.getValue()).or(post.content.contains(keyword.getValue()));
        };
    }
}
