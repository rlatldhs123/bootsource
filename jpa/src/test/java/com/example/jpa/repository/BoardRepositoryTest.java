package com.example.jpa.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Test
    public void queryMethodTest() {
        // where
        // b1_0.title=? 이러면 정확히 일치하는 것만 가져온다
        // List<Board> list = boardRepository.findByTitle("Title");
        // System.out.println("findByTitle" + list.size());

        // boardRepository.findByTitleContaining("Title");
        // System.out.println("findByTitleContaining" + list.size());

        // boardRepository.findByTitleEndingWith("Title");
        // System.out.println("findByTitleEndingWith" + list.size());

        // boardRepository.findByTitleStartingWith("Title");
        // System.out.println("findByTitleStartingWith" + list.size());

        // list = boardRepository.findByWriterStartingWith("user");
        // System.out.println("findByWriterStartingWith" + list.size());

        // List<Board> list = boardRepository.findByTitleContainingOrContent("Title",
        // "Content");
        // System.out.println("findByTitleContainingOrContent" + list.size());

        // list = boardRepository.findByTitleContainingOrContentContaining("Title",
        // "Content");
        // System.out.println("findByTitleContainingOrContentContaining" + list.size());

        // List<Board> list =
        // boardRepository.findByTitleContainingAndidGreaterThan("Title",
        // 50L);
        // System.out.println("findByTitleContainingAndidGreaterThan" + list.size());

        // List<Board> list = boardRepository.findByIdGreaterThanOrderByIdDesc(50L);
        // System.out.println("findByIdGreaterThanOrderByDesc" + list.size());

        Pageable pageable = PageRequest.of(0, 10);
        // PageRequest.of(0, 10); 페이지 0 부터 시작
        // Pageable 은 도메인 걸 가져와야 한다
        List<Board> list = boardRepository.findByIdGreaterThanOrderByIdDesc(50L, pageable);
        list.forEach(System.out::println);
    }

}
