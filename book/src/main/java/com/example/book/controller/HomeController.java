package com.example.book.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.book.dto.PageRequestDto;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class HomeController {

    @GetMapping("/")
    public String home(PageRequestDto pageRequestDto) {
        log.info("home 요청");
        return "home";
    }

}
