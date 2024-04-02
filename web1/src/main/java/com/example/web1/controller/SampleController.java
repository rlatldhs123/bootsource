package com.example.web1.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.web1.dto.SampleDto;

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
    public void basic(Model model) {
        log.info("/sample/basic 요청");

        // model.addAttribute("name", "홍길동");
        // SampleDto sampleDto = new SampleDto();

        // sampleDto.setFirtst("firtst");
        // sampleDto.setId(1L);
        // sampleDto.setLast("last");
        // sampleDto.setRegTime(LocalDateTime.now());

        // lombok Builder 패턴 이용
        // SampleDto sampleDto = SampleDto.builder()
        // .firtst("first")
        // .last("last")
        // .regTime(LocalDateTime.now())
        // .id(1L)
        // .build();

        List<SampleDto> list = new ArrayList<>();
        for (Long i = 1L; i < 2L; i++) {
            SampleDto dto = SampleDto.builder()
                    .firtst("first" + i)
                    .last("last" + i)
                    .regTime(LocalDateTime.now())
                    .id(i)
                    .build();

            list.add(dto);
        }

        model.addAttribute("list", list);

        //
        model.addAttribute("now", new Date());
        model.addAttribute("price", 123456789);
        model.addAttribute("title", "This is a just sample");
        model.addAttribute("options", Arrays.asList("AAAA", "BBBB", "CCCC", "DDDD"));

    }

    // // http://localhost:8080/sample/basic/ex1 요청

    @GetMapping("/ex1")
    public void ex1(Model model) {
        log.info("/sample/ex1 요청");
        model.addAttribute("result", "SUCCESS");

    }

    @GetMapping("/ex2")
    public String ex2() {

        log.info("/sample/ex2 요청");
        // return "/board/create";
        return "/index";
    }

    @GetMapping("/ex3")
    public void ex3() {
        log.info("sample/ex3 요청");

    }

    @GetMapping("/ex4")
    public void ex4(String param1, String param2, Model model) {
        log.info("sample/ex4 요청");
        log.info("param1{} , param2{}", param1, param2);

        model.addAttribute("param1", param1);
        model.addAttribute("param2", param2);

    }

    @GetMapping("/ex5")
    public void ex5() {
        log.info("sample/ex5 요청");

    }

    @GetMapping("/ex6")
    public void ex6() {
        log.info("sample/ex6 요청");

    }

}
