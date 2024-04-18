package com.example.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.rest.dto.SampleDto;

@Controller
public class BasicContorrler {

    @GetMapping("/basic")
    @ResponseBody // 리 턴 값이 데이터임 템플릿 찾으란말 아님
    public String getMethodName() {

        return "반갑습니다";
    }

    // ResponseEntity객체를 쓰게되면 일반 컨트롤러에서도 리턴 값이 데이터임을 의미함
    @GetMapping(value = "/check2", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SampleDto> getCheck(double height, double weight) {

        SampleDto dto = new SampleDto();
        dto.setMno(1L);
        dto.setFirstName(String.valueOf(height));
        dto.setLastName(String.valueOf(weight));
        // 200 : OK

        if (height < 150) {

            return new ResponseEntity<SampleDto>(dto, HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<SampleDto>(dto, HttpStatus.OK);

        // 객체 리턴이 가능함
    }

}
