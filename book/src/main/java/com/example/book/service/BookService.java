package com.example.book.service;

import java.util.List;

import com.example.book.dto.BookDto;
import com.example.book.dto.PageRequestDto;
import com.example.book.dto.PageResultDto;
import com.example.book.entity.Book;
import com.example.book.entity.Category;
import com.example.book.entity.Publisher;

public interface BookService {
    // 페이지 나누기 전
    // List<BookDto> getList();

    // 페이지를 나누기 후
    PageResultDto<BookDto, Book> getList(PageRequestDto requestDto);

    Long bookCreate(BookDto dto);

    List<String> categoryNameList();

    BookDto getRow(Long id);

    BookDto priceUpdate(BookDto dto);

    void BookDelete(Long id);

    public default BookDto entityToDto(Book book) {

        BookDto bDto = BookDto.builder()

                .id(book.getId())
                .title(book.getTitle())
                .price(book.getPrice())
                .writer(book.getWriter())
                .categoryName(book.getCategory().getName())
                .publisherName(book.getPublisher().getName())
                .salePrice(book.getSalePrice())
                .createdDate(book.getCreatedDate())
                .lastModifiedDate(book.getLastModifiedDate())

                .build();
        return bDto;

    }

    public default Book dtoToEntity(BookDto bdto) {

        return Book.builder()

                .id(bdto.getId())
                .title(bdto.getTitle())
                .salePrice(bdto.getSalePrice())
                .price(bdto.getPrice())
                .writer(bdto.getWriter())

                .build();

    }

}
