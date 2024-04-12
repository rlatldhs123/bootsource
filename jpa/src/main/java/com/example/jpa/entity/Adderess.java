package com.example.jpa.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Embeddable // 다른 엔티티에서 포함해서 사용
@ToString
public class Adderess {

    private String city;
    private String street;
    private String zipcode;

}
