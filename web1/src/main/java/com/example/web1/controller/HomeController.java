package com.example.web1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Log4j2
public class HomeController {
    // vlaue 의 서비스를 하는 주소를 넣어주면 된다
    // @RequestMapping(value = "/", method = RequestMethod.GET)
    // 더 간단한 어노테이션은
    // public String getMethodName(@RequestParam String param) {
    // return new String();
    // }

    @GetMapping("/")
    public String home() {
        log.info("home 요청"); // 시스템 프린트 아웃과 같은 것이다

        // templates 아래 경로부터 확장자 빼고 파일명만 슨다
        return "index";

    }

}
