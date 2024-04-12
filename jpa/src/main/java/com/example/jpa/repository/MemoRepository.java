package com.example.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.entity.Memo;

// <Entity,Entity 에서 기본키 타입>
public interface MemoRepository extends JpaRepository<Memo, Long> {
    // DAO 역할

    // mno 가 5보다 작은 메모 조회
    List<Memo> findByMnoLessThan(Long mno);

    // mno 가 10보다 작은 메모 조회 mno 내림차순
    List<Memo> findByMnoLessThanOrderByMnoDesc(Long id);

    // mno 가 >= 50이상 and mno <= 70 메모 조회
    List<Memo> findByMnoBetween(Long start, Long end);

}
