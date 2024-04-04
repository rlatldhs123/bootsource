package com.example.jpa.repository;

import java.util.Optional;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.entity.Board;
import com.example.jpa.entity.Memo;

@SpringBootTest
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertTest() {
        LongStream.rangeClosed(1, 100).forEach(i -> {
            Board board = Board.builder()
                    .title("Title..." + i)
                    .content("content..." + i)
                    .writer("user" + (i % 10))
                    .title("Title..." + i)

                    .build();

            boardRepository.save(board);

        });
    }

    @Test
    public void readTest() {

        System.out.println(boardRepository.findById(5L));
    }

    @Test
    public void getListTest() {
        boardRepository.findAll().forEach(board -> System.out.println(board));
    }

    @Test
    public void updateTest() {
        Optional<Board> result = boardRepository.findById(25L);

        // result.get()
        result.ifPresent(board -> {
            board.setTitle("Update Title");
            board.setContent("Update content");

            System.out.println(boardRepository.save(board));
        });

    }

    @Test
    public void delete() {

        // 엔티티 찾기
        Optional<Board> result = boardRepository.findById(2L);
        // 찾은 것을 엔티티 객체에 담음
        Board board = result.get();
        // 딜리트메소드 실행
        boardRepository.delete(board);

        System.out.println("삭제 board " + boardRepository.findById(24L));

        // 삭제 memoOptional.empty 정상처리 되었을떄 나오는 디버그 콘솔 텍스트
        // Optional 형태이기에 정상 사제 진행시 empty로 나온다

    }

}
