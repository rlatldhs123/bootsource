package com.example.guestbook.contorller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2

public class HomeContorller {
    @GetMapping("/")
    public String home(RedirectAttributes rttr) {
        log.info("홈 요청");

        return "redirect:/guestbook/list";
    }

}
