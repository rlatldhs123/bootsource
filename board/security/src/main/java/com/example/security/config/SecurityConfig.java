package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity // 웹에서 시큐리티 적용이 가능 하도록 해주는 어노테이션
@Configuration // ==@Component(@Controller, @Service) : 객체 생성
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 스프링 시큐리티는

        // 접근제한 개념이 있다
        // => 회원(member, admin, guest ...)에 대한 요청별 접근 제한

        // http 로 들어오는 요청이 "/" 이라면 커미션을 남기지 말기

        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/security/guest", "/auth").permitAll()
                        .requestMatchers("/security/member").hasRole("USER")
                        .requestMatchers("/security/admin").hasRole("ADMIN"))

                // 인증 처리 (웹에서는 대부분 폼 로그인으로 작업)
                // .formLogin(Customizer.withDefaults()); // 디펄트 로그인 페이지 로 연겨 ㄹ시켜줌
                // 이렇게 하면 우리가 만든 커스텀 로그인 페이지를 갈 수 있다 퍼미션을 줘야지 갈 수 있다
                .formLogin(login -> login.loginPage("/member/login").permitAll()
                        .usernameParameter("userid") // username 요소 이름 변경시
                        .passwordParameter("pwd") // password 요소 이름 변경
                        .successForwardUrl("")) // 로그인 성공 후 가야할 곳 지정

                .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) // 커스텀 로그아웃
                        .logoutSuccessUrl("/")); // 로그아웃 성공시 default 로그인 페이지로 이동
        return http.build();
        // 해당 코드의 뜻은 이제부터 어떤 작업이 들어오든 필터를 한다는 의미
    }

    @Bean
    PasswordEncoder passwordEncoder() {

        // passwordEncoder : 이게 뭐냐? 비밀번호를 암호화 시켜주는 객체이다
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();

    }

    // 임시 - 데이터베이스에 인증을 요청하는 객체
    // InMemoryUserDetailsManager 메모리에 등록해 놓고 임시로 사용
    @Bean
    UserDetailsService users() {
        UserDetails user = User.builder().username("user1")
                .password("{bcrypt}$2a$10$kDtInoaAf0w6NNzIGXBG6ubmq.yYn47kWO1qBUU7xqDBJ2piOvx96").roles("USER").build();
        UserDetails admin = User.builder().username("admin1")
                .password("{bcrypt}$2a$10$kDtInoaAf0w6NNzIGXBG6ubmq.yYn47kWO1qBUU7xqDBJ2piOvx96")
                .roles("ADMIN", "USER")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}
