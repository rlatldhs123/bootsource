package com.example.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import groovy.transform.ToString;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(excludes = "childList")
@Getter
@Setter
@Entity

public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    // cascade : 영속 상태 전이
    // 부모가 영속 상태시 자식도 같이 영속 상태로 감
    // orphRemoval : 부모하고 연관 관계가 끊어진 경우 (고아 객체) 자동으로 삭제

    @Builder.Default
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER) // FetchType.LAZY
                                                                                                              // 이기에 나온
                                                                                                              // 것
    private List<Child> childList = new ArrayList<>();

}
