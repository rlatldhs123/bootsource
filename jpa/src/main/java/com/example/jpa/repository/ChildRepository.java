package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.entity.Child;
import com.example.jpa.entity.Item;

public interface ChildRepository extends JpaRepository<Child, Long> {

}
