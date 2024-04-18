package com.example.book.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.Data;

@Data

// PageResultDto<DTO, EN> : DTO 와 EN(엔티티) 객체를 담기 위한 구조를 설계

public class PageResultDto<DTO, EN> {
    // 화면에 보여줄 목록
    private List<DTO> dtoList;
    // 총 페이지 번호
    private int totalPage;

    // 현재 페이지 번호
    private int page;

    // 목록 크기(한페이지에 보여줄 게시물 수)
    private int size;

    private int start, end;
    // 이전/다음 이동 링크 여부
    private boolean prev, next;
    // 페이지 번호 목록
    private List<Integer> pageList;

    public PageResultDto(Page<EN> result, Function<EN, DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        // 총개수/ 한페이지당 보여줄 게시물 수
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {
        // this.page = pageable.getPageNumber() 사용자가 요청한 페이지 번호 (페이지 번호가 0부터 시작하게 + 1 을
        // 한다)
        this.page = pageable.getPageNumber() + 1;
        // 한페이지당 보여줄 게시물 수
        this.size = pageable.getPageSize();

        int tempEnd = (int) (Math.ceil(page / 10.0)) * 10;
        this.start = tempEnd - 9;
        this.prev = start > 1;
        this.end = totalPage > tempEnd ? tempEnd : totalPage;
        this.next = totalPage > tempEnd;
        // int 타입으로 1~ 10 생성 ==> List<Interget> list
        // boxed() 메소드는 int 였던 것을 ==> Integer 로 변경 해줌
        this.pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

    }

}
