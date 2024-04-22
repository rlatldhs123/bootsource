package com.example.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class SecurityTest {

    // SecurityConfig 의 passwordEncoder() 가 실행 되면서 주입 됨
    @Autowired
    private PasswordEncoder passwordEncoder; // 비밀번호 암호화 , 원래 비밀번화와 암호화된 비밀번의 매치 여부(matches)

    @Test
    public void testEncoder() {
        String password = "1111";

        // 원 비밀번호 암호화
        // {bcrypt}$2a$10$kDtInoaAf0w6NNzIGXBG6ubmq.yYn47kWO1qBUU7xqDBJ2piOvx96
        String encodePassword = passwordEncoder.encode(password);

        System.out.println("password : " + password + ", encode password : " + encodePassword);

        System.out.println(passwordEncoder.matches(password, encodePassword));
    }

}
