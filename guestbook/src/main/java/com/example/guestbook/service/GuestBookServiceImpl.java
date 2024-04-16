package com.example.guestbook.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.guestbook.dto.GuestBookDto;
import com.example.guestbook.dto.PageRequestDto;
import com.example.guestbook.dto.PageResultDto;
import com.example.guestbook.entity.GuestBook;
import com.example.guestbook.entity.QGuestBook;
import com.example.guestbook.repository.GuestBookRepository;
import com.querydsl.core.BooleanBuilder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuestBookServiceImpl implements GuestBookService {

    private final GuestBookRepository guestBookRepository;

    @Override
    public GuestBookDto getRow(Long gno) {

        GuestBook entity = guestBookRepository.findById(gno).get();

        return entityToDto(entity);

    }

    @Override
    public GuestBookDto update(GuestBookDto dto) {

        GuestBook entity = guestBookRepository.findById(dto.getGno()).get();
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        ;
        // entity.setTitle 입력한 값 저장
        // dto.getTitle 유저가 입력한 값

        return entityToDto(guestBookRepository.save(entity));

    }

    @Override
    public void delete(Long gno) {
        guestBookRepository.deleteById(gno);
    }
    // 페이지 나누기전
    // @Override
    // public List<GuestBookDto> getList() {
    // List<GuestBook> entitys = guestBookRepository.findAll();

    // Function<GuestBook, GuestBookDto> fn = (entity -> entityToDto(entity));

    // return entitys.stream().map(fn).collect(Collectors.toList());

    // // gbook 엔티티 ==> gbook DTO 변환

    // }

    @Override
    public PageResultDto<GuestBookDto, GuestBook> getList(PageRequestDto requestDto) {

        Pageable pageable = requestDto.getPageable(Sort.by("gno").descending());
        // 페이징 처리가 된 findAll 을 부른다

        // Page<GuestBook> entitys = guestBookRepository.findAll(pageable);

        BooleanBuilder builder = getSearch(requestDto);
        Page<GuestBook> result = guestBookRepository.findAll(builder, pageable);

        Function<GuestBook, GuestBookDto> fn = (entity -> entityToDto(entity));

        return new PageResultDto<GuestBookDto, GuestBook>(result, fn);

    }

    @Override
    public Long create(GuestBookDto gDto) {

        GuestBook guestBook = guestBookRepository.save(dtoToEntity(gDto));

        return guestBook.getGno();

    }

    // book 프로젝트에서는 동일한 메소드를 bookrepositoy 에 작성함
    private BooleanBuilder getSearch(PageRequestDto requestDto) {
        BooleanBuilder builder = new BooleanBuilder();

        // Q 클래스 사용
        QGuestBook guestBook = QGuestBook.guestBook;

        // type , keyword 가져오기
        String type = requestDto.getType();
        String keyword = requestDto.getKeyword();

        // gno > 0 좀더 구동을 빨리 하기위해 썼음
        builder.and(guestBook.gno.gt(0L));

        if (type == null || type.trim().length() == 0) {
            return builder;

        }
        // 검색 타입이 있는 경우
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if (type.contains("t")) {
            conditionBuilder.or(guestBook.title.contains(keyword));

        }
        if (type.contains("c")) {
            conditionBuilder.or(guestBook.content.contains(keyword));

        }
        if (type.contains("w")) {
            conditionBuilder.or(guestBook.writer.contains(keyword));

        }
        builder.and(conditionBuilder);
        return builder;
    }

}
