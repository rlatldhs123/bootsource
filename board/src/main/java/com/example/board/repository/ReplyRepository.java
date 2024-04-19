package com.example.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.board.entity.Board;
import com.example.board.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    // 삭제는 하지만 기준은 bno (Board 테이블 PK)를 이용해서 삭제 해야 한다

    @Modifying // 사실 @Query 는 셀렉트할떄만 사용하는게 일반적 그러기에 @Modifying 을 넣어줘야 한다

    @Query("delete from Reply r where r.board.bno=:bno")
    void deleteByBno(Long bno);

    List<Reply> getRepliesByBoardOrderByRno(Board board);

}
