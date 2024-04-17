package com.example.board.repository.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchBoardRepsitory {
    // @Query(select m ,t from Member m hoin m.team t = ?1)

    // 하나는 멤버 타입 하나는 팀타입 으로 받아야 하는 상황 즉 2개 이상이 될 경우
    // 무조건 오브젝트로 담는다
    // 전체 조회시 board, member,reply 정보를 전부다 조회
    Page<Object[]> list(String type, String keyword, Pageable pageable);

    // 상세 조회
    Object[] getRow(Long bno);

}
