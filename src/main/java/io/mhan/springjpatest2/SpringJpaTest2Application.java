package io.mhan.springjpatest2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringJpaTest2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringJpaTest2Application.class, args);
    }

}
