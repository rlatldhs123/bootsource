package com.example.todo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.todo.dto.TodoDto;
import com.example.todo.entity.Todo;

@SpringBootTest
public class TodoServiceTest {

    @Autowired
    private TodoServiceImple sevice;

    @Test
    public void serviceList() {
        System.out.println(sevice.getList());

    }

    @Test
    public void serviceCreate() {
        TodoDto dto = new TodoDto();
        dto.setTitle("스프링 공부");
        dto.setImportant(true);

        System.out.println(sevice.create(dto));

    }

    @Test
    public void serviceRead() {
        System.out.println(sevice.getTodo(101L));

    }

}
