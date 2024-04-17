package com.example.board.service;

import java.util.List;

import com.example.board.dto.BoardDto;
import com.example.board.dto.PageRequestDto;
import com.example.board.dto.PageResultDto;
import com.example.board.entity.Board;
import com.example.board.entity.Member;

public interface BoardService {

    PageResultDto<BoardDto, Object[]> getList(PageRequestDto requestDto);

    void update(BoardDto dto);

    BoardDto getRow(Long bno);

    void deleteWithReplies(Long bno);

    public default BoardDto entityToDto(Board board, Member member, Long replyCnt) {
        BoardDto gDto = BoardDto.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .writerName(board.getWriter().getName())
                .writerEmail(board.getWriter().getEmail())
                .replyCount(replyCnt)
                .createdDate(board.getCreatedDate())
                .lastModifiedDate(board.getLastModifiedDate())
                .build();

        return gDto;

    }

    // dto 를 엔티티로

    public default Board dtoToEntity(BoardDto dto) {

        Member member = Member.builder().email(dto.getWriterEmail()).build();
        return Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();

    }

}
