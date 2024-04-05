package com.example.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.jpa.entity.TeamMember;

public interface TeamMemberRepository extends JpaRepository<TeamMember, String> {

    // 쿼리 직접 날리기
    // sql 이 아님(객체를 기준으로 작성 해야함)

    @Query("select m,t from TeamMember m join m.team t where t.name=?1")
    public List<TeamMember> findByMemberEqualTeam(String teamName);
}
