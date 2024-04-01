package com.example.web1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddDto {

    private int num1;
    private int num2;

    private int result;

    private String s1;
    private String s2;
    private String s3;

}
