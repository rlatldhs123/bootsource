package com.example.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString(exclude = "sportsMember")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder

public class Locker extends BaseEntity {

    @SequenceGenerator(name = "lock_member_seq_gen", sequenceName = "lock_member_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lock_member_seq_gen")
    @Id
    @Column(name = "locker_id")
    private Long id;

    private String name;
    // 주인은 한쪽에만 알려준다

    @OneToOne(mappedBy = "locker")
    private SportsMember sportsMember;

}
