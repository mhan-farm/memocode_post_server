package io.mhan.springjpatest2.base.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryDslConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory queryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public ApplicationRunner printTransactionIsolationLevel() {
        return args -> {
            System.out.println("Current Transaction Isolation Level: " + entityManager
                    .createNativeQuery("SELECT @@transaction_isolation")
                    .getSingleResult());
        };
    }
}
