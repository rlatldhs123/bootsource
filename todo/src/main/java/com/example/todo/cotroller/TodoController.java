package com.example.todo.cotroller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.todo.dto.TodoDto;
import com.example.todo.service.TodoServiceImple;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/todo")

public class TodoController {

    private final TodoServiceImple service;

    // 멤버 변수 초기화 -1) 생성자 2) setter

    // 8080 으로 끝났을떄 루트로(/) 접속 했을때 list.html 이 노출 되도록 설정

    // 의존성 주입 : 스프링 프레임워크가 해주는 기술중 하나이며 자바의 언어 특징으로 인해 = new 를 해서 객체를 생성해야 하지만
    // 스프링프레임워크의 기술인 의존성 주입으로 인해 생략이 가능하다

    @GetMapping({ "/", "/list" })
    public String list(Model model) {
        log.info("list 요청");

        List<TodoDto> list = service.getList();

        model.addAttribute("list", list);
        return "/todo/list";
    }

    @GetMapping("/create")
    public void getCreate() {
        log.info("create 템플릿 요청");

    }

    @PostMapping("/create")
    public String postMethodName(TodoDto dto, RedirectAttributes rttr) {
        TodoDto result = service.create(dto);
        rttr.addFlashAttribute("msg", result.getId());

        return "redirect:/todo/list";
    }

    // todo/read?id=11 요청 처리 컨트롤러생성

    @GetMapping("/read")
    public void getRead(@RequestParam Long id, Model model) {

        log.info("get Read 요청");
        TodoDto todo = service.getTodo(id);

        model.addAttribute("todo", todo);

    }

    @GetMapping("/done")
    public void getCompleList(Model model) {
        log.info("Comple list 요청");

        List<TodoDto> compleList = service.getCompletedList();

        model.addAttribute("list", compleList);

    }

}
