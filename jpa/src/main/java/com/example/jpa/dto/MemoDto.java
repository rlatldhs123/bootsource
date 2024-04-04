package com.example.jpa.dto;

import groovy.transform.ToString;
import groovy.transform.builder.Builder;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@lombok.Builder
@Getter
@Setter
public class MemoDto {

    private Long mno;

    @NotBlank(message = "메모 내용을 확인해 주세요")
    private String memoText;

}
