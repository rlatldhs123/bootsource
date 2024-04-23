package com.example.club.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.club.constant.ClubMemberRole;
import com.example.club.dto.ClubAuthMemberDto;
import com.example.club.entity.ClubMember;
import com.example.club.repository.ClubMemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ClubOAuth2UserDetailService extends DefaultOAuth2UserService {

    private final ClubMemberRepository clubMemberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("++++++++++++++++++++++++++++++++++++++++++++++");

        log.info("userRequest {}", userRequest);
        String clientName = userRequest.getClientRegistration().getClientName();
        log.info("clientName {}", clientName);
        log.info(userRequest.getAdditionalParameters());
        log.info("++++++++++++++++++++++++++++++++++++++++++++++");
        OAuth2User oAuth2User = super.loadUser(userRequest);

        oAuth2User.getAttributes().forEach((key, value) -> {
            log.info("{} : {}", key, value);

        });

        /////////

        ClubMember clubMember = saveSocialMember(oAuth2User.getAttribute("email"));

        // 엔티티 ==> DTO

        ClubAuthMemberDto clubAuthMemberDto = new ClubAuthMemberDto(clubMember.getEmail(), clubMember.getPassword(),
                clubMember.isFromSocial(), clubMember.getRoleSet().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toSet()),
                oAuth2User.getAttributes());
        clubAuthMemberDto.setName(clubMember.getName());

        return clubAuthMemberDto;

    }

    private ClubMember saveSocialMember(String email) {
        Optional<ClubMember> result = clubMemberRepository.findByEmailAndFromSocial(email, true); // 소셜통해서 온 애들 리절트로 모음
        if (result.isPresent()) {
            return result.get();

        }

        ClubMember clubMember = ClubMember.builder()
                .email(email)
                .name(email)
                .password(passwordEncoder.encode("1111")) // 임의 지정 어차피 이 회원은 소셜 로그인을 통해 들어왔기 때문에 그냥 지정해놓음
                .fromSocial(true) // 소셜 통해서 옴? ㅇㅇ
                .build();
        // 롤 부여 코드
        clubMember.addMemberRole(ClubMemberRole.USER);
        clubMemberRepository.save(clubMember);
        log.info("소셜 회원 저장 완료!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        return clubMember;
    }

}
