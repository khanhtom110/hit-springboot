package com.example.revise.dto.response;

import com.example.revise.entity.Product;
import com.example.revise.entity.ProductCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Schema(description = "Thông tin sản phẩm trả về")
public class ProductResponse {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private String category;

    private Integer stockQuantity;

    private Boolean active;

    private LocalDateTime createdAt;

    public static ProductResponse form(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory().name())
                .stockQuantity(product.getStockQuantity())
                .active(product.getActive())
                .createdAt(product.getCreatedAt())
                .build();
    }

}
