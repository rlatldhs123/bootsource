package com.example.web1.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class MemberDto {
    @NotEmpty
    @Pattern(regexp = "(?=^[A-Za-z])(?=.+\\d)[A-Za-z\\d]{6,12}$", message = "아이디는 영대소문자, 숫자를 사용해서  6~12 자리입니다")
    private String userid;

    @NotEmpty
    @Pattern(regexp = "(?=^[A-Za-z])(?=.+\\d)(?=.+[!@$%])[A-Za-z\\d!@$%]{8,15}$", message = "비밀번호는 영대소문자, 숫자 특수 문자(!@$%)를 이용해서 8~15자리입니다")
    private String password;

    @NotNull(message = "나이 똑바로 입력하세요")
    @Min(value = 0)
    @Max(value = 120)
    private Integer age;

    @Email
    @NotEmpty(message = "이메일은 필수 요소입니다")
    private String email;

    @Length(min = 2, max = 6, message = "이름은2~6자리로 작성해주세요")
    private String name;

}
