package com.example.revise.dto.request;

import com.example.revise.entity.ProductCategory;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {

    @NotBlank(message = "Ten san pham khong duoc de trong")
    @Size(max = 100,message = "Ten san pham toi da 100 ky tu")
    private String name;

    @Size(max = 1000,message = "Mo ta toi da 1000 ky tu")
    private String description;

    @NotNull(message = "Gia khong duoc de trong")
    @DecimalMin(value = "0.01",message = "Gia phai lon hon 0")
    private BigDecimal price;

    @NotNull(message = "Danh muc khong duoc de trong")
    private ProductCategory category;

    @NotNull(message = "So luong ton kho khong duoc de trong")
    @Min(value = 0,message = "So luong ton kho phai >= 0")
    private Integer stockQuantity;

}
