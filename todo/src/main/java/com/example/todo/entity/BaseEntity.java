package com.example.todo.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@MappedSuperclass // 베이스 엔티티 상속할 경우 필드를 컬럼으로 인식하게 해줄 수 있는 어노테이션
@EntityListeners(value = AuditingEntityListener.class) // AuditingEntityListener
public abstract class BaseEntity {
    @CreatedDate
    private LocalDateTime createdeDate;
    // 컬럼내 만든 시간을 타임스태프 찍어줌
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    // 컬럼내 수정 시간 타임스태프 찍어줌

    // 이렇게 베이스 엔티티를 만들고 어노테이션 목적은 어노테이션 이름에 맞게
    // 해놓고 이 베이스 엔티티를 상속 해버리면 된다

}
