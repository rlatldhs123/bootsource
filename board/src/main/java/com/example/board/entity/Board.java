package com.example.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(exclude = "writer")
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Board extends BaseEntity {
    @Id
    @SequenceGenerator(name = "board_seq_gen", sequenceName = "board_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq_gen")
    private Long bno;

    private String title;

    private String content;
    // @ManyToOne = fetch 방식이 즉시 로딩 (FetchType.EAGER)

    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;

}
