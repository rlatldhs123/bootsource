package com.example.guestbook.service;

import java.util.List;

import com.example.guestbook.dto.GuestBookDto;
import com.example.guestbook.dto.PageRequestDto;
import com.example.guestbook.dto.PageResultDto;
import com.example.guestbook.entity.GuestBook;

public interface GuestBookService {
    // 페이지 나누기전
    // List<GuestBookDto> getList();

    // 페이지 나누기후
    PageResultDto<GuestBookDto, GuestBook> getList(PageRequestDto requestDto);

    GuestBookDto getRow(Long gno);

    GuestBookDto update(GuestBookDto dto);

    void delete(Long gno);

    Long create(GuestBookDto gDto);

    public default GuestBookDto entityToDto(GuestBook entity) {
        GuestBookDto gDto = GuestBookDto.builder()
                .gno(entity.getGno())
                .writer(entity.getWriter())
                .title(entity.getTitle())
                .content(entity.getContent())
                .createdDate(entity.getCreatedDate())
                .lastModifiedDate(entity.getLastModifiedDate())
                .build();

        return gDto;

    }

    // dto 를 엔티티로

    public default GuestBook dtoToEntity(GuestBookDto dto) {
        return GuestBook.builder()
                .gno(dto.getGno())
                .writer(dto.getWriter())
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();

    }

}
