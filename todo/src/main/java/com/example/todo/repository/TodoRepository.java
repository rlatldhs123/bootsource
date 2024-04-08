package com.example.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todo.entity.Todo;
import java.util.List;
import java.time.LocalDateTime;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    // 특정 칼럼에 있는 것도 여기에 정의룰 해주면 컬럼 값에 따라 조회

    List<Todo> findByCompleted(Boolean completed);

    List<Todo> findByCompletedOrderByIdDesc(Boolean completed);

    // 특정 칼럼에 있는 것도 여기에 정의룰 해주면 컬럼 값에 따라 조회
    List<Todo> findByImportant(Boolean important);

}
