package com.example.web1.dto;

import lombok.Data;

// @ToString
// @Getter
// @Setter
// @AllArgsConstructor
// @NoArgsConstructor
@Data
// @data 로 위 어노테이션을 한번에 넣어줄 수 있다
public class loginDto {

    String email;
    String name;

}
