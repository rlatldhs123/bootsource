package com.example.jpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import com.example.jpa.entity.Member2;
import com.example.jpa.entity.Product;
import com.example.jpa.entity.QMember2;
import com.example.jpa.entity.QProduct;
import com.querydsl.core.BooleanBuilder;

@SpringBootTest
public class QuertdslTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private Member2Repository member2Repository;

    @Test
    public void testQueryDsl() {

        // q프로덕트 가져오기

        QProduct prodoct = QProduct.product;
        // 제품명이 제품 1인 제품 조회
        Iterable<Product> products = productRepository.findAll(prodoct.name.eq("제품1"));

        // products =
        // productRepository.findAll(prodoct.name.eq("제품1")).and(prodoct.price.gt(5000));

        // 제품명이 제품 글자가 포함된 제품 조회

        products = productRepository.findAll(prodoct.name.contains("제품"));

        // 제품이라는 글자로 시작 하는 제품 조회
        products = productRepository.findAll(prodoct.name.startsWith("제품"));

        // 제품이라는 글자로 끝나는 제품 조회
        products = productRepository.findAll(prodoct.name.startsWith("제품"));


        // 제품명이 제품 1이고 가격이 5000 초과인 제품 조회
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(prodoct.name.eq("제품1"));
        builder.and(prodoct.price.goe(5000));
    }

    


    @Test
    public void testQueryDsl2() {

        QMember2 member2 = QMember2.member2;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(member2.userName.eq("User1"));

        Iterable<Member2> list = member2Repository.findAll(builder, Sort.by("id").descending());

        for (Member2 mem : list) {
            System.out.println(mem);

        }
    }

}
