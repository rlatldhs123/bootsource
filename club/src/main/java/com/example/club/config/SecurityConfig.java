package com.example.club.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SecurityConfig {

    @Bean

    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.build();
    }

    // 암호화, 비밀번호 입력 값 검증 하기 위해 : PasswordEncoder
    // 단반향 암호화 : 압호화만 가능하고 복호화는 불가능
    @Bean // 객체 생성 해주는 어노테이션은 Bean 이다
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
