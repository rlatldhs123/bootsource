package com.example.rest.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.rest.dto.SampleDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

// @Controller - 메소드가 끝나고 찾는 대상은 템플릿임 (list.html , create.html~ 등등) 
// @RestController - 데이터 그 자체를 리턴 할 수 있다
// 컨트롤러는 앞으로 이 2개가 나올 것이다
//                            - 객체 ==> json 변환하는 컨버터 필요 객체는 브라우저에서 해석이 안되니

@RestController
public class RestControllerTest {

    @GetMapping("/hello")
    public String getHello() {
        return "Hello World";
    }

    @GetMapping(value = "/sample", produces = MediaType.APPLICATION_JSON_VALUE)
    public SampleDto getSample() {

        SampleDto dto = new SampleDto();
        dto.setFirstName("홍");
        dto.setLastName("길동");
        dto.setMno(1L);
        return dto;

        // 객체 리턴이 가능함
    }

    @GetMapping(value = "/sample2", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SampleDto> getSample2() {

        List<SampleDto> list = new ArrayList<>();

        LongStream.rangeClosed(1, 10).forEach(i -> {

            SampleDto dto = new SampleDto();
            dto.setMno(i);
            dto.setFirstName("홍");
            dto.setLastName("길동");
            list.add(dto);
        });

        return list;

    }

    // 데이터 + 상태 코드 (Http 상태 코드 -- 200 , 500 , 400)
    // 를 하고 싶음
    // 이럴떄 사용 하는게 ResponseEntity
    // 라는 객체가 있음
    // check?height=150&weight=150

    @GetMapping(value = "/check", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SampleDto> getCheck(double height, double weight) {

        SampleDto dto = new SampleDto();
        dto.setMno(1L);
        dto.setFirstName(String.valueOf(height));
        dto.setLastName(String.valueOf(weight));
        // 200 : OK
        //

        if (height < 150) {

            return new ResponseEntity<SampleDto>(dto, HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<SampleDto>(dto, HttpStatus.OK);

        // 객체 리턴이 가능함
    }

    // product/bags/1234
    @GetMapping("/product/{cat}/{pid}")
    public String[] getMethodName(@PathVariable("cat") String cat, @PathVariable("pid") String pid) {
        return new String[] {
                "category : " + cat,
                "product id : " + pid

        };

    }

}
