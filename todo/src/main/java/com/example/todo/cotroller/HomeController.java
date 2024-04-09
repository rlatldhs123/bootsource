package com.example.todo.cotroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import groovy.util.logging.Log4j2;

@Controller
@Log4j2
public class HomeController {
    @GetMapping({ "/", "/list" })
    public String home() {

        return "redirect:/todo/list";
    }

}
