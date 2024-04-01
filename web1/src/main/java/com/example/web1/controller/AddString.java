package com.example.web1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.web1.dto.AddDto;
import com.example.web1.dto.loginDto;

import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Log4j2
@RequestMapping("/add")
public class AddString {

    @GetMapping("/addString")
    public void addString() {

    }

    @PostMapping("/addString")
    public String addString(AddDto addDto, Model model) {

        log.info("s1 : " + addDto.getS1());
        log.info("s2 : " + addDto.getS2());
        log.info("s3 : " + addDto.getS3());

        model.addAttribute("result", addDto.getS1() + addDto.getS2() + addDto.getS3());

        return "/add/result";
    }

}
