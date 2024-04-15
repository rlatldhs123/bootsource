package com.example.guestbook.service;

import java.util.List;

import com.example.guestbook.dto.GuestBookDto;
import com.example.guestbook.entity.GuestBook;

public interface GuestBookService {

    List<GuestBookDto> getList();

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
