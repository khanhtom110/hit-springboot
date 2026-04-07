package edu.khanh.ProductManagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên không được để trống")
    @Size(min = 2,max = 100,message = "Tên nằm trong khoảng 2-100 ký tự")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Giá không được để trống")
    @Positive(message = "Giá không được nhỏ hơn 0")
    private Double price;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 0,message = "Số lượng không được nhỏ hơn 0")
    private Integer quantity;

    @Size(max = 1000,message = "Tên nằm không quá 1000 ký tự")
    @Column(length = 1000)
    private String description;

    private boolean active=true;

    @Column(name = "create_at",updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    private void onCreate(){
        this.createdAt=LocalDateTime.now();
        this.updatedAt=LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate(){
        this.updatedAt=LocalDateTime.now();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
