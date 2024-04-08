package com.example.todo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.todo.dto.TodoDto;
import com.example.todo.entity.Todo;
import com.example.todo.repository.TodoRepository;

import groovy.util.logging.Log4j2;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Log4j2
public class TodoServiceImple {
    private final TodoRepository todoRepository;

    public List<TodoDto> getList() {
        List<Todo> list = todoRepository.findAll();

        // Todo => TOdoDto 변환
        List<TodoDto> todolist = new ArrayList<>();
        list.forEach(todo -> todolist.add(entityToDto(todo)));

        return todolist;
    }

    private TodoDto entityToDto(Todo entity) {
        return TodoDto.builder()
                .id(entity.getId())
                .completed(entity.getCompleted())
                .important(entity.getImportant())
                .title(entity.getTitle())
                .createdeDate(entity.getCreatedeDate())
                .lastModifiedDate(entity.getLastModifiedDate())
                .build();

    }

}
