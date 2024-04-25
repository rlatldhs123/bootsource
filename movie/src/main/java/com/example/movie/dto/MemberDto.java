package com.example.movie.dto;

import com.example.movie.constant.MemberRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class MemberDto {

    private Long mid;

    private String email;

    private String password;

    private String nickname;

    private MemberRole role;

}
