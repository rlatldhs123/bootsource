package com.example.jpa.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.jpa.dto.MemoDto;
import com.example.jpa.service.MemoServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Log4j2
@Controller
@RequestMapping("/memo")

public class MemoController {

    private final MemoServiceImpl service;
    // memo List + GET

    @GetMapping("/list")
    public void getMethodName(Model model) {
        log.info("리스트 요청");

        List<MemoDto> list = service.getMemoList();
        model.addAttribute("list", list);

    }

    @GetMapping({ "/read", "/modify" })
    public void read(@RequestParam Long mno, Model model) {
        log.info("read form 요청");

        MemoDto mdto = service.getMemo(mno);

        model.addAttribute("mDto", mdto);
        // 템플릿 찾기 =>

        // /memo/read => templates 폴더 아래 /memo/read.html
        // /memo/modify => templates 폴더 아래 /memo/modify.html
    }

    @PostMapping("modify")
    public String modifyPost(MemoDto mDto, RedirectAttributes rttr) {
        log.info("modify 요청 " + mDto);

        // 수정이 잘 되면 완료시 리드로 다시 가고 싶음
        MemoDto updateDto = service.updateMemo(mDto);

        // 경로는 /memo/read 로 이동
        rttr.addAttribute("mno", updateDto.getMno());

        return "redirect:/memo/read";
    }

    @PostMapping("/delete")
    public String deleteGet(@RequestParam Long mno) {

        log.info("메모 삭제 요청{}", mno);
        service.deleteMemo(mno);
        // 삭제 성공시 리스트
        return "redirect:/memo/list";
    }

    @GetMapping("/create")
    public void getCreate(@ModelAttribute("mDto") MemoDto mDto) {
        // ModelAttribute("mDto") 를 쓰는이유는 원래 MemoDto 였던 것을 mDto 로접근하기 쉽게 하기 위해

        log.info("메모 입력 폼 요청");

    }

    @PostMapping("/create")
    public String postCreate(@Valid @ModelAttribute("mDto") MemoDto mDto, BindingResult result,
            RedirectAttributes rttr) {

        log.info("create post 요청{}", mDto);

        if (result.hasErrors()) {
            return "/memo/create";

        }
        service.insertMemo(mDto);
        rttr.addFlashAttribute("result", "SUCCESS");

        return "redirect:/memo/list";
    }

    // /memo/read?mno=3 + GET

    // /memo/modify?mon=3 + GET : 수정을 위해 화면 보여주기 요청

    // /memo/modify + POST : 실제 수정 요청

    // /memo/delete?mno=3 + GET : 삭제 요청

    // /memo/create + GET : 입력을 위해 화면 보여주기 요청
    // /memo/create + POST : 실제 입력 요청

}
