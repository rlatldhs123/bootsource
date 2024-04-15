package com.example.book.controller;

import java.util.List;

import org.hibernate.query.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.book.dto.BookDto;
import com.example.book.dto.PageRequestDto;
import com.example.book.dto.PageResultDto;
import com.example.book.entity.Book;
import com.example.book.service.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @GetMapping("/list")
    public void list(Model model, PageRequestDto requestDto) {
        // 페이지 나누기 후
        log.info("list 요청");
        PageResultDto<BookDto, Book> result = service.getList(requestDto);
        model.addAttribute("result", result);

    }

    @GetMapping(value = { "/read", "/modify" })
    public void read(@RequestParam Long id, PageRequestDto pageRequestDto, Model model) {
        log.info("read or modify 요청");

        BookDto dto = service.getRow(id);

        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String modifyPost(BookDto dto, PageRequestDto pageRequestDto, RedirectAttributes rttr) {
        log.info("업데이트 요청{}", dto);
        log.info("page 나누기 정보", pageRequestDto);

        // 수정이 잘 되면 완료시 리드로 다시 가고 싶음
        BookDto updateDto = service.priceUpdate(dto);

        // 경로는 /memo/read 로 이동
        // =? 주소줄에 딸려가게 하는 방식
        // 세션에 담고싶으면
        // rttr.addFlashAttribute() 를 쓴다
        rttr.addAttribute("id", updateDto.getId());
        rttr.addAttribute("page", pageRequestDto.getPage());
        rttr.addAttribute("type", pageRequestDto.getType());
        rttr.addAttribute("keyword", pageRequestDto.getKeyword());

        return "redirect:/book/read";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id, PageRequestDto pageRequestDto, RedirectAttributes rttr) {

        service.BookDelete(id);

        // 페이지 나누기 정보
        rttr.addAttribute("page", pageRequestDto.getPage());
        rttr.addAttribute("type", pageRequestDto.getType());
        rttr.addAttribute("keyword", pageRequestDto.getKeyword());

        return "redirect:/book/list";
    }

    @GetMapping(value = { "/create" })
    public void create(BookDto bdto, Model model, PageRequestDto pageRequestDto) {
        log.info("create 요청");

        // 테이블에 카테고리화면 단 보여주기
        model.addAttribute("categories", service.categoryNameList());
    }

    @PostMapping("/create")
    public String createPost(@Valid BookDto bDto, BindingResult result, RedirectAttributes rttr, Model model,
            PageRequestDto pageRequestDto) {
        log.info("book post 요청{}", bDto);

        if (result.hasErrors()) {

            model.addAttribute("categories", service.categoryNameList());
            // post 방식일때 이렇게 담아가지 않으면 분류 목록이 사라진다 따라서 모델 객체에 담아 가야 함
            return "/book/create";

        }

        // insert 작성
        Long id = service.bookCreate(bDto);
        rttr.addFlashAttribute("msg", id);
        rttr.addAttribute("page", pageRequestDto.getPage());
        rttr.addAttribute("type", pageRequestDto.getType());
        rttr.addAttribute("keyword", pageRequestDto.getKeyword());

        return "redirect:/book/list";

    }

}
