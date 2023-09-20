package io.mhan.springjpatest2.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class ApiSecurityConfig {

    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    public SecurityFilterChain apiChain(HttpSecurity http) throws Exception {

        http
                .formLogin().disable()
                .sessionManagement(s -> s
                        .sessionCreationPolicy(STATELESS)
                )
                .csrf().disable()
                .cors(c -> c
                        .configurationSource(corsConfigurationSource)
                )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::opaqueToken);

        return http.build();
    }
}
