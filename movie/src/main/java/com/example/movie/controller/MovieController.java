package com.example.movie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.movie.dto.MovieDto;
import com.example.movie.dto.PageRequestDto;
import com.example.movie.dto.PageResultDto;
import com.example.movie.service.MovieService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Log4j2
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService service;

    @GetMapping("/list")
    public void getList(@ModelAttribute("requestDto") PageRequestDto pageRequestDto, Model model) {

        PageResultDto<MovieDto, Object[]> result = service.getList(pageRequestDto);

        model.addAttribute("result", result);

    }

    @GetMapping({ "/read", "/modify" })
    public void getRow(Model model, Long mno, @ModelAttribute("requestDto") PageRequestDto pageRequestDto) {

        log.info("영화 상제 정보 요청 {}", mno);

        MovieDto result = service.getRow(mno);

        model.addAttribute("dto", result);

    }

    @PostMapping("/remove")
    public String postRemove(Long mno) {

        service.movieRemove(mno);

        return "redirect:/movie/list";
    }

}
