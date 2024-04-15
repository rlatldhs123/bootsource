package com.example.guestbook.contorller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.guestbook.dto.GuestBookDto;
import com.example.guestbook.service.GuestBookServiceImpl;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
@RequestMapping("/guestbook")
@RequiredArgsConstructor
public class GuestContorller {

    private final GuestBookServiceImpl service;

    @GetMapping("/list")
    public void getList(Model model) {

        model.addAttribute("list", service.getList());

    }

    public void list(Model model) {

        log.info("리스트 요청");

    }

}
