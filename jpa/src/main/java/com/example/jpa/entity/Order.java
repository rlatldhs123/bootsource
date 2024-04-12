package com.example.jpa.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString(exclude = { "member2", "product" })
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "jpql_order")
public class Order {

    @Id
    @SequenceGenerator(name = "jpql_order_seq_gen", sequenceName = "jpql_order_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jpql_order_seq_gen")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member2 member2;
    @Embedded
    private Adderess homeAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

}
