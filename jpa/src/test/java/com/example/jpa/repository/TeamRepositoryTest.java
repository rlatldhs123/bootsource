package com.example.jpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Team;
import com.example.jpa.entity.TeamMember;

import jakarta.transaction.Transactional;

@SpringBootTest
public class TeamRepositoryTest {
        // 여기는 final이 들어올 수 없어서 autowired 가 강제됨
        @Autowired
        private TeamRepository teamRepository;

        @Autowired
        private TeamMemberRepository teamMemberRepository;

        @Test
        public void insertTest() {
                // 팀 정보 삽입
                Team team1 = teamRepository.save(Team.builder()
                                .id("team1")
                                .name("슈퍼민주주의")
                                .build());
                Team team2 = teamRepository.save(Team.builder()
                                .id("team2")
                                .name("통제된민주주의")
                                .build());
                Team team3 = teamRepository.save(Team.builder()
                                .id("team3")
                                .name("정돈된민주주의")
                                .build());

                // 팀 멤버 정보 삽입
                teamMemberRepository.save(TeamMember.builder()
                                .id("member1")
                                .userName("이춘향")
                                .team(team1)
                                .build());
                teamMemberRepository.save(TeamMember.builder()
                                .id("member2")
                                .userName("김진우")
                                .team(team2)
                                .build());
                teamMemberRepository.save(TeamMember.builder()
                                .id("member3")
                                .userName("김뚜띠")
                                .team(team2)
                                .build());
                teamMemberRepository.save(TeamMember.builder()
                                .id("member4")
                                .userName("감블러")
                                .team(team3)
                                .build());
        }

        @Test
        public void getRowTest() {

                // team+member(N) : team(1) => 외래키 제약 조건
                // member를 찾을 때 N:1 관계에서는 1에 해당하는 정보도 기본으로 가지고 옴
                // 중요한건 n 대 1의 관계에서 findById("member1") member만 찾아왔지만
                // 디버크 콘솔에서는 뭔가 하지 않아도 알아서 1의대한 정보도 기본으로 가지고 옴
                // ==> join 필요(left join) 스프링 부트에서 해준 것

                // 연관관계가 있는 데이터 조회
                // 1. 다대일(멤버:팀) 관계에서는 기본적으로 1에 해당하는 엔티티 정보를 가지고 나옴 => FetchType
                // ==> FetchType.EAGER 기본값
                // => 멤버를 조회시 팀정보도 같이 조회됨 (객체 그래프 탐색으로 접근이 가능)
                // => 객체 지향 쿼리 작성

                // 2. 일대다(팀:멤버) 관계에서는 다에 해당하는 엔티티를 안가지고 나옴
                // ==>FetchType.LAZY : 안가지고 나옴

                // FetchType : 연결관계에서 상대 정보를 같이 가지고 나올건지 말건지 여부
                // FetchType.EAGER : 가지고 나옴
                // FetchType.LAZY : 안가지고 나옴

                // from_team_member tb1_0 left join team t1_0
                TeamMember teamMember = teamMemberRepository.findById("member1").get();
                System.out.println(teamMember);
                // 객체 그래프 탐색
                System.out.println("팀 전체 정보: " + teamMember.getTeam()); // Team(id = team1, name 팀1)
                System.out.println("팀 명 : " + teamMember.getTeam().getName()); // 팀1

                // 회원 조회시 나와 같은 팀의 소속된 회원 조회

                // select
                // tm1_0.member_id,
                // t1_0.team_id,
                // t1_0.team_name,
                // tm1_0.team_name
                // from
                // team_member tm1_0
                // left join
                // team t1_0
                // on t1_0.team_id=tm1_0.team_team_id
                // where
                // tm1_0.member_id=?
                // 쿼리가 뜨게 됨

                // 회원 조회시 나와 같은 팀에 소속된 회원과 팀 조회

                teamMemberRepository.findByMemberEqualTeam("슈퍼민주주의").forEach(member -> {
                        System.out.println(member);

                });
        }

        @Test
        public void updateTest() {
                // 멤버의 팀 변경
                // 수정할 것을 먼저 조회

                TeamMember member = teamMemberRepository.findById("member3").get();

                Team team = Team.builder()
                                .id("team3")

                                .build();

                member.setTeam(team);
                System.out.println("수정 후" + teamMemberRepository.save(member));

        }

        // @Test
        // public void deleteTest() {
        // // 팀 삭제

        // teamRepository.deleteById("team1");

        // 이대로만 하면 무결성 제약조건 위배가 뜸 - 자식레코드가 발련되어서

        // }

        @Test
        public void deleteTest() {

                // 팀원 삭제 or 팀원 팀을 null 로 설정

                // 팀 삭제

                TeamMember member = teamMemberRepository.findById("member1").get();
                member.setTeam(null); // 팀원 팀을 null 로 바꾼다
                teamMemberRepository.save(member);

                teamRepository.deleteById("team1");

        }

        // OneToMany 설정 기준으로
        // 팀을 기준으로 회원 조회?

        @Test
        public void getMemberList() {
                Team team = teamRepository.findById("team3").get();

                System.out.println(team);
                // 레이지 익셉션 toStirng() : members 변수를 출력하라고 했기 때문에
                // 해결법
                // ! . @Transactional
                // ! . FetchType.EAGER 로 변경

                team.getMembers().forEach(m -> {
                        System.out.println(m);
                });

        }

}