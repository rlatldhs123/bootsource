package com.example.movie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@Log4j2
@RequestMapping("/movie")
public class MovieController {

    @GetMapping("/list")
    public void getList() {
        

        

    }

}
