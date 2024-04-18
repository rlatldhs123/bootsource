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

public class BoardDto {

    private Long bno;
    @NotBlank(message = "입력해주세요")
    private String title;
    @NotBlank(message = "입력해주세요")
    private String content;
    // @ManyToOne = fetch 방식이 즉시 로딩 (FetchType.EAGER)
    @NotBlank(message = "입력해주세요")
    private String writerEmail; // 작성자 아이디
    private String writerName; // 작성자 이름
    private Long replyCount; // 해당 게시물의 댓글 수

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;
}
