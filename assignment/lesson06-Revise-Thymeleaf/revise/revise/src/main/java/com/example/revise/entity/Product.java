package com.example.revise.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Product extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 200)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false,precision = 12,scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ProductCategory category=ProductCategory.OTHER;

    @Column(name = "stock_quantity",nullable = false)
    @Builder.Default
    private Integer stockQuantity=0;

    @Column(name = "is_active",nullable = false)
    @Builder.Default
    private Boolean active=true;

}
