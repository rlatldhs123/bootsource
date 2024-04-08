package com.example.todo.cotroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2

public class TodoController {

    // 8080 으로 끝났을떄 루트로(/) 접속 했을때 list.html 이 노출 되도록 설정

    @GetMapping({ "/", "/todo/list" })
    public String list() {
        log.info("list 요청");
        return "/todo/list";
    }

}
