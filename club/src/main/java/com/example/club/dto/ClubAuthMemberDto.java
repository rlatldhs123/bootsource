package com.example.club.dto;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ToString
// 이 DTO 는 항상 User 를 꼭 상속 받아야 함
public class ClubAuthMemberDto extends User implements OAuth2User {

    // db 에서 인증된 정보를 담을 객체

    // auth 로 봤던 그 정보들을 해당 클래스의 옮기는 그런 거라고 생각하면 됌

    private String email;
    private String password;
    private String name;
    private boolean fromSocial;

    // 소셜 로그인에서 넘어오는 값을 담는 객체
    private Map<String, Object> attr;

    public ClubAuthMemberDto(String username, String password, boolean fromSocial,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
        this.fromSocial = fromSocial;
        this.password = password;

    }

    public ClubAuthMemberDto(String username, String password, boolean fromSocial,
            Collection<? extends GrantedAuthority> authorities, Map<String, Object> attr) {
        this(username, password, fromSocial, authorities);
        this.attr = attr;
   
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }

}
