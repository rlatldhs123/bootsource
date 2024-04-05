package com.example.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder;

@Entity
@Setter
@Getter
@ToString(exclude = { "members" }) // toStirng
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Team {
    @Column(name = "team_id")
    @Id
    private String id;

    @Column(name = "team_name")
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER) // 주체가 누군지 알려줘야 한다 2. 서로지금 연결되어 있는 상태 many 쪽이 연결 되어있는 상태에서의
                                                           // 주인이다
    private List<TeamMember> members = new ArrayList<>();

}
