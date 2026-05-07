package com.example.revise.dto.request;

import com.example.revise.entity.OrderStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {

    @NotBlank(message = "Ten sp khong de trong")
    @Size(max = 200,message = "Ten sp toi da 200 ky tu")
    private String product;

    @NotNull(message = "Gia khong duoc de trong")
    @Positive(message = "Gia phai lon hon 0")
    private Double price;

    @NotNull(message = "So luong khong duoc de trong")
    @Positive(message = "So luong phai lon hon 0")
    private Integer quantity;

    @NotNull(message = "Trang thai khong duoc de trong")
    private OrderStatus status;

    @NotNull(message = "Ma khach hang khong duoc de trong")
    @Positive(message = "Ma khach hang phai la so duong")
    private Long userId;
}
