package com.example.club.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ClubUserDetailService implements UserDetailsService {

    // UserDetails <-----------User <-------------CustomUser
    // User 로 리턴 하던지 Or UserDetails 로 리턴 하던지 해야함

    @Override
    // loadUserByUsername 로그인 담당 메소드이다 다른 거 못씀 무조건 이거 쓰셈
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("로그인 요청 {} ", username);

        return null;

    }

}
