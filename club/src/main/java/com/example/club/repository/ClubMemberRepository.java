package com.example.club.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import com.example.club.entity.ClubMember;

public interface ClubMemberRepository extends JpaRepository<ClubMember, String> {

    // 회원 찾기(email 기준으로 찾고 social 회원 여부인지 확인)
    @EntityGraph(attributePaths = { "roleSet" }, type = EntityGraphType.LOAD)
    @Query("select m from ClubMember m where m.email = :email and m.fromSocial=:fromSocial")
    Optional<ClubMember> findByEmailAndFromSocial(String email, boolean fromSocial);
}
