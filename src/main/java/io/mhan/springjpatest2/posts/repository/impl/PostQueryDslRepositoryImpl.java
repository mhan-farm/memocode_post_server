package io.mhan.springjpatest2.posts.repository.impl;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.mhan.springjpatest2.base.utils.QueryDslUtils;
import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.posts.exception.PostException;
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
import java.util.function.Function;

import static io.mhan.springjpatest2.base.exception.ErrorCode.INCORRECT_SEQUENCE_NUMBER_POST;
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

        Predicate[] where = {
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
    public Page<Post> findByAuthorId(Long authorId, PostKeyword keyword, Pageable pageable) {

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
    public Optional<Post> findActiveById(Long postId) {
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

    @Override
    public long countActiveAllByAuthorIdAndParentPostIsNull(Long authorId) {

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(post.count())
                .from(post)
                .where(
                        eqAuthorId(authorId),
                        eqIsDeleted(false),
                        post.parentPost.isNull()
                );

        Long count = countQuery.fetchOne();

        return count;
    }

    @Override
    public long countActiveAllByAuthorIdAndParentPost(Long authorId, Post parentPost) {

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(post.count())
                .from(post)
                .where(
                        eqAuthorId(authorId),
                        eqIsDeleted(false),
                        eqParentPost(parentPost)
                );

        Long count = countQuery.fetchOne();

        return count;
    }

    @Override
    public List<Post> findActiveAllByAuthorIdAndParentPostOrderBySequenceASC(Long authorId, Post parentPost) {
        JPAQuery<Post> contentQuery = jpaQueryFactory
                .select(post)
                .from(post)
                .where(
                        eqAuthorId(authorId),
                        eqIsDeleted(false),
                        eqParentPost(parentPost)
                )
                .orderBy(post.sequence.asc());

        List<Post> content = contentQuery.fetch();

        return content;
    }

    @Override
    public List<Post> findActiveAllByAuthorIdAndParentPostBetweenSequenceOrderBySequenceASC(
            Long authorId, Post parentPost, Long startSequence, Long endSequence) {
        JPAQuery<Post> contentQuery = jpaQueryFactory
                .select(post)
                .from(post)
                .where(
                        eqAuthorId(authorId),
                        eqIsDeleted(false),
                        eqParentPost(parentPost),
                        post.sequence.between(startSequence, endSequence)
                )
                .orderBy(post.sequence.asc());

        List<Post> content = contentQuery.fetch();

        return content;
    }

    private BooleanExpression eqParentPost(Post parentPost) {
        if (parentPost == null) {
            return post.parentPost.isNull();
        }

        if (parentPost.getId() <= 0) {
            throw new PostException(INCORRECT_SEQUENCE_NUMBER_POST);
        }

        return post.parentPost.id.eq(parentPost.getId());
    }

    private static BooleanExpression eqPostId(Long postId) {
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

    private static BooleanExpression eqAuthorId(Long authorId) {
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
