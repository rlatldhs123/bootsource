package com.example.club.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices.RememberMeTokenAlgorithm;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.club.handler.ClubLoginSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // @PreAuthorize 어노테이션 활성화 시키는 어노테이션

public class SecurityConfig {

    @Bean

    SecurityFilterChain securityFilterChain(HttpSecurity http, RememberMeServices rememberMeServices) throws Exception {

        http
                // 접근 제한 경로를 전부 일일이 작성 하는 방법
                // .authorizeHttpRequests(authrize -> authrize
                // .requestMatchers("/", "auth", "static/**", "/img/*").permitAll()
                // .requestMatchers("/club/member").hasAnyRole("USER", "MANAGER", "ADMIN")
                // .requestMatchers("/club/manager").hasAnyRole("MANAGER")
                // .requestMatchers("/club/admin").hasAnyRole("ADMIN"))

                .authorizeHttpRequests(authrize -> authrize
                        .requestMatchers("/static/**", "/img/*").permitAll()
                        .anyRequest().permitAll())
                .formLogin(login -> login
                        .loginPage("/club/member/login").permitAll()
                        .successHandler(clubLoginSuccessHandler()))
                .oauth2Login(login -> login.successHandler(clubLoginSuccessHandler()))
                .rememberMe(remember -> remember.rememberMeServices(rememberMeServices))
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/club/member/logout"))
                        .logoutSuccessUrl("/"));

        return http.build();
    }

    // 자동 로그인 처리 -1) 쿠키 이용 2) 데이터베이스 이용
    @Bean
    RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
        RememberMeTokenAlgorithm encodingAlgorithm = RememberMeTokenAlgorithm.SHA256;
        TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices("myKey", userDetailsService,
                encodingAlgorithm);
        rememberMeServices.setTokenValiditySeconds(60 * 60 * 24 * 7); // 쿠키 만료시간 7 일

        return rememberMeServices;

    }

    // 암호화, 비밀번호 입력 값 검증 하기 위해 : PasswordEncoder
    // 단반향 암호화 : 압호화만 가능하고 복호화는 불가능
    @Bean // 객체 생성 해주는 어노테이션은 Bean 이다
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    ClubLoginSuccessHandler clubLoginSuccessHandler() {
        return new ClubLoginSuccessHandler();
    }

}
