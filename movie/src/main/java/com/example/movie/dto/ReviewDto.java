package com.example.movie.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class ReviewDto {

    private Long reviewNo;

    // grade(int)
    private int grade;

    // text 평점내용

    private String text;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    // 멤버 관계

    private Long mid;
    private String email;
    private String nickname;

    // 영화 관계
    private Long mno;

}
