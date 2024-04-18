package com.example.board.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.dto.BoardDto;
import com.example.board.dto.PageRequestDto;
import com.example.board.dto.PageResultDto;
import com.example.board.entity.Board;
import com.example.board.entity.Member;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.MemberRepositoty;
import com.example.board.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    private final ReplyRepository replyRepository;

    private final MemberRepositoty memberRepositoty;

    @Override
    public PageResultDto<BoardDto, Object[]> getList(PageRequestDto requestDto) {
        // PageRequest.of(0, 10, Sort.by("bno").descending());

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Object[]> result = boardRepository.list(requestDto.getType(), requestDto.getKeyword(),
                requestDto.getPageable(Sort.by("bno").descending()));
        // List<Object[]> result = boardRepository.list();

        Function<Object[], BoardDto> fn = (entity -> entityToDto((Board) entity[0],
                (Member) entity[1],
                (Long) entity[2]));
        // return result.stream().map(fn).collect(Collectors.toList());
        return new PageResultDto<>(result, fn);

    }

    @Override
    public BoardDto getRow(Long bno) {
        Object[] row = boardRepository.getRow(bno);

        return entityToDto((Board) row[0], (Member) row[1],
                (Long) row[2]);

    }

    @Override
    public void update(BoardDto dto) {

        Board entity = boardRepository.findById(dto.getBno()).get();

        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());

        boardRepository.save(entity);

    }

    @Override
    @Transactional
    public void deleteWithReplies(Long bno) {

        replyRepository.deleteByBno(bno);

        boardRepository.deleteById(bno);

    }

    @Override
    public Long create(BoardDto dto) {
        Optional<Member> member = memberRepositoty.findById(dto.getWriterEmail());

        if (member.isPresent()) {
            Board entity = Board.builder()
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .writer(member.get())
                    .build();
            boardRepository.save(entity);

            return entity.getBno();
        }
        return null;

    }

}
