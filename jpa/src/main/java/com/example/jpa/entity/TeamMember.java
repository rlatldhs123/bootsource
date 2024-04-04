package com.example.jpa.entity;

import groovy.transform.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class TeamMember {
    @Column(name = "member_id")
    @Id
    private String id;

    @Column(name = "team_name")
    private String userName;

    // 이 코드는 외래캐 제약 조건과 같은 구문이다 외래키는 다수쪽에 준다
    // many = 팀 1 member

    @ManyToOne
    private Team team;

}
