package com.example.movie.dto;

import com.example.movie.constant.MemberRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    private Long mid;

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email
    private String email;

    @NotBlank(message = "패스워드는 필수 입력 값입니다")
    private String password;

    @NotBlank(message = "닉네임은 필수 입력 값입니다")
    private String nickname;

    private MemberRole role;
}
