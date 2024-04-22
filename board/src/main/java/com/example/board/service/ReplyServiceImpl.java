package com.example.board.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.board.dto.ReplyDto;
import com.example.board.entity.Board;
import com.example.board.entity.Reply;
import com.example.board.repository.ReplyRepository;

import groovy.util.logging.Log4j2;
import lombok.RequiredArgsConstructor;

@Log4j2
@Service
@RequiredArgsConstructor

public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;

    @Override
    public List<ReplyDto> getReplies(Long bno) {
        Board board = Board.builder()
                .bno(bno).build();

        List<Reply> replies = replyRepository.getRepliesByBoardOrderByRno(board);
        // 엔티티 리스트 ==> DTO 리스트

        return replies.stream().map(reply -> entityToDto(reply)).collect(Collectors.toList());

    }

    // 댓글 크리에이트
    @Override
    public Long create(ReplyDto dto) {
        return replyRepository.save(dtoToEntity(dto)).getRno();
    }

    @Override
    public void remove(Long rno) {

        replyRepository.deleteById(rno);

    }

    @Override
    public ReplyDto getReply(Long rno) {
        // Reply reply = replyRepository.findById(rno).get();
        // ReplyDto dto = entityToDto(reply);
        // 코드를 나눠 쓸때에 예시

        return entityToDto(replyRepository.findById(rno).get());

    }

    @Override
    public Long update(ReplyDto dto) {

        // Long rno = replyRepository.save(dtoToEntity(dto)).getRno();

        Reply reply = replyRepository.findById(dto.getRno()).get();

        reply.setText(dto.getText());

        reply = replyRepository.save(reply);

        return reply.getRno();

    }

}
