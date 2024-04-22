package com.example.club.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ToString

public class ClubAuthMemberDto extends User {

    // db 에서 인증된 정보를 담을 객체

    // auth 로 봤던 그 정보들을 해당 클래스의 옮기는 그런 거라고 생각하면 됌

    private String email;
    private String name;
    private boolean fromSocial;

    public ClubAuthMemberDto(String username, String password, boolean fromSocial,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
        this.fromSocial = fromSocial;

    }

}
