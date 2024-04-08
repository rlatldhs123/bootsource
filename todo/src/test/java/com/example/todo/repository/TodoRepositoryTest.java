package com.example.todo.repository;

import static org.mockito.Mockito.validateMockitoUsage;

import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.todo.entity.Todo;
import com.example.todo.repository.TodoRepository;

@SpringBootTest
public class TodoRepositoryTest {

    // DAO == TodoRepository
    // service 에서 호출 하는 메소드 테스트

    @Autowired
    private TodoRepository todoRepository;

    // todo 삽입하는 메소드

    // insert into todotbl
    // (completed,createde_date,important,last_modified_date,title,todo_id) values
    // (?,?,?,?,?,?)

    @Test
    public void insertTest() {
        LongStream.rangeClosed(1, 100).forEach(i -> {
            // Todo todo = Todo.builder()
            // .title("오늘 할일 " + i)
            // .completed(false) // sql 에서는 굳이 안넣어도 될것을 JPA 에서는 넣어줘야 한다
            // .important(true)

            // .build();

            Todo todo = Todo.builder()
                    .title("오늘 할일 " + i)

                    .build();

            // 디버그 콘솔 변경 현황 : insert into todotbl
            // (createde_date,last_modified_date,title,todo_id) values (?,?,?,?)
            // Hibernate: select todo_seq.nextval from dual

            todoRepository.save(todo);

        });

    }

    @Test
    public void todoList() {
        todoRepository.findAll().forEach(todo -> {
            System.out.println(todo);
        });

    }

    @Test
    public void readTodo() {
        System.out.println(todoRepository.findById(2L));

    }

    // 완료 목록 조회
    @Test
    public void todoCompletedList() {
        todoRepository.findByCompleted(true).forEach(todo -> {
            System.out.println(todo);

        });

    }

    // 중요 목록 조회

    @Test
    public void todoImporteandList() {
        todoRepository.findByImportant(true).forEach(todo -> {
            System.out.println(todo);

        });

    }

    // Todo 수정
    @Test
    public void updateTodo() {

        Todo entity = todoRepository.findById(2L).get();
        entity.setCompleted(true);
        todoRepository.save(entity);

    }

    // Todo 삭제

    @Test
    public void deleteTodo() {
        todoRepository.deleteById(10L);

    }

}
