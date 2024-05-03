package com.example.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PasswordChangeDto {

    private String email;

    private String currentPassword; // 현재 비밀번호

    private String newPassword; // 새 비밀번호

}
