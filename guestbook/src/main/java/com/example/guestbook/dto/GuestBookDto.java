package com.example.guestbook.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestBookDto {

    private Long gno;
    @NotBlank(message = "작성자를 입력하세요")
    private String writer;
    @NotBlank(message = "제목을 입력하세요")
    private String title;
    @NotBlank(message = "내용을 입력하세요")
    private String content;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

}
