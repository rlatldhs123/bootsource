package com.example.club.service;

import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.club.constant.ClubMemberRole;
import com.example.club.dto.ClubAuthMemberDto;
import com.example.club.dto.ClubMemberDto;
import com.example.club.entity.ClubMember;
import com.example.club.repository.ClubMemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ClubUserDetailService implements UserDetailsService, ClubMemberService {

    private final ClubMemberRepository clubMemberRepository;
    private final PasswordEncoder passwordEncoder;

    // UserDetails <-----------User <-------------CustomUser
    // User 로 리턴 하던지 Or UserDetails 로 리턴 하던지 해야함

    // 로그인폼에서 포스트를 보냈지만
    // 어떻게 컨트롤러도 없이 서비스로 도착했지
    // 이 주석을 쓸때는 포스트맵핑 컨트롤러를 하나도 만들지 않았다 왜?

    @Override
    // loadUserByUsername 로그인 담당 메소드이다 다른 거 못씀 무조건 이거 쓰셈
    // 이 메소드는 로그인 작업을 담당 하는 메소드이다
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("로그인 요청 {} ", username);

        Optional<ClubMember> result = clubMemberRepository.findByEmailAndFromSocial(username, false);

        if (!result.isPresent())
            throw new UsernameNotFoundException("해당 이메일 혹은 소셜 확인");

        ClubMember clubMember = result.get();
        log.info("===========================");
        log.info(clubMember);
        log.info("===========================");

        // 엔티티 ==> DTO

        ClubAuthMemberDto clubAuthMemberDto = new ClubAuthMemberDto(clubMember.getEmail(), clubMember.getPassword(),
                clubMember.isFromSocial(), clubMember.getRoleSet().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toSet()));
        clubAuthMemberDto.setName(clubMember.getName());

        return clubAuthMemberDto;

    }

    @Override
    public String register(ClubMemberDto member) {
        // dto ==> 엔티티 로 변경

        // DTO 원본 비밀번호 == 암호화 시켜야 함

        ClubMember clubMember = ClubMember.builder()
                .email(member.getEmail())
                .name(member.getName())
                .fromSocial(member.isFromSocial())
                .password(passwordEncoder.encode(member.getPassword()))

                .build();
        // 롤 부여
        clubMember.addMemberRole(ClubMemberRole.ADMIN);

        return clubMemberRepository.save(clubMember).getEmail();
    }

}
