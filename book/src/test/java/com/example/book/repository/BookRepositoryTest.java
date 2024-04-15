package com.example.book.repository;

import static org.mockito.Mockito.description;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.ScrollPosition.Direction;
import org.springframework.transaction.annotation.Transactional;

import com.example.book.entity.Book;
import com.example.book.entity.Category;
import com.example.book.entity.Publisher;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Test
    public void testCategoryCreate() {

        Category category = Category.builder().name("컴퓨터").build();
        categoryRepository.save(category);

        category = Category.builder().name("경제/경영").build();
        categoryRepository.save(category);

        category = Category.builder().name("인문").build();
        categoryRepository.save(category);

        category = Category.builder().name("소설").build();
        categoryRepository.save(category);

        category = Category.builder().name("자기계발").build();
        categoryRepository.save(category);
    }

    @Test
    public void testPublisherCreate() {

        Publisher publisher = Publisher.builder().name("로드북").build();
        publisherRepository.save(publisher);

        publisher = Publisher.builder().name("다산").build();
        publisherRepository.save(publisher);

        publisher = Publisher.builder().name("웅진지식하우스").build();
        publisherRepository.save(publisher);

        publisher = Publisher.builder().name("비룡소").build();
        publisherRepository.save(publisher);

        publisher = Publisher.builder().name("을유문화사").build();
        publisherRepository.save(publisher);
    }

    @Test
    public void testBookCreate() {

        LongStream.rangeClosed(1, 200).forEach(i -> {
            Book book = Book.builder()
                    .price(25000)
                    .salePrice(22500)
                    .title("스프링 부트 프레임워크 " + i)
                    .writer("홍길동")
                    .category(Category.builder().id((i % 5) + 1).build())
                    .publisher(Publisher.builder().id((i % 5) + 1).build())
                    .build();

            bookRepository.save(book);
        });

    }

    @Test
    @Transactional
    public void testBookList() {
        List<Book> books = bookRepository.findAll();

        books.forEach(book -> {
            System.out.println(book);
            System.out.println("출판사 : " + book.getPublisher().getName());
            System.out.println("분야 : " + book.getCategory().getName());
        });

    }

    @Test
    public void testCateNameList() {
        List<Category> list = categoryRepository.findAll();

        list.forEach(category -> System.out.println(category));

        // 실행시
        // Category(id=1, name=컴퓨터)
        // Category(id=2, name=경제/경영)
        // Category(id=3, name=인문)
        // Category(id=4, name=소설)
        // Category(id=5, name=자기계발)

        // 카테고리를 잘 뽑아옴

        // 하지만 필요한 것은 카테고리 이름만 필요 하다

        // List<String> cateList = new ArrayList<>();

        // list.forEach(category -> cateList.add(category.getName()));

        // 더 간단한 코드

        List<String> cateList = list.stream().map(entity -> entity.getName()).collect(Collectors.toList());
        cateList.forEach(System.out::println);

    }

    @Test
    public void testSearchList() {
        // page 번호 : 0 부터 시작
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());

        // Page 객체는 페이지 나누기에 필요한 메소드 제공
        Page<Book> result = bookRepository.findAll(bookRepository.makePredicate("t", "스프링"), pageable);

        System.out.println("getTotalElements (전체 행수)  :  " + result.getTotalElements());
        System.out.println("getTotalPages (필요한 페이지수) :  " + result.getTotalPages());
        result.getContent().forEach(book -> System.out.println(book));

    }

}
