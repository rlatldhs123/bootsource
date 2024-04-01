package com.example.web1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.web1.dto.AddDto;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Log4j2
@RequestMapping("/calc")
public class AddController {

    @GetMapping("/add")
    public void addGet() {
        log.info("/calc/add 요청");

    }

    // add.html 에 있는 form 이 post 방식이기에 포스트 요청 맵핑을 해줘야 한다
    // 1) 사용자 입력 값을 가져오기위해 mvc 모델 2방식에서 썻던 HttpServletRequest 객체를 사용한다
    // 2) 파라메터 이용
    // 3) DTO 이용
    // - post 컨트롤러 응답 후 보여지는 화면단에서 dto 에 들어있는 값을 사용 가능

    // 2 번과 3번을 많이 쓴다
    // @PostMapping("/add")
    // public void addPost(HttpServletRequest req) {
    // log.info("/calc/add post 요청");
    // log.info("num1 {}", req.getParameter("num1"));
    // log.info("num2 {}", req.getParameter("num2"));

    // }

    // 스프링 프레임 워크에 편한점
    // 타입을 자동으로 바꿔서 넣어준다 String 으로 받아오는 것을 기존은 타입을 일일이 바꿔줘야 했지만
    // 스프링은 자동으로 해준다
    // html 네임 속성과 이름읆 맞춰야 한다
    // 2번쨰 방법인 파라메터를 이용해서 값을 가져오려면
    // form 이름과 변수명을 일치시켜야 한다
    // @PostMapping("/add")
    // public void addPost(@RequestParam(value = "op1", defaultValue = "0") int
    // num1,
    // @RequestParam(value = "op2", defaultValue = "0") int num2) {
    // log.info("/calc/add post 요청");
    // log.info("num1{}", num1);
    // log.info("num2{}", num2);

    // }

    @PostMapping("/add")
    public void addPost(AddDto dto, Model model) {
        log.info("/calc/add post 요청");
        log.info("num1{}", dto.getNum1());
        log.info("num2{}", dto.getNum2());

        // dto.setResult(dto.getNum1() + dto.getNum2());
        // 모델 객체에 담았기 때문에 화면단에서 쓸려면 그냥 result 만 쓰면 된다 ex)= ${result}
        model.addAttribute("result", dto.getNum1() + dto.getNum2());

    }

    @GetMapping("/rules")
    public void getMethodName() {
        log.info("calc/rules 요청");

    }

    @PostMapping("/rules")
    public String postMethodName(AddDto addDto, @ModelAttribute("calc") String calc, Model model) {
        log.info("/calc/rules post 요청");

        int result = 0;

        switch (calc) {
            case "+":
                result = addDto.getNum1() + addDto.getNum2();

                break;
            case "-":
                result = addDto.getNum1() - addDto.getNum2();

                break;
            case "/":
                result = addDto.getNum1() / addDto.getNum2();

                break;
            case "*":
                result = addDto.getNum1() * addDto.getNum2();

                break;

        }
        // model.addAttribute("result", result);
        addDto.setResult(result);

        return "/calc/result";
        // 끝나고 보내는
    }

    // 데이터 보내기
    // mvc 모델 2의 경우

    // request.setAttribute("이름",값) 으로 세팅 하고
    // ${} 를 이용해서 jsp 로 보여주게 함

    // 하지만 스프링 쪽에서는 이러한 역할을 똑같이 수행하는 Model 이라는 객채를 이미 만들어 놓음
}
