package com.example.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableMethodSecurity
@EnableWebSecurity
@Configuration

public class SecurityConfig {

    @Bean
    SecurityFilterChain seqFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/static/**", "/css/*", "/assets/*", "/img/*", "/js/*").permitAll()
                .requestMatchers("/board/read").permitAll()
                // get 주소창에서 혹시나 개발자나 잘 아는 사람들의 접근을 막기위해
                // 회원이 아니면 수정창 접속이 안되게 막아버린다 일단 모디파이 막아봄
                .requestMatchers("/board/modify").authenticated()
                .anyRequest().permitAll())
                .formLogin(login -> login.loginPage("/member/login").permitAll())
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                        .logoutSuccessUrl("/"));
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
