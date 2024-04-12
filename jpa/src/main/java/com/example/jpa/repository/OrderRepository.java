package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.jpa.entity.Adderess;
import com.example.jpa.entity.Member2;
import com.example.jpa.entity.Order;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 주소를 조회
    @Query("SELECT o.homeAddress FROM Order o")
    List<Adderess> findByHomeAddress();

    @Query("SELECT o.member2, o.product,o.id FROM Order o")
    List<Object[]> findByOrders();

}
