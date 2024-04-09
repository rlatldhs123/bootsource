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

    // 미완료 목록
    public List<TodoDto> getList() {
        List<Todo> list = todoRepository.findByCompleted(false);

        // Todo => TOdoDto 변환
        List<TodoDto> todolist = new ArrayList<>();
        list.forEach(todo -> todolist.add(entityToDto(todo)));

        return todolist;
    }

    public TodoDto create(TodoDto dto) {
        // TOdoDto => Todo 변환

        Todo entity = todoRepository.save(dtoToEntity(dto));
        return entityToDto(entity);
    }

    public TodoDto getTodo(Long id) {
        Todo todo = todoRepository.findById(id).get();

        return entityToDto(todo);
    }

    public List<TodoDto> getCompletedList() {

        List<Todo> list = todoRepository.findByCompleted(true);

        List<TodoDto> compList = new ArrayList<>();
        list.forEach(todo -> compList.add(entityToDto(todo)));
        return compList;

    }

    private Todo dtoToEntity(TodoDto dto) {
        return Todo.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .completed(dto.getCompleted())
                .important(dto.getImportant())
                .build();

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
