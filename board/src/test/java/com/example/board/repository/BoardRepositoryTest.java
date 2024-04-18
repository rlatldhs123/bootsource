package com.example.board.repository;

import static org.mockito.Mockito.description;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.entity.Board;
import com.example.board.entity.Member;

@SpringBootTest
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder().email("user" + i + "@gmail.com").build();

            Board board = Board.builder()
                    .title("title" + i)
                    .content("content" + i)
                    .writer(member)
                    .build();
            boardRepository.save(board);
        });
    }

    @Transactional
    @Test
    public void ReadBoard() {
        Board board = boardRepository.findById(2L).get();
        System.out.println(board);

        System.out.println(board.getWriter());
    }

    @Test
    public void testList() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Object[]> list = boardRepository.list("tcw", "title", pageable);

        for (Object[] objects : list) {
            // System.out.println(Arrays.toString(objects));
            // Board board = (Board) objects[0];
            // Member member = (Member) objects[1];
            // Long replyCnt = (Long) objects[2];

            // System.out.println(board + " " + member + " " + replyCnt);

        }

    }

    @Test
    public void getRow() {
        Object[] row = boardRepository.getRow(3L);

        System.out.println(Arrays.toString(row));

    }

    @Test
    @Transactional
    public void testRemove() {

        // 자식 (댓글) 삭제
        replyRepository.deleteByBno(4L);

        // 부모(원본글) 삭제
        boardRepository.deleteById(4L);

    }

}
