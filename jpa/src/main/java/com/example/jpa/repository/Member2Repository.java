package com.example.jpa.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.example.jpa.entity.Member2;
import com.example.jpa.entity.Team2;

public interface Member2Repository extends JpaRepository<Member2, Long>, QuerydslPredicateExecutor<Member2> {

    // jpal 사용시

    // 1. entity 타입결과 => List<Entity명>
    // 2. 개별 타입 결과 => List<Object[]> 셀렉트를 전부다 하지않고 몇개만 하고 싶을떄
    @Query("SELECT m FROM Member2 m")
    List<Member2> findbyMembers(Sort sort);

    // unsename 과 age 만 뽑아 오고 싶다
    @Query("SELECT m.userName,m.age FROM Member2 m")
    List<Object[]> findbyMembers2();

    // 특정 나이보다 많은 회원 조회

    // @Query("SELECT m FROM Member2 m WHERE m.age > ?1 ") 둘중 하나를 쓰면 됌 파라메터 처리
    @Query("SELECT m FROM Member2 m WHERE m.age > :age")
    List<Member2> findByAgeList(int age);

    // 특정 팀의 회원 조회
    @Query("SELECT m FROM Member2 m WHERE m.team2.id = ?1")
    List<Member2> findByTeamEqual(Long id);

    // 집계 함수
    @Query("SELECT COUNT(m), SUM(m.age),AVG(m.age), MAX(m.age), MIN(m.age) FROM Member2 m")
    List<Object[]> aggregate();

    // 조인
    @Query("SELECT m FROM Member2 m  JOIN m.team2 t WHERE t.name= :teamName")
    List<Member2> findByTeamMember(String teamName);

    @Query("SELECT m t FROM Member2 m  JOIN m.team2 t WHERE t.name= :teamName")
    List<Object[]> findByTeamMember2(String teamName);

    // 외부조인
    // 내부조인을 할떄는 ON 구문을 잘 쓰진 않지만 외부조인 할때는
    // On 구문을 쓴다
    @Query("SELECT m t FROM Member2 m  LEFT JOIN m.team2 t ON t.name= :teamName")
    List<Object[]> findByTeamMember3(String teamName);

}
