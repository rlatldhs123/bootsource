package com.example.mart.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ManyToAny;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "orders")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = { "orderItems", "delivery" })
@Builder

public class Order extends BaseEntity {

    // 주문번호 pk
    @Id
    @SequenceGenerator(name = "mart_order_seq_gen", sequenceName = "order_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mart_order_seq_gen")

    @Column(name = "order_id")
    private Long id; // 주문번호
    // 외래키제작
    // 주문을 누가 햇니(회원);
    @ManyToOne
    private Member member; //

    // 주문 시간
    @CreatedDate
    private LocalDateTime orderDate;

    // 주문 상태 - ORDER,CANCEL
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Builder.Default
    // OneToMany 어노테이션은 무조건 mappedBy 를 줘야한다
    // order item 쪽에서 order 에 접근 할 수 있게 통로를 열어준다
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)

    private List<OrderItem> orderItems = new ArrayList<>();

    // 배송과 1대1 관계

    @OneToOne
    private Delivery delivery; // 1대1 관계이기에 둘증 어느 엔티티에 가도 상관은 없지만
    // 우리는 오더 엔티티 클래스의 딜리버리를 들여오기로 했다

}
