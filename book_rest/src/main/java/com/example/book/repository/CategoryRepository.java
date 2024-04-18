package com.example.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.book.entity.Category;
import com.example.book.entity.Publisher;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // 분류명으로 찾기
    Optional<Category> findByName(String name);

    // 카테고리 이름미나 따로 찾기

}
