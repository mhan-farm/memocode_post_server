package io.mhan.springjpatest2.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class ApiSecurityConfig {

    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    public SecurityFilterChain apiChain(HttpSecurity http) throws Exception {

        http
                .securityMatcher("/api/v1/**")
                .formLogin().disable()
                .sessionManagement(s -> s
                        .sessionCreationPolicy(STATELESS)
                )
                .csrf().disable()
                .cors(c -> c
                        .configurationSource(corsConfigurationSource)
                )
                .authorizeHttpRequests(a -> a
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}
