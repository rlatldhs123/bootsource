package com.example.board.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.board.constant.MemberRole;
import com.example.board.dto.MemberAuthDto;
import com.example.board.dto.MemberDto;
import com.example.board.entity.Member;
import com.example.board.repository.MemberRepositoty;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberDetailService implements UserDetailsService, MemberService {

    private final MemberRepositoty memberRepositoty;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // id == email == username
        // 시큐리티에서는 username 이 아이디에 해당 되기 때문에

        Optional<Member> result = memberRepositoty.findById(username);

        if (!result.isPresent()) {
            throw new UsernameNotFoundException("이메일을 확인해 주세요");

        }
        Member member = result.get();
        // 엔티티 ==> DTO

        // 시큐리티 로그인 member 정보 + 허가와 관련된 정보(사이트를 접근 여부)들도 포함

        MemberDto memberDto = MemberDto.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .name(member.getName())
                .memberRole(member.getMemberRole()).build();

        return new MemberAuthDto(memberDto);

    }

    @Override
    public void register(MemberDto insertDto) throws Exception {

        log.info("회원 가입 요청 {}", insertDto);

        // 중복 이메일
        validateDuplicationMember(insertDto.getEmail());

        // 중복 이메일(PK)롣 검사 해야 함

        // select ==> 해서 입력한 값이 중복이면 업데이트 세이브 작동
        // select ==> 해서 입력한 값이 중복이 아니면 인서트 세이브 작동
        Member member = Member.builder()
                .email(insertDto.getEmail())
                .name(insertDto.getName())
                .password(passwordEncoder.encode(insertDto.getPassword()))
                .memberRole(MemberRole.MEMBER).build();
        memberRepositoty.save(member);

    }

    // 하단에 throw new Exception("이미 가입된 회원입니다"); 이렇게 쓴다면 throws Exception 이렇게 엑셥션을 던짐
    private void validateDuplicationMember(String email) throws Exception {
        Optional<Member> member = memberRepositoty.findById(email);
        if (member.isPresent()) {
            // throw : 강제 익셉션 발생 시키기
            throw new IllegalStateException("이미 가입된 회원입니다");

        }
    }

}
