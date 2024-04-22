package com.example.club.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.example.club.constant.ClubMemberRole;
import com.example.club.entity.ClubMember;

import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@SpringBootTest
public class ClubRepositoryTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClubMemberRepository clubMemberRepository;

    @Test
    public void testInsert() {
        // 1~ 80 : USER, 81 ~ 90 : USER MANAGER , 91 ~ 100 : USER MANAGER ADMIN

        IntStream.rangeClosed(1, 100).forEach(i -> {
            ClubMember clubMember = ClubMember.builder()
                    .email("user" + i + "@soldesk.co.kr")
                    .name("사용지" + i)
                    .fromSocial(false)
                    .password(passwordEncoder.encode("1111"))
                    .build();

            clubMember.addMemberRole(ClubMemberRole.USER);

            if (i > 80) {
                clubMember.addMemberRole(ClubMemberRole.MANAGER);

            }
            if (i > 90) {
                clubMember.addMemberRole(ClubMemberRole.ADMIN);

            }
            clubMemberRepository.save(clubMember);
        });

    }

    // FetchType
    // EAGER : left outter join 기본으로 실행 됨
    // LAZY : select 문 두번으로 처리

    // 웹 개발시 EAGER 를 자주 사용하지 말자 => 처음부터 사용하지 않는 정보를 가지고 나올 피룡가 없음

    // @OneToMany , @OneToOne : 기본적으로 FetchType.EAGER 인 것들은 변경

    // @Transactional

    @Test

    public void testFind() {
        ClubMember clubmember = clubMemberRepository.findByEmailAndFromSocial("user95@soldesk.co.kr", false).get();
        System.out.println(clubmember);
    }

}
