package com.example.board.contorller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.dto.ReplyDto;
import com.example.board.service.ReplyService;
import com.example.board.service.ReplyServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/replies")
@Log4j2
@RequiredArgsConstructor
public class ReplyContorller {

    private final ReplyService service;

    @GetMapping("/board/{bno}")
    public List<ReplyDto> getListByBoard(@PathVariable("bno") Long bno) {

        log.info("댓글 가져오기 {} ", bno);

        List<ReplyDto> replies = service.getReplies(bno);

        // @RestController 로 만들었다는 것은 리턴은 무조건 데이터 타입을 해야 한다

        return replies;
    }

    // 댓글 추가
    // 주소/replies/new + POST

    @PostMapping("/new")
    public ResponseEntity<Long> insertReply(@RequestBody ReplyDto dto) {

        log.info("댓글 등록{} ", dto);

        return new ResponseEntity<Long>(service.create(dto), HttpStatus.OK);
    }

    // /{rno} + DELETE
    @DeleteMapping("/{rno}")
    public ResponseEntity<String> deleteReply(@PathVariable("rno") Long rno) {

        log.info("댓글제거 {}", rno);

        service.remove(rno);
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

    // /replies/{rno} + GET 딜리트랑 보기에는 같지만 맵핑이 다르기때문에 괜찮다
    @GetMapping("/{rno}")
    public ResponseEntity<ReplyDto> getRow(@PathVariable("rno") Long rno) {

        log.info("댓글 하나 요청 {}", rno);
        return new ResponseEntity<ReplyDto>(service.getReply(rno), HttpStatus.OK);
    }

    @PutMapping("/replies/{id}")
    public ResponseEntity<String> modifyPost(@RequestBody ReplyDto dto, @PathVariable("id") String id) {
        log.info("업데이트 요청{}, {}", dto, id);

        // 수정이 잘 되면 완료시 리드로 다시 가고 싶음
        Long rno = service.update(dto);

        return new ResponseEntity<String>(String.valueOf(rno), HttpStatus.OK);
    }

}
