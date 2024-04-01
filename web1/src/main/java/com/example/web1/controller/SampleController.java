package com.example.web1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Log4j2
// 위에서 샘플을 붙혀주면 밑에 애들은 sample 폴더 경로 생략 가능
@RequestMapping("/sample")
public class SampleController {

    // String,void 형태의 메소드 작성
    // 404 : 경로 없음 (컨트롤러에 매핑 주소 확인)
    // 500 : Error resolving template 템플릿즈 폴더를 확인 한다

    // http://localhost:8080/sample/basic 요청 get 방식 요청

    // 리턴 타입 결정
    // void : 경로와 일치 하는 곳에 템플릿이 존재할 때 void 로 가면 된다
    // // http://localhost:8080/sample/basic 마지막 즉 basic 이라는 단어가 html 파일과 이름을 일치시켜야
    // 하며 앞에있는 것은 폴도 경로 이름과 맞춘다

    // String : 경로와 일치하는 곳에 템플릿이 없을때 (템플릿의 위치와 관게 없이 자유롭게 지정이 가능)

    @GetMapping("/basic")
    public void basic() {
        log.info("/sample/basic 요청");

    }

    // // http://localhost:8080/sample/basic/ex1 요청

    @GetMapping("/ex1")
    public void ex1() {
        log.info("/sample/ex1 요청");

    }

    @GetMapping("/ex2")
    public String ex2() {

        log.info("/sample/ex2 요청");
        // return "/board/create";
        return "/index";
    }

}
