package com.example.movie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableMethodSecurity
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/assets/**", "/css/**", "/js/**", "/auth").permitAll()
                .requestMatchers("/movie/list", "/movie/read").permitAll()
                .requestMatchers("/upload/display").permitAll()
                .requestMatchers("/reviews/**").permitAll()
                .requestMatchers("/member/register").permitAll()
                .anyRequest().authenticated());
        // login 페이지는 /member/login 경로요청 해야함
        http.formLogin(login -> login
                .loginPage("/member/login").permitAll()
                .defaultSuccessUrl("/movie/list", true));

        http.logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/"));

        // http.csrf(csrf -> csrf.disable()); // csrf 필터 비활성화
        // csfr 가 비활성화 되지 않으면 이제 get 을 제외한 모든 방식은 csrf 토큰이 필요함
        // ==> 타임리프 에서 form:action 을 삽입하면 자동으로 만들어 준다

        // 403 에러가 발생시 확인 해야 될 것

        // 1) csrf 토큰이 포함 되었는가?
        // 2) 입력값을 확인해 봐야함

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
