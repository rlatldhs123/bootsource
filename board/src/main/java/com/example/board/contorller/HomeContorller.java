package com.example.board.contorller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.board.dto.PageRequestDto;

@Controller
@Log4j2
public class HomeContorller {
    @GetMapping("/")
    public String home(RedirectAttributes rttr, PageRequestDto requestDto) {
        log.info("홈 요청");

        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());

        return "redirect:/board/list";
    }

}
