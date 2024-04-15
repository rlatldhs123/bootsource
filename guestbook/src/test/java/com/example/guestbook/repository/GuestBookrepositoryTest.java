package com.example.guestbook.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.guestbook.entity.GuestBook;

@SpringBootTest
public class GuestBookrepositoryTest {

    @Autowired
    private GuestBookRepository guestBookRepository;

    @Test
    public void testInsert() {
        // 300개 테스트 삽입

        LongStream.rangeClosed(1, 300).forEach(i -> {
            GuestBook guestBook = GuestBook.builder()
                    .writer("user" + (i % 10))
                    .title("title" + i)
                    .content("contest" + i)
                    .build();

            guestBookRepository.save(guestBook);
        });

    }

    // 전체 리스트 가져오는 테스트 메소드
    @Test
    public void testList() {
        List<GuestBook> list = guestBookRepository.findAll();

        list.forEach(book -> {
            System.out.println(book);
            System.out.println(book.getContent());
            System.out.println(book.getTitle());
            System.out.println(book.getWriter());
            System.out.println(book.getWriter());
        });

    }

    // 특정 책 조회
    @Test
    public void testRow() {
        System.out.println(guestBookRepository.findById(20L));

    }

    // 특정 책 수정
    @Test
    public void testUpdate() {
        GuestBook gBook = guestBookRepository.findById(20L).get();

        gBook.setTitle("수정 타이틀");
        gBook.setWriter("수정 작가");
        guestBookRepository.save(gBook);

    }

    // 특정 책 삭제
    @Test
    public void testDelete() {
        GuestBook gBook = guestBookRepository.findById(10L).get();
        guestBookRepository.delete(gBook);

    }

}
