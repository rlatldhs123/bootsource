package com.example.board.contorller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.board.dto.BoardDto;
import com.example.board.dto.PageRequestDto;
import com.example.board.repository.BoardRepository;
import com.example.board.service.BoardService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/board")

public class BoardContorller {

    private final BoardService service;

    @GetMapping("/list")
    public void getList(Model model, PageRequestDto requestDto) {

        model.addAttribute("result", service.getList(requestDto));

    }

    @GetMapping(value = { "/read", "/modify" })
    public void getRow(@RequestParam Long bno, Model model, PageRequestDto requestDto) {
        log.info("read or modify 요청");

        BoardDto dto = service.getRow(bno);

        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String update(BoardDto dto, Model model, RedirectAttributes rttr, PageRequestDto requestDto) {

        service.update(dto);

        rttr.addAttribute("bno", dto.getBno());
        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());

        return "redirect:/board/read";
    }

    @PostMapping("/delete")
    public String delete(Long bno, PageRequestDto requestDto, RedirectAttributes rttr) {
        service.deleteWithReplies(bno);

        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());

        return "redirect:/board/list";

    }

    @GetMapping("/create")
    public void createGet(BoardDto gDto, Model model, PageRequestDto requestDto) {
        

        log.info("create 요청");

    }

    @PostMapping("/create")
    public String postMethodName(@Valid BoardDto bDto, BindingResult result, Model model, PageRequestDto requestDto,
            RedirectAttributes rttr) {

        if (result.hasErrors()) {
            return "/board/create";

        }

        Long bno = service.create(bDto);
        rttr.addFlashAttribute("msg", bno);
        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());

        return "redirect:/board/list";
    }

}
