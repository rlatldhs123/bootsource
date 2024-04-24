package com.example.board.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data

public class MemberAuthDto extends User {

    // 시큐리티 로그인 member 정보 + 허가와 관련된 정보(사이트를 접근 여부)들도 포함
    // 위 주석의 목적을 위한 DTO클래스이다
    private MemberDto memberDto;

    public MemberAuthDto(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        // Collection<? extends GrantedAuthority> == GrantedAuthority 가 부모인 자식인 객체
        // 만 담을 수 있다

        super(username, password, authorities);

    }

    public MemberAuthDto(MemberDto memberDto) {
        super(memberDto.getEmail(), memberDto.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + memberDto.getMemberRole())));
        this.memberDto = memberDto;
    }

}
