package com.example.jpa.entity;

import groovy.transform.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

@Entity
@Setter
@Getter

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Team {
    @Column(name = "team_id")
    @Id
    private String id;

    @Column(name = "team_name")
    private String name;

}
