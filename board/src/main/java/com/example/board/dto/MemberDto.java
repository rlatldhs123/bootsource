package com.example.board.dto;

import com.example.board.constant.MemberRole;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class MemberDto {
    @NotBlank(message = "비어있으면 안됩니다")
    private String email;
    @NotBlank(message = "비어있으면 안됩니다")
    private String name;
    @NotBlank(message = "비어있으면 안됩니다")
    private String password;

    private MemberRole memberRole;

}
