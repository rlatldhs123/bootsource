package com.example.board.service;

import java.util.List;

import com.example.board.dto.ReplyDto;
import com.example.board.entity.Board;
import com.example.board.entity.Member;
import com.example.board.entity.Reply;

public interface ReplyService {

    List<ReplyDto> getReplies(Long bno);

    Long create(ReplyDto dto);

    void remove(Long rno);

    ReplyDto getReply(Long rno);

    Long update(ReplyDto dto);

    public default ReplyDto entityToDto(Reply reply) {
        ReplyDto Dto = ReplyDto.builder()
                .rno(reply.getRno())
                .bno(reply.getBoard().getBno()) // 보드 번호
                .writerEmail(reply.getReplyer().getEmail())
                .writerName(reply.getReplyer().getName())
                .text(reply.getText())
                .createdDate(reply.getCreatedDate())
                .lastModifiedDate(reply.getLastModifiedDate())
                .build();

        return Dto;

    }

    // dto 를 엔티티로

    public default Reply dtoToEntity(ReplyDto dto) {

        Board board = Board.builder().bno(dto.getBno()).build();
        Member member = Member.builder().email(dto.getWriterEmail()).build();

        return Reply.builder()
                .rno(dto.getRno())
                .board(board)
                .replyer(member)
                .text(dto.getText())
                .build();

    }

}
