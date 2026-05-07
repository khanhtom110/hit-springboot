package com.example.revise.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 200)
    private String product;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    @Builder.Default
    private Integer quantity=1;

    @Column(nullable = false)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private OrderStatus status=OrderStatus.PENDING;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
}
