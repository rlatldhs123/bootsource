package com.example.board.dto;

import java.time.LocalDateTime;

import com.example.board.entity.Member;

import jakarta.validation.constraints.NotBlank;
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
public class ReplyDto {

    private Long rno;

    private String text;

    private String replyer;

    private Long bno; // 부모글 번호

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

}
