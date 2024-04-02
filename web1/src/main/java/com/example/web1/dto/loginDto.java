package com.example.web1.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

// @ToString
// @Getter
// @Setter
// @AllArgsConstructor
// @NoArgsConstructor
@Data
// @data 로 위 어노테이션을 한번에 넣어줄 수 있다
public class LoginDto {
    @NotEmpty // 널 값 불가
    @Email(message = "이메일을 확인해주세요") // 입력이 되었을떄만 발동되는 어노테이션이다
    // 그러므로 NotEmpty 를 빼먹으면 500 에러가 뜨게 된다

    String email;

    @NotBlank // 비어 있을 수 없음 하지만 어차피 길이제약을 거는
    // 어노테이션을 쓸거라면 굳이 쓸 필요는 없다
    @Length(min = 2, max = 6) // 길이 검증 어노테이션
    String name;

}
