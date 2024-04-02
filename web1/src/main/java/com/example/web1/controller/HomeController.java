package com.example.web1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    // RedirectAttributes : 리다이렉트시 데이터를 전달 할 수 있는 객체

    @GetMapping("/ex3")
    public String ex3(RedirectAttributes rttr) {
        log.info("ex3 요청");

        // response.sendRedirect("/qList.do")
        // 홈컨트롤러고 가는 경로
        // return "redirect:/";

        // return "redirect:/sample/basic";

        // rttr.addAttribute("이름", 값); : 파라메터로 전달
        // rttr.addFlashAttribute("이름", 값);

        // rttr.addAttribute("bno", 15);
        rttr.addFlashAttribute("bno", 15); // : Session (세션) 을 이용해서 값을 저장한다

        return "redirect:/sample/basic";
    }

    // .IllegalStateException: Ambiguous mapping

    // @GetMapping("/ex3")
    // public void ex4() {
    // log.info("ex3 요청");

    // // response.sendRedirect("/qList.do")
    // // 홈컨트롤러고 가는 경로
    // // return "redirect:/";

    // // return "redirect:/sample/basic";

    // }

    // 리다이렉트를 보낼떄 뭔가(데이터) 를 딸려보내고 싶을때
    // mvc 모델 2 방식 : path+=?bno +bno
}
