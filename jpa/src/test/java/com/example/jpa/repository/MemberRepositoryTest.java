package com.example.jpa.repository;

import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Member;
import com.example.jpa.entity.RoleType;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertTest() {
        IntStream.rangeClosed(1, 50).forEach(i -> {
            Member member = Member.builder()
                    .id("user" + i)
                    .userName("user" + i)
                    .age(i)
                    .roleType(RoleType.USER)
                    .description("user" + i)
                    .build();
            memberRepository.save(member);

        });

    }

    @Test
    public void readTest() {
        System.out.println(memberRepository.findById("user1"));
        // 특정 이름을 조회 간단한 쿼리는 스프링에서 만들어주는 규칙에 마주처 만들어 주는 메소드를 활용 할 수 잇다
        memberRepository.findByUserName("user1").forEach(member -> {
            System.out.println(member);
        });

    }

    @Test
    public void updateTest() {
        Optional<Member> result = memberRepository.findById("user1");

        result.ifPresent(member -> {
            member.setRoleType(RoleType.ADMIN);
            memberRepository.save(member);
        });

    }

    @Test
    public void deleteTest() {
        Member member = memberRepository.findById("user20").get();
        memberRepository.delete(member);
    }

}
