package com.example.mart.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class OrderItem {
    @Column(name = "order_item_id")
    @Id
    @SequenceGenerator(name = "mart_orderitem_seq_gen", sequenceName = "orderitem_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mart_orderitem_seq_gen")

    private Long id;
    @ManyToOne()
    private Item item;

    @ManyToOne
    private Order order;

    private int orderprice;

    private int count;

}
