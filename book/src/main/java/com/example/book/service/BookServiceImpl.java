package com.example.book.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.book.dto.BookDto;
import com.example.book.entity.Book;
import com.example.book.entity.Category;
import com.example.book.entity.Publisher;
import com.example.book.repository.BookRepository;
import com.example.book.repository.CategoryRepository;
import com.example.book.repository.PublisherRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;

    @Override
    public List<BookDto> getList() {
        List<Book> books = bookRepository.findAll(Sort.by("id").descending());

        // List<BookDto> bookDtos = new ArrayList<>();
        // books.forEach(book -> bookDtos.add(entityToDto(book)));

        List<BookDto> bookDtos = books.stream().map(book -> entityToDto(book)).collect(Collectors.toList());

        return bookDtos;

    }

    @Override
    public Long bookCreate(BookDto dto) {

        Category category = categoryRepository.findByName(dto.getCategoryName()).get();
        Publisher publisher = publisherRepository.findByName(dto.getPublisherName()).get();

        Book book = dtoToEntity(dto);
        book.setCategory(category);
        book.setPublisher(publisher);

        Book newBook = bookRepository.save(book);

        return newBook.getId();

    }

    @Override
    public List<String> categoryNameList() {

        List<Category> list = categoryRepository.findAll();

        return list.stream().map(entity -> entity.getName()).collect(Collectors.toList());

    }

    @Override
    public BookDto getRow(Long id) {
        Book entity = bookRepository.findById(id).get();

        return entityToDto(entity);

    }

    @Override
    public BookDto priceUpdate(BookDto dto) {
        // 수정 전 찾기
        Book entity = bookRepository.findById(dto.getId()).get();

        // 찾은 엔티티의 변화를 준다
        entity.setPrice(dto.getPrice());
        entity.setSalePrice(dto.getSalePrice());

        return entityToDto(bookRepository.save(entity));

    }

    @Override
    public void BookDelete(Long id) {

        bookRepository.deleteById(id);

    }

}
