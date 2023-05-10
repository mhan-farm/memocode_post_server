package io.mhan.springjpatest2.base.init;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitData {

    @Bean
    public ApplicationRunner defaultInitData(TestService testService) {
        return args -> testService.testData();
    }
}
