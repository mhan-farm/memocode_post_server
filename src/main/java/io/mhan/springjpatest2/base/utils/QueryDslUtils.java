package io.mhan.springjpatest2.base.utils;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.data.domain.Sort;

import java.util.function.Function;

public class QueryDslUtils {
    public static OrderSpecifier<?>[] getOrderSpecifiers(Sort sort, Function<String, Expression<?>> expressionFunction) {
        return sort.stream()
                .map(order -> getOrderSpecifier(order, expressionFunction))
                .distinct()
                .toArray(OrderSpecifier[]::new);
    }

    public static OrderSpecifier<?> getOrderSpecifier(Sort.Order o, Function<String, Expression<?>> expressionFunction) {
        Order order = o.getDirection().isAscending() ? Order.ASC : Order.DESC;

        Expression<?> expression = expressionFunction.apply(o.getProperty());

        return new OrderSpecifier(order, expression);
    }
}
