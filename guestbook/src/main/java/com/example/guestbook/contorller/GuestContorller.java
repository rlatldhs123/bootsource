package com.example.guestbook.contorller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.guestbook.dto.GuestBookDto;
import com.example.guestbook.dto.PageRequestDto;
import com.example.guestbook.dto.PageResultDto;
import com.example.guestbook.entity.GuestBook;
import com.example.guestbook.service.GuestBookServiceImpl;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Log4j2
@RequestMapping("/guestbook")
@RequiredArgsConstructor
public class GuestContorller {

    private final GuestBookServiceImpl service;

    @GetMapping("/list")
    public void getList(Model model, PageRequestDto requestDto) {
        log.info("list 요청");

        PageResultDto<GuestBookDto, GuestBook> result = service.getList(requestDto);

        model.addAttribute("result", result);

    }

    @GetMapping(value = { "/read", "/modify" })
    public void getRow(@RequestParam Long gno, Model model, PageRequestDto requestDto) {
        log.info("read or modify 요청");

        GuestBookDto dto = service.getRow(gno);

        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String update(GuestBookDto dto, RedirectAttributes rttr, PageRequestDto requestDto) {
        GuestBookDto updateDto = service.update(dto);

        // 경로는 /guestbook/read 로 이동
        // =? 주소줄에 딸려가게 하는 방식
        // 세션에 담고싶으면
        // rttr.addFlashAttribute() 를 쓴다
        rttr.addAttribute("gno", dto.getGno());
        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());

        return "redirect:/guestbook/read";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long gno, RedirectAttributes rttr, PageRequestDto requestDto) {
        service.delete(gno);

        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());

        return "redirect:/guestbook/list";

    }

    @GetMapping("/create")
    public void create(GuestBookDto gDto, Model model, PageRequestDto requestDto) {
        log.info("create 요청");

    }

    @PostMapping("/create")
    public String createPost(@Valid GuestBookDto gDto, BindingResult result, Model model, PageRequestDto requestDto,
            RedirectAttributes rttr) {
        if (result.hasErrors()) {
            return "/guestbook/create";

        }
        Long gno = service.create(gDto);
        rttr.addFlashAttribute("msg", gno);
        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());

        return "redirect:/guestbook/list";
    }

}
