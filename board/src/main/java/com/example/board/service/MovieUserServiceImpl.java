package com.example.board.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

// 컨트롤러에서 포스트 맵핑은 필요 없지만

// 이게 좀 특별한 로그인 메소드 같은 거임 시큐리티에서 사용하면서 로그인을 구현을 하려면 이게 필요하다

public class MovieUserServiceImpl implements UserDetailsService {

    // 이게 좀 특별한 로그인 메소드 같은 거임 시큐리티에서 사용하면서 로그인을 구현을 하려면 이게 필요하다
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    }

}
